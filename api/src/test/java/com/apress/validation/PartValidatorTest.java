package com.apress.validation;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.apress.dto.PartDTO;

@ExtendWith(MockitoExtension.class)
public class PartValidatorTest {

	@InjectMocks
	private PartValidator validator;

	@Test
	void shouldHasErrorWhenCommentIsNotPresent() {
		PartDTO partDTO = Mockito.spy(PartDTO.builder().sku(" ").name("namePart").price(111).build());

		validator.validate(partDTO);

		verify(partDTO).addError("Sku can't be empty");
	}

	@Test
	void shouldHasErrorWhenNameIsNotPresent() {
		PartDTO partDTO = Mockito.spy(PartDTO.builder().sku("XXX-111").name(" ").price(111).build());

		validator.validate(partDTO);

		verify(partDTO).addError("Name can't be empty");
	}

}