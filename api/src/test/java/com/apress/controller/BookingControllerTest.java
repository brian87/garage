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

import com.apress.dto.BookingDTO;
import com.apress.service.BookingService;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

	@InjectMocks
	private BookingController controller;
	@Mock
	private BookingService bookingService;

	@Test
	public void shouldReturnAllBookingDTOsWithHttpStatusOk() {
		BookingDTO bookingDTO = BookingDTO.builder().id(1L).comments("comment").build();
		when(bookingService.findAll()).thenReturn(Arrays.asList(bookingDTO));

		ResponseEntity<Collection<BookingDTO>> response = controller.findAll();

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().size()).isEqualTo(1);
	}

	@Test
	public void shouldReturnBookingDTOById() {
		BookingDTO bookingDTO = BookingDTO.builder().id(1L).comments("comment").build();
		when(bookingService.findById(1L)).thenReturn(Optional.of(bookingDTO));

		ResponseEntity<BookingDTO> response = controller.findById(1L);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getId()).isEqualTo(1);
	}

	@Test
	public void shouldThrowResourceNotFoundExceptionWhenBookingIdDoesntExist() {
		when(bookingService.findById(-1L)).thenReturn(Optional.empty());

		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			controller.findById(-1L);
		});

		assertThat(exception.getMessage()).contains("-1 not found");
	}

	@Test
	public void shouldCreateBookingWithHttpStatusCreated() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		BookingDTO bookingDTO = BookingDTO.builder().id(1L).comments("comment").build();
		when(bookingService.save(any())).thenReturn(bookingDTO);

		ResponseEntity<?> response = controller.create(BookingDTO.builder().comments("comment").build(), request);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/1");
	}

	@Test
	public void shouldUpdatedBookingWithHttpStatusOk() {
		when(bookingService.existsById(1L)).thenReturn(true);

		ResponseEntity<Void> response = controller.update(BookingDTO.builder().comments("comment").build(), 1L);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		verify(bookingService).save(any());
	}

	@Test
	public void shouldDeleteBookingByIdWithHttpStatusAccepted() {
		when(bookingService.existsById(1L)).thenReturn(true);

		ResponseEntity<Void> response = controller.delete(1L);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
		verify(bookingService).deleteById(1L);
	}

}
