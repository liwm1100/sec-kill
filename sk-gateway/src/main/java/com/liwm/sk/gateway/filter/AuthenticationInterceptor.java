package com.liwm.sk.gateway.filter;


import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.context.SaReactorHolder;
import cn.dev33.satoken.reactor.context.SaReactorSyncHolder;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.spring.pathmatch.SaPathPatternParserUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.liwm.sk.common.dto.Result;
import com.liwm.sk.common.dto.ResultEnum;
import com.liwm.sk.gateway.feign.SkAuthClient;
import com.liwm.sk.gateway.feign.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.*;

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
        log.info("gateway request global filter path:{}", path);
        SaReactorSyncHolder.setContext(exchange);

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
                    return false;
                })
                .check(r -> StpUtil.checkLogin());

        //权限认证
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
/*
        allApi.forEach((api, auth) -> {
            List<String> list = StrUtil.split(api, "###");
            String uri = list.get(0);
            String requestMethod = list.get(1);
            SaRouter.match(uri).matchMethod(requestMethod)
                    .notMatch(r -> {
                        String path = SaHolder.getRequest().getRequestPath();
                        String method = SaHolder.getRequest().getMethod();
                        for (Map.Entry<String, Set<String>> map : anyone.entrySet()) {
                            String key = map.getKey();
                            Set<String> value = map.getValue();
                            if (StrUtil.equalsAny(key, method, SaHttpMethod.ALL.name())) {
                                for (String ignore : value) {
                                    if (StrUtil.equals(ignore, path)) {
                                        return true;
                                    }

                                    if (SaPathPatternParserUtil.match(ignore, path)) {
                                        return true;
                                    }
                                }
                            }
                        }
                        return false;
                    })
                    .check(r -> StpUtil.checkPermissionOr(auth.toArray(String[]::new)));
        });*/
        return null;
    }

    private Set<String> baseIgnoreUrls() {
        LinkedHashSet<String> baseIgnoreUrls = new LinkedHashSet<>();
        Collections.addAll(baseIgnoreUrls,
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
