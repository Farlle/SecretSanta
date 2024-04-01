package ru.sberschool.secretsanta.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggerService {

    private static final Logger logger = Logger.getLogger(LoggerService.class.getName());

    @After("execution(* ru.sberschool.secretsanta.service.impl.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Method executed in Service " + joinPoint.getSignature().getName());
    }

}
