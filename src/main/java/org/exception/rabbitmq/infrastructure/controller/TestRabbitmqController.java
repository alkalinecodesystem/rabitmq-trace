package org.exception.rabbitmq.infrastructure.controller;

import org.exception.rabbitmq.application.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TestRabbitmqController {

	private static final Logger logger = LoggerFactory.getLogger(TestRabbitmqController.class);

	@Autowired
	private DemoService demoService;

	@GetMapping("/rabbitmq")
	void testQueueDemo() {
		try {
			logger.info("start - testQueue");

			demoService.publisherMethod();
			
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
		} finally {
			logger.info("end - testQueue");
		}

	}


}
