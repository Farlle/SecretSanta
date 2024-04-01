package ru.sberschool.secretsanta.logger;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;


@Aspect
@Component
public class LoggerController {

    private static final Logger logger = Logger.getLogger(LoggerController.class.getName());

    @After("execution(* ru.sberschool.secretsanta.controller.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Method executed in Controller " + joinPoint.getSignature().getName());
    }

}
