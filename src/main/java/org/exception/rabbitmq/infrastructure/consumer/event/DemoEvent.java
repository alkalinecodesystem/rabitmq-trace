package org.exception.rabbitmq.infrastructure.consumer.event;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DemoEvent {

	private String orderId;

	public DemoEvent() {
	}

	public DemoEvent(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String toJson() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (Exception ex) {
			return "{}";
		}
	}

}
