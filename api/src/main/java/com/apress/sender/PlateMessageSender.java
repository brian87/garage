package com.apress.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apress.dto.AuditDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service
public class PlateMessageSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendVehiclePlate(AuditDTO auditDTO) {
		log.info("Sending Vehicle Plate details: {} " + auditDTO);
		rabbitTemplate.convertAndSend("garage.vehicle.plate", auditDTO);
	}

}
