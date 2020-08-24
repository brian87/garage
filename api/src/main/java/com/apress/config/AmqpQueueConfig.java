package com.apress.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpQueueConfig {

	@Bean
	public Queue plateQueue() {
		return new Queue("garage.vehicle.plate", false);
	}
}