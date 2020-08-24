package com.apress.validation;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.apress.dto.UserDTO;

@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {

	@InjectMocks
	private UserValidator validator;

	@Test
	void shouldHasErrorWhenEmailIsNotPresent() {
		UserDTO userDTO = Mockito.spy(UserDTO.builder().email(" ").fullName("Administrador").mobile("999-999-999")
				.password("admin").role("admin").build());

		validator.validate(userDTO);

		verify(userDTO).addError("Email can't be empty");
	}

	@Test
	void shouldHasErrorWhenNamelIsNotPresent() {
		UserDTO userDTO = Mockito.spy(UserDTO.builder().email("admin@garage.com").fullName(" ").mobile("999-999-999")
				.password("admin").role("admin").build());

		validator.validate(userDTO);

		verify(userDTO).addError("Name can't be empty");
	}

	@Test
	void shouldHasErrorWhenMobilelIsNotPresent() {
		UserDTO userDTO = Mockito.spy(UserDTO.builder().email("admin@garage.com").fullName("admin").mobile(" ")
				.password("admin").role("admin").build());

		validator.validate(userDTO);

		verify(userDTO).addError("Mobile can't be empty");
	}

	@Test
	void shouldHasErrorWhenPasswordlIsNotPresent() {
		UserDTO userDTO = Mockito.spy(UserDTO.builder().email("admin@garage.com").fullName("admin")
				.mobile("999-999-999").password(" ").role("admin").build());

		validator.validate(userDTO);

		verify(userDTO).addError("Password can't be empty");
	}

	@Test
	void shouldHasErrorWhenRolelIsNotPresent() {
		UserDTO userDTO = Mockito.spy(UserDTO.builder().email("admin@garage.com").fullName("admin")
				.mobile("999-999-999").password("admin").role(" ").build());

		validator.validate(userDTO);

		verify(userDTO).addError("Role can't be empty");
	}
}
