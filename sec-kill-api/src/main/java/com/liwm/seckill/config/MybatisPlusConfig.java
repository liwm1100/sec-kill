package com.liwm.seckill.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.liwm.seckill.mapper")
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分表策略：按订单ID取模分10张表
        interceptor.addInnerInterceptor(new DynamicTableNameInnerInterceptor() {
            @Override
            protected String changeTableName(String sql, String tableName) {
                if ("sk_order".equals(tableName)) {
                    RequestData requestData = RequestDataHolder.get();
                    return "sk_order_" + (requestData.getOrderId().hashCode() % 10);
                }
                return tableName;
            }
        });
        return interceptor;
    }
}

