package com.rafaelquines.mqtest;

import com.ibm.mq.jms.MQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.slf4j.MDC;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.UUID;

@Component
public class MessageReceiver {
    private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);
    /*
    @Bean
    public DefaultJmsListenerContainerFactory myFactory(
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory());
        factory.setMessageConverter(myMessageConverter());
        return factory;
    }
*/

//    @Bean
//    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory() {
//        DefaultJmsListenerContainerFactory factory =
//                new DefaultJmsListenerContainerFactory();
////        factory
////                .setConnectionFactory(receiverActiveMQConnectionFactory());
//        factory.setConcurrency("3-10");
//
//        return factory;
//    }

    @JmsListener(destination = "${mq.fila}", containerFactory = "queueContainerFactory",
     concurrency = "5-10")
    public void receiveMessage(TextMessage message) throws Exception {
        String correlationId = CorrelationIdGenerator.generate();
        //MDC.put("correlationId", correlationId);
//        System.out.println("Fila: " + message.getJMSDestination().
        logger.info("Received <" + correlationId + ">: " + message.getText());
        Thread.sleep(2000);
        logger.info("Fim <" + correlationId + ">");
        //logger.info("{ campo: \"tri\", campo2: \"xxx\" }");
        //throw new Exception("erro");
    }
}
