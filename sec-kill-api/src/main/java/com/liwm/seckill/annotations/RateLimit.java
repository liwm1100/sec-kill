package com.liwm.seckill.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    double permitsPerSecond() default 10.0; // 默认QPS=10
    String key() default "";               // 限流维度（如用户ID）
}
