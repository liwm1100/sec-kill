package com.liwm.sk.gateway.filter;


import cn.dev33.satoken.reactor.context.SaReactorHolder;
import cn.dev33.satoken.reactor.context.SaReactorSyncHolder;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.spring.pathmatch.SaPathPatternParserUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.liwm.sk.common.dto.Result;
import com.liwm.sk.common.dto.ResultEnum;
import com.liwm.sk.gateway.feign.SkAuthClient;
import com.liwm.sk.gateway.feign.dto.ApiDTO;
import com.liwm.sk.gateway.feign.dto.ResourcesDTO;
import com.liwm.sk.gateway.feign.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AuthenticationInterceptor implements WebFilter, Ordered {

    @Autowired
    private SkAuthClient skAuthClient;

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();
        log.info("gateway request global filter path:{}", path);
        SaReactorSyncHolder.setContext(exchange);
        //0授权，1忽略授权，2公开
        List<ApiDTO> publicApis = skAuthClient.listApi(2).getData();
        //身份认证
        SaRouter.match("/**")    // 拦截的 path 列表，可以写多个 */
                .notMatch(r -> {
                    for (String uri : baseIgnoreUrls()) {
                        if (StringUtils.equals(uri, path)) {
                            return true;
                        }
                        if (SaPathPatternParserUtil.match(uri, path)) {
                            return true;
                        }
                    }
                    for (ApiDTO apiDTO : publicApis) {
                        if (StringUtils.equals(apiDTO.getUri(), path)) {
                            return true;
                        }
                        if (SaPathPatternParserUtil.match(apiDTO.getUri(), path)) {
                            return true;
                        }
                    }
                    return false;
                })
                .check(r -> StpUtil.checkLogin());

        //权限认证:外部用户，理论上应该内部外部分离gateway
        Result<UserDTO> userInfo = skAuthClient.getUserInfo(Long.parseLong(StpUtil.getLoginId()+""));
        if (userInfo.getCode() == ResultEnum.SUCCESS.getCode() && userInfo.getData().getType() == 2) {
            //外部用户
            return chain.filter(exchange).contextWrite(ctx -> {
                ctx = ctx.put(SaReactorHolder.CONTEXT_KEY, exchange);
                return ctx;
            }).doFinally(r -> {
                SaReactorSyncHolder.clearContext();
            });
        }
        //权限认证 内部用户-忽略授权
        List<ApiDTO> ignoreApis = skAuthClient.listApi(1).getData();
        List<ApiDTO> currentIgnoreApi = ignoreApis.stream().filter(p -> SaPathPatternParserUtil.match(p.getUri(), path)).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(currentIgnoreApi)) {
            return chain.filter(exchange).contextWrite(ctx -> {
                ctx = ctx.put(SaReactorHolder.CONTEXT_KEY, exchange);
                return ctx;
            }).doFinally(r -> {
                SaReactorSyncHolder.clearContext();
            });
        }
        //权限认证 内部用户
        List<ResourcesDTO> resourcesDTOS = skAuthClient.listAllResourceApi().getData();
        LinkedHashMap<String, Set<String>> allApi = resourcesDTOS.stream()
                .collect(Collectors.toMap(
                        item -> item.getUri() + "###" + item.getRequestMethod(),
                        resourceApiVO -> {
                            Set<String> codes = new HashSet<>();
                            codes.add(resourceApiVO.getCode());
                            return codes;
                        },
                        (existingCodes, newCodes) -> {
                            existingCodes.addAll(newCodes);
                            return existingCodes;
                        },
                        LinkedHashMap::new
                ));
        allApi.forEach((api, auth) -> {
            String[] arrays = StringUtils.split(api, "###");
            String uri = arrays[0];
            String requestMethod = arrays[1];
            SaRouter.match(uri).matchMethod(requestMethod).check(r -> StpUtil.checkPermissionOr(auth.toArray(new String[]{})));
        });
        return chain.filter(exchange).contextWrite(ctx -> {
            ctx = ctx.put(SaReactorHolder.CONTEXT_KEY, exchange);
            return ctx;
        }).doFinally(r -> {
            SaReactorSyncHolder.clearContext();
        });
    }

    private Set<String> baseIgnoreUrls() {
        LinkedHashSet<String> baseIgnoreUrls = new LinkedHashSet<>();
        Collections.addAll(baseIgnoreUrls,
                "/user/login",
                    "/user/register",
                    "/{p:[a-zA-Z0-9]+}.css",
                    "/{p:[a-zA-Z0-9]+}.js",
                    "/{p:[a-zA-Z0-9]+}.html",
                    "/{p:[a-zA-Z0-9]+}.ico",
                    "/{p:[a-zA-Z0-9]+}.jpg",
                    "/{p:[a-zA-Z0-9]+}.jpeg",
                    "/{p:[a-zA-Z0-9]+}.png",
                    "/{p:[a-zA-Z0-9]+}.gif",

                    "/swagger-ui.html**",
                    "/doc.html**",
                    "/favicon.ico",
                    "/v3/**",
                    "/webjars/**",
                    "/v2/**",
                    "/swagger-resources/**",
                    "/actuator/**",
                    "/static/**",
                    "/error",
                    "/druid/**"
        );
        return baseIgnoreUrls;
    }
}
