package ru.otus.hw.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@ConditionalOnProperty(name = "application.isLoggingAspectEnabled", havingValue = "true")
@Component
public class LoggingAspect {
    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* ru.otus.hw.service.*.*(..))")
    public void logBeforeServiceMethods(JoinPoint joinPoint) {
        LOG.info("Executing service method:  {}(), with args: {}", joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* ru.otus.hw.service.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        LOG.info("Method {} executed successfully, Returned: {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(pointcut = "execution(* ru.otus.hw.service.*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        LOG.error("Method {} threw an exception: ", joinPoint.getSignature().getName(), error);
    }
}
