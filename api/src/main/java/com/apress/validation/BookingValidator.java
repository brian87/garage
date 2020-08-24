package com.apress.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apress.client.VatServiceClient;
import com.apress.dto.BookingDTO;

import eu.europa.ec.taxud.vies.services.checkvat.types.CheckVat;
import eu.europa.ec.taxud.vies.services.checkvat.types.CheckVatResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Component
public class BookingValidator {

	@Autowired
	VatServiceClient client;

	public void validate(BookingDTO bookingDTO) {
		validateComments(bookingDTO);
		validateVehiculeNumberPlate(bookingDTO);
		validateStatus(bookingDTO);
		validateVatNumberAndCountryCode(bookingDTO);
	}

	private void validateComments(BookingDTO bookingDTO) {
		if (StringUtils.isBlank(bookingDTO.getComments())) {
			bookingDTO.addError("Comments can't be empty");
		}
	}

	private void validateVehiculeNumberPlate(BookingDTO bookingDTO) {
		if (StringUtils.isBlank(bookingDTO.getVehiculeNumberPlate())) {
			bookingDTO.addError("Vehicule number plate can't be empty");
		}
	}

	private void validateStatus(BookingDTO bookingDTO) {
		if (StringUtils.isBlank(bookingDTO.getStatus())) {
			bookingDTO.addError("Status can't be empty");
		}
	}

	private void validateVatNumberAndCountryCode(BookingDTO bookingDTO) {
		if (!StringUtils.isBlank(bookingDTO.getVatNumber()) && !StringUtils.isBlank(bookingDTO.getCountryCode())) {
			CheckVat checkVat = new CheckVat();
			checkVat.setCountryCode(bookingDTO.getCountryCode());
			checkVat.setVatNumber(bookingDTO.getVatNumber());
			CheckVatResponse checkVatResponse = client.checkVat(checkVat);
			if(checkVatResponse == null) {
				log.warn("Vies Service unavailable, vat validation skiped");
			}else if (!checkVatResponse.isValid()) {
				bookingDTO.addError("Invalid vatNumber");
			}
		}
	}

}
