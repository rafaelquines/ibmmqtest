package com.rafaelquines.mqtest.exceptions;

import org.springframework.util.ErrorHandler;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

public class JmsExceptionHandler implements ErrorHandler, ExceptionListener {

    @Override
    public void onException(JMSException e) {

    }

    @Override
    public void handleError(Throwable throwable) {

    }
}
