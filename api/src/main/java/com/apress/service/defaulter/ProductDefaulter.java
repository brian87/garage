package com.apress.service.defaulter;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.apress.dto.ProductDTO;

@Component
public class ProductDefaulter {

	public void populateDefaults(ProductDTO productDTO) {
		populateReference(productDTO);
	}

	public void populateReference(ProductDTO productDTO) {
		productDTO.setReference(UUID.randomUUID().toString());
	}

}