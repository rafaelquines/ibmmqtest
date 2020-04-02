package com.rafaelquines.mqtest;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {
    @JmsListener(destination = "DEV.QUEUE.1", containerFactory = "myFactory")
    public void receiveMessage(String email) {
        System.out.println("Received <" + email + ">");
    }
}
