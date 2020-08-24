package com.apress.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import com.apress.dto.UserDTO;
import com.apress.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@InjectMocks
	private UserController controller;
	@Mock
	private UserService userService;

	@Test
	public void shouldReturnAllUserDTOsWithHttpStatusOk() {
		UserDTO userDTO = UserDTO.builder().id(1L).mobile("12345678").build();
		when(userService.findAll()).thenReturn(Arrays.asList(userDTO));

		ResponseEntity<Collection<UserDTO>> response = controller.findAll();

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.hasBody()).isTrue();
		assertThat(response.getBody().size()).isEqualTo(1);
	}

	@Test
	public void shouldReturnuserDTOById() {
		UserDTO userDTO = UserDTO.builder().id(1L).mobile("comment").build();
		when(userService.findById(1L)).thenReturn(Optional.of(userDTO));

		ResponseEntity<UserDTO> response = controller.findById(1L);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getId()).isEqualTo(1);
	}

	@Test
	public void shouldThrowResourceNotFoundExceptionWhenPartIdDoesntExist() {
		when(userService.findById(-1L)).thenReturn(Optional.empty());

		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			controller.findById(-1L);
		});

		assertThat(exception.getMessage()).contains("-1 not found");
	}

	@Test
	public void shouldCreateBookingWithHttpStatusCreated() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		UserDTO userDTO = UserDTO.builder().id(1L).mobile("comment").build();
		when(userService.save(any())).thenReturn(userDTO);

		ResponseEntity<Void> response = controller.create(UserDTO.builder().mobile("12345678").build());

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/1");
	}

	@Test
	public void shouldUpdatedUserWithHttpStatusOk() {
		UserDTO userDTO = UserDTO.builder().id(1L).mobile("12345678").build();
		when(userService.existsById(1L)).thenReturn(true);

		ResponseEntity<Void> response = controller.update(UserDTO.builder().mobile("12345678").build(), 1L);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		verify(userService).save(any());
	}

	@Test
	public void shouldDeleteUserByIdWithHttpStatusAccepted() {
		UserDTO userDTO = UserDTO.builder().id(1L).mobile("12345678").build();
		when(userService.existsById(1L)).thenReturn(true);

		ResponseEntity<Void> response = controller.delete(1L);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
		verify(userService).deleteById(1L);
	}

}
