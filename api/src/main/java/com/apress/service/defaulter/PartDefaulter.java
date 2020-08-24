package com.apress.service.defaulter;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.apress.dto.PartDTO;

@Component
public class PartDefaulter {

	public void populateDefaults(PartDTO partDTO) {
		populateReference(partDTO);
	}

	public void populateReference(PartDTO partDTO) {
		partDTO.setSku(UUID.randomUUID().toString());
	}

}