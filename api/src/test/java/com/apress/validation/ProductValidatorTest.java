package com.apress.validation;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.apress.dto.ProductDTO;

@ExtendWith(MockitoExtension.class)
public class ProductValidatorTest {

	@InjectMocks
	private ProductValidator validator;

	@Test
	void shouldHasErrorWhenNameIsNotPresent() {
		ProductDTO productDTO = Mockito.spy(ProductDTO.builder().name(" ").category("base").build());

		validator.validate(productDTO);

		verify(productDTO).addError("Name can't be empty");
	}

	@Test
	void shouldHasErrorWhenCategoryIsNotPresent() {
		ProductDTO productDTO = Mockito.spy(ProductDTO.builder().name("nameProduct").category(" ").build());

		validator.validate(productDTO);

		verify(productDTO).addError("Category can't be empty");
	}

}