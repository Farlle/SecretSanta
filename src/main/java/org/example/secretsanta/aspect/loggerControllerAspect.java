package org.example.secretsanta.aspect;


import java.util.logging.Logger;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class loggerControllerAspect {

    private static final Logger logger = Logger.getLogger(loggerControllerAspect.class.getName());

    @After("execution(* org.example.secretsanta.controller.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Method executed in Controller " + joinPoint.getSignature().getName());
    }

}
