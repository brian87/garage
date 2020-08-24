package com.apress.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.apress.dto.PartDTO;

@Component
public class PartValidator {

	public void validate(PartDTO bookingDTO) {
		validateComments(bookingDTO);
		validateName(bookingDTO);
		validatePrice(bookingDTO);
	}

	private void validateComments(PartDTO bookingDTO) {
		if (StringUtils.isBlank(bookingDTO.getSku())) {
			bookingDTO.addError("Sku can't be empty");
		}
	}

	private void validateName(PartDTO bookingDTO) {
		if (StringUtils.isBlank(bookingDTO.getName())) {
			bookingDTO.addError("Name can't be empty");
		}
	}

	private void validatePrice(PartDTO bookingDTO) {
		if (StringUtils.isBlank(String.valueOf(bookingDTO.getPrice()))) {
			bookingDTO.addError("Price can't be empty");
		}
	}

}
