package com.liwm.seckill.config;

import com.google.common.util.concurrent.RateLimiter;
import com.liwm.seckill.annotations.RateLimit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitAspect {
    private final ConcurrentHashMap<String, RateLimiter> limiters = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimit)")
    public Object limit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String key = resolveKey(joinPoint, rateLimit.key());
        RateLimiter limiter = limiters.computeIfAbsent(key, k -> RateLimiter.create(rateLimit.permitsPerSecond()));

        if (limiter.tryAcquire()) {
            return joinPoint.proceed();
        } else {
            throw new RuntimeException("当前请求被限流");
        }
    }
    //todo key generate
    private String resolveKey(ProceedingJoinPoint joinPoint, String keyExpr) {
        // 解析SpEL表达式，如根据参数动态生成key
        return "default_key"; // 示例简化
    }
}
