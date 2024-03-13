package org.example.secretsanta.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class loggerServiceAspect {

    private static final Logger logger = Logger.getLogger(loggerServiceAspect.class.getName());

    @After("execution(* org.example.secretsanta.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Method executed in Service " + joinPoint.getSignature().getName());
    }

}
