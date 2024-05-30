package org.exception.rabbitmq.infrastructure.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.exception.rabbitmq.application.service.DemoService;
import org.exception.rabbitmq.infrastructure.consumer.event.DemoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class DemoConsumer {

    private ObjectMapper objectMapper;
    private DemoService demoService;

    private static final Logger log = LoggerFactory.getLogger(DemoConsumer.class);

    @Autowired
    public DemoConsumer(ObjectMapper objectMapper, DemoService demoService) {
        this.objectMapper = objectMapper;
        this.demoService = demoService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "${queues.demo.queue}",
                    durable = "true",
                    arguments = {
                            @Argument(
                                    name = "x-dead-letter-exchange",
                                    value = ""
                            ),
                            @Argument(
                                    name = "x-dead-letter-routing-key",
                                    value = "${queues.demo.dlx}"
                            )
                    }
            ),
            exchange = @Exchange(
                    value = "${queues.exchange}",
                    durable = "true",
                    type = ExchangeTypes.HEADERS
            ),
            arguments = {
                    @Argument(name = "exchange", value = "${queues.exchange}"),
                    @Argument(name = "resource", value = "demo-application"),
                    @Argument(name = "subject", value = "demo-method"),
                    @Argument(name = "action", value = "test")
            }
    ))
    public void processDocumentRequest(@Payload String payload, Message message) throws Exception {
        log.info("Event recived from queue: {}", payload);
        try {
            DemoEvent demoEvent = objectMapper.readValue(payload, DemoEvent.class);
            demoService.someMethod(demoEvent);
        } catch (Exception ex) {
            log.error("Exception: {}", ex.getMessage());
            throw new AmqpRejectAndDontRequeueException("Error Handler converted exception to fatal", ex);
        }
    }

}
