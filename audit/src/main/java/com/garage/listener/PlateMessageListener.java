package com.garage.listener;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.garage.dto.AuditDTO;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
public class PlateMessageListener {

	@RabbitListener(queues = "garage.vehicle.plate", ackMode = "MANUAL")
	public void receiveMessage(final AuditDTO auditDTO, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
		log.info("Received message: {} Tag: {}", auditDTO.getVehiculeNumberPlate(), tag);
		RestTemplate restTemplate = new RestTemplate();

		String url = "https://httpbin.org//delay/5";
		if (Math.random() < 0.5) {
			url = "https://httpbin.org/status/201";
		}
		log.info("Invoking url: {}", url);

		ResponseEntity<String> response = restTemplate.postForEntity(url, auditDTO, String.class);

		log.info("Response from httpbin: {}", response.getStatusCode().getReasonPhrase());

		if (response.getStatusCode().equals(HttpStatus.OK)) {
			try {
				log.info("Ask-ing {}", tag);
				channel.basicAck(tag, false);
			} catch (IOException e) {
				log.warn("It was not possible to complete ACK");
			}
		} else {
			try {
				log.info("Nack-ing and re-queue {}", tag);
				channel.basicNack(tag, false, true);
			} catch (IOException e) {
				log.warn("It was not possible to complete ACK");
			}
		}
	}

}
