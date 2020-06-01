package com.rafaelquines.mqtest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.ConnectionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import javax.jms.ConnectionFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@RestController
//@EnableJms
public class IbmmqtestApplication {

	@Value("${mq.fila}")
	private String fila;

	@Autowired
	private JmsTemplate jmsTemplate;
	private static final Logger logger = LoggerFactory.getLogger(IbmmqtestApplication.class);
	private static final Logger splunkLogger = LoggerFactory.getLogger("Splunk");
	public static void main(String[] args) {
//		String test = "abcdefghijklmnopqrstuvwxyz";
//		System.out.println(test.substring(0, 10));//a-j
//		System.out.println(test.substring(10, 20));//k-t
//		String dateStr = "2004-05-29-04.46.23.671700";
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss.SSSSSS");
//		LocalDateTime date = LocalDateTime.parse(dateStr, formatter);
//		System.out.println("Date: " + date);
//		System.out.println("Fraction: " + date.getYear());
		//logger.info("teste 123");
		SpringApplication.run(IbmmqtestApplication.class, args);
	}

	@GetMapping("send")
	String send(){
		try{
			logger.info("sending message");
			MDC.put("correlationId", CorrelationIdGenerator.generate());
			splunkLogger.info("Msg enviada");
			jmsTemplate.convertAndSend(this.fila, "Msg " + LocalDateTime.now());
			return "OK";
		}catch(JmsException ex){
			ex.printStackTrace();
			return "FAIL";
		}
	}

//	@GetMapping("recv")
//	String recv(){
//		try{
//			return jmsTemplate.receiveAndConvert("DEV.QUEUE.1").toString();
//		}catch(JmsException ex){
//			ex.printStackTrace();
//			return "FAIL";
//		}
//	}

//	@Bean
//	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
//													DefaultJmsListenerContainerFactoryConfigurer configurer) {
//		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
//		// This provides all boot's default to this factory, including the message converter
//		configurer.configure(factory, connectionFactory);
//		// You could still override some of Boot's default if necessary.
//		return factory;
//	}

//	@Bean // Serialize message content to json using TextMessage
//	public MessageConverter jacksonJmsMessageConverter() {
//		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//		converter.setTargetType(MessageType.TEXT);
//		converter.setTypeIdPropertyName("_type");
//		return converter;
//	}

}
