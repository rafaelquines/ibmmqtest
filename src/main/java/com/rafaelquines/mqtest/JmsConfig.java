package com.rafaelquines.mqtest;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;

@Configuration
@EnableJms
public class JmsConfig {

    @Value("${mq.queueManager}")
    private String queueManager;

    @Value("${mq.channel}")
    private String channel;

    @Value("${mq.connName}")
    private String connName;

    @Value("${mq.user}")
    private String user;

    @Value("${mq.password}")
    private String password;


    @Bean("jmsTemplate")
    @Primary
    public JmsTemplate jmsTemplate(@Qualifier("cachingConnectionFactory") ConnectionFactory factory) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(factory);
        jmsTemplate.setReceiveTimeout(-1);
        return jmsTemplate;
    }

    @Bean
    @Primary
    public MQConnectionFactory jmsConnectionFactory() throws JMSException {
        MQConnectionFactory cf = new MQConnectionFactory();
        cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, queueManager);
        cf.setStringProperty(WMQConstants.WMQ_CONNECTION_NAME_LIST, connName);
        cf.setStringProperty(WMQConstants.WMQ_CHANNEL, channel);
        cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
        cf.setStringProperty(WMQConstants.USERID, user);
        cf.setStringProperty(WMQConstants.PASSWORD, password);
        return cf;
    }

    @Bean("cachingConnectionFactory")
    public ConnectionFactory cachingConnectionFactory(MQConnectionFactory factory) {
        CachingConnectionFactory cachingFactory = new CachingConnectionFactory(factory);
        cachingFactory.setCacheProducers(false);
        return cachingFactory;
    }

    @Bean
    public JmsExceptionHandler jmsExceptionHandler() {
        return new JmsExceptionHandler();
    }

    @Bean(name = "queueContainerFactory")
    public DefaultJmsListenerContainerFactory queueContainerFactory(
            @Qualifier("cachingConnectionFactory") ConnectionFactory connectionFactory,
            JmsExceptionHandler jeh) {
        CustomJmsListenerContainerFactory factory = new CustomJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(false);
        factory.setErrorHandler(jeh);
        factory.setExceptionListener(jeh);
        factory.setSessionTransacted(true);
        return factory;
    }

    public static class CustomJmsListenerContainerFactory extends DefaultJmsListenerContainerFactory {
        private ExceptionListener exceptionListener;
        public void setExceptionListener(ExceptionListener exceptionListener) {
            this.exceptionListener = exceptionListener;
        }

        protected DefaultMessageListenerContainer createContainerInstance() {
            DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
            container.setExceptionListener(exceptionListener);
            return container;
        }
    }

}
