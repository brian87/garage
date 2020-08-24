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

import com.apress.dto.PartDTO;
import com.apress.service.PartService;

@ExtendWith(MockitoExtension.class)
public class PartControllerTest {

	@InjectMocks
	private PartController controller;
	@Mock
	private PartService partService;

	@Test
	public void shouldReturnAllPartDTOsWithHttpStatusOk() {
		PartDTO partDTO = PartDTO.builder().id(1L).name("Engine motor oil").build();
		when(partService.findAll()).thenReturn(Arrays.asList(partDTO));

		ResponseEntity<Collection<PartDTO>> response = controller.findAll();

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().size()).isEqualTo(1);
	}

	@Test
	public void shouldReturnPartDTOById() {
		PartDTO partDTO = PartDTO.builder().id(1L).name("Engine motor oil").build();
		when(partService.findById(1L)).thenReturn(Optional.of(partDTO));

		ResponseEntity<PartDTO> response = controller.findById(1L);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getId()).isEqualTo(1);
	}

	@Test()
	public void shouldThrowResourceNotFoundExceptionWhenPartIdDoesntExist() {
		when(partService.findById(-1L)).thenReturn(Optional.empty());

		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			controller.findById(-1L);
		});

		assertThat(exception.getMessage()).contains("-1 not found");
	}

	@Test
	public void shouldCreatePartWithHttpStatusCreated() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		PartDTO partDTO = PartDTO.builder().id(1L).name("Engine motor oil").build();
		when(partService.save(any())).thenReturn(partDTO);

		ResponseEntity<Void> response = controller.create(PartDTO.builder().name("Engine motor oil").build());

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/1");
	}

	@Test
	public void shouldUpdatedPartWithHttpStatusOk() {
		PartDTO partDTO = PartDTO.builder().id(1L).name("Engine motor oil").build();
		when(partService.existsById(1L)).thenReturn(true);

		ResponseEntity<Void> response = controller.update(PartDTO.builder().name("Engine motor oil").build(), 1L);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		verify(partService).save(any());
	}

	@Test
	public void shouldDeletePartByIdWithHttpStatusAccepted() {
		PartDTO partDTO = PartDTO.builder().id(1L).name("Engine motor oil").build();
		when(partService.existsById(1L)).thenReturn(true);

		ResponseEntity<Void> response = controller.delete(1L);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
		verify(partService).deleteById(1L);
	}

}
