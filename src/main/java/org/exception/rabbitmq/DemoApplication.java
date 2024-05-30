package org.exception.rabbitmq;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import brave.Tracing;
import brave.messaging.MessagingTracing;
import brave.spring.rabbit.SpringRabbitTracing;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	
	@Bean
	public Tracing tracing() {
	  return Tracing.newBuilder()
	      .localServiceName("spring-amqp-producer")
	      .build();
	}

	@Bean
	public MessagingTracing messagingTracing(Tracing tracing) {
	  return MessagingTracing.create(tracing);
	}

	@Bean
	public SpringRabbitTracing springRabbitTracing(MessagingTracing messagingTracing) {
	  return SpringRabbitTracing.newBuilder(messagingTracing)
	                            .remoteServiceName("my-mq-service")
	                            .build();
	}
	
	//PRODUCER
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
	    SpringRabbitTracing springRabbitTracing) {
	  RabbitTemplate rabbitTemplate = springRabbitTracing.newRabbitTemplate(connectionFactory);
	  // other customizations as required
	  return rabbitTemplate;
	}
	
	
	//CONSUMER
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
	    ConnectionFactory connectionFactory,
	    SpringRabbitTracing springRabbitTracing
	) {
	  return springRabbitTracing.newSimpleRabbitListenerContainerFactory(connectionFactory);
	}
}