package org.exception.rabbitmq.infrastructure.consumer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqConfig {
    @Bean
    public static Queue newProcessDocumentQueueDlx(@Value("${queues.demo.dlx}") String queue) {
        return new Queue(queue);
    }

}
