package com.rafaelquines.mqtest.queue;

import javax.jms.TextMessage;

import com.rafaelquines.mqtest.utils.CorrelationIdGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

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
