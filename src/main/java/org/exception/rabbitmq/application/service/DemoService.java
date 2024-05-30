package org.exception.rabbitmq.application.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.exception.rabbitmq.infrastructure.consumer.event.DemoEvent;
import org.exception.rabbitmq.infrastructure.publisher.DemoPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

	private DemoPublisher demoPublisher;
	
	@Autowired
	public DemoService(DemoPublisher demoPublisher) {
		this.demoPublisher=demoPublisher;
	}

	public void publisherMethod() {
		demoPublisher.publishEvent(new DemoEvent(RandomStringUtils.randomAlphabetic(10)));	
	}
	
	public void someMethod(DemoEvent event) throws Exception {
		
		//some business logic
		
		//throw new Exception("Test message to DLX");
		
	}


	
}
