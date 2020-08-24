package com.apress.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.apress.dto.ProductDTO;

@Component
public class ProductValidator {

	public void validate(ProductDTO productDTO) {
		validateName(productDTO);
		validateCategory(productDTO);
		validatePrice(productDTO);
	}

	private void validateName(ProductDTO productDTO) {
		if (StringUtils.isBlank(productDTO.getName())) {
			productDTO.addError("Name can't be empty");
		}
	}

	private void validateCategory(ProductDTO productDTO) {
		if (StringUtils.isBlank(productDTO.getCategory())) {
			productDTO.addError("Category can't be empty");
		}
	}

	private void validatePrice(ProductDTO productDTO) {
		if (StringUtils.isBlank(String.valueOf(productDTO.getPrice()))) {
			productDTO.addError("Price can't be empty");
		}
	}

}
