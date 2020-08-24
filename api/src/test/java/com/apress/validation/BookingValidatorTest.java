package com.apress.validation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.apress.client.VatServiceClient;
import com.apress.dto.BookingDTO;

import eu.europa.ec.taxud.vies.services.checkvat.types.CheckVatResponse;

@ExtendWith(MockitoExtension.class)
public class BookingValidatorTest {

	@InjectMocks
	private BookingValidator validator;

	@Mock
	private VatServiceClient client;

	@Test
	void shouldHasErrorWhenCommentIsNotPresent() {
		BookingDTO bookingDTO = Mockito
				.spy(BookingDTO.builder().comments(" ").vehiculeNumberPlate("AAA-111").status("booked").build());

		validator.validate(bookingDTO);

		verify(bookingDTO).addError("Comments can't be empty");

	}

	@Test
	void shouldHasErrorWhenVehiculeNumberPlateIsNotPresent() {
		BookingDTO bookingDTO = Mockito
				.spy(BookingDTO.builder().comments("comment").vehiculeNumberPlate(" ").status("booked").build());

		validator.validate(bookingDTO);

		verify(bookingDTO).addError("Vehicule number plate can't be empty");
	}

	@Test
	void shouldHasErrorWhenVatNumberIsNotValid() {
		BookingDTO bookingDTO = Mockito.spy(BookingDTO.builder().comments("comment").vehiculeNumberPlate("AAA-111")
				.status("booked").countryCode("ES").vatNumber("1234567").build());
		CheckVatResponse checkVatResponse = new CheckVatResponse();
		checkVatResponse.setValid(false);
		when(client.checkVat(any())).thenReturn(checkVatResponse);

		validator.validate(bookingDTO);

		verify(bookingDTO).addError("Invalid vatNumber");
	}

	@Test
	void shouldHasErrorWhenVatNumberIsValid() {
		BookingDTO bookingDTO = Mockito.spy(BookingDTO.builder().comments("comment").vehiculeNumberPlate("AAA-111")
				.status("booked").countryCode("ES").vatNumber("1234567").build());
		CheckVatResponse checkVatResponse = new CheckVatResponse();
		checkVatResponse.setValid(true);
		when(client.checkVat(any())).thenReturn(checkVatResponse);

		validator.validate(bookingDTO);

		verify(bookingDTO, never()).addError(any());
	}

}
