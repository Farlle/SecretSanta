package org.example.secretsanta.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


import java.util.logging.Level;
import java.util.logging.Logger;


@ControllerAdvice
public class CustomErrorController {

    private static final Logger logger = Logger.getLogger(CustomErrorController.class.getName());

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(final Throwable throwable) {
        logger.log(Level.SEVERE, "Произошла ошибка на сервере", throwable);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
