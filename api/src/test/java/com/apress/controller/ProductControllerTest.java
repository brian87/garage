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

import com.apress.dto.ProductDTO;
import com.apress.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

	@InjectMocks
	private ProductController controller;
	@Mock
	private ProductService productService;

	@Test
	public void shouldReturnAllProductDTOsWithHttpStatusOk() {
		ProductDTO productDTO = ProductDTO.builder().id(1L).name("Annual Service").build();
		when(productService.findAll()).thenReturn(Arrays.asList(productDTO));

		ResponseEntity<Collection<ProductDTO>> response = controller.findAll();

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().size()).isEqualTo(1);
	}

	@Test
	public void shouldReturnProductDTOById() {
		ProductDTO productDTO = ProductDTO.builder().id(1L).name("Annual Service").build();
		when(productService.findById(1L)).thenReturn(Optional.of(productDTO));

		ResponseEntity<ProductDTO> response = controller.findById(1L);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getId()).isEqualTo(1);
	}

	@Test()
	public void shouldThrowResourceNotFoundExceptionWhenPartIdDoesntExist() {
		when(productService.findById(-1L)).thenReturn(Optional.empty());

		Exception exception = assertThrows(ResponseStatusException.class, () -> {
			controller.findById(-1L);
		});

		assertThat(exception.getMessage()).contains("-1 not found");
	}

	@Test
	public void shouldCreateProductWithHttpStatusCreated() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		ProductDTO productDTO = ProductDTO.builder().id(1L).name("Annual Service").build();
		when(productService.save(any())).thenReturn(productDTO);

		ResponseEntity<Void> response = controller.create(ProductDTO.builder().name("Annual Service").build());

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/1");
	}

	@Test
	public void shouldUpdatedProductWithHttpStatusOk() {
		ProductDTO productDTO = ProductDTO.builder().id(1L).name("Annual Service").build();
		when(productService.existsById(1L)).thenReturn(true);

		ResponseEntity<Void> response = controller.update(ProductDTO.builder().name("Annual Service").build(), 1L);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		verify(productService).save(any());
	}

	@Test
	public void shouldDeleteProductByIdWithHttpStatusAccepted() {
		ProductDTO productDTO = ProductDTO.builder().id(1L).name("Annual Service").build();
		when(productService.existsById(1L)).thenReturn(true);

		ResponseEntity<Void> response = controller.delete(1L);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
		verify(productService).deleteById(1L);
	}

}
