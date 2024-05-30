package org.exception.rabbitmq.infrastructure.publisher;

import org.exception.rabbitmq.infrastructure.consumer.event.DemoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DemoPublisher {

    private static final Logger log = LoggerFactory.getLogger(DemoPublisher.class);

    @Value("${queues.exchange}")
    private String exchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void publishEvent(DemoEvent demoEvent) {

        log.info("Sending event to queue: {}", demoEvent.toJson());

        String topic = exchange + ".demo-application.demo-method.test";

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("topic", topic);
        messageProperties.setHeader("exchange", exchange);
        messageProperties.setHeader("resource", "demo-application");
        messageProperties.setHeader("subject", "demo-method");
        messageProperties.setHeader("action", "test");

        Message message = rabbitTemplate.getMessageConverter().toMessage(demoEvent.toJson(), messageProperties);
        rabbitTemplate.convertAndSend(exchange, topic, message);
    }

}
