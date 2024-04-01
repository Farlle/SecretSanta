package ru.sberschool.secretsanta.logger;


import java.util.logging.Logger;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggerController {

    private static final Logger logger = Logger.getLogger(LoggerController.class.getName());

    @After("execution(* ru.sberschool.secretsanta.controller.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Method executed in Controller " + joinPoint.getSignature().getName());
    }

}