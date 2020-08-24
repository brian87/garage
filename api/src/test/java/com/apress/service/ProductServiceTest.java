package com.apress.service;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.apress.domain.Product;
import com.apress.dto.ProductDTO;
import com.apress.repository.ProductRepository;
import com.apress.service.defaulter.ProductDefaulter;
import com.apress.service.mappers.ProductMapper;
import com.apress.validation.ProductValidator;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository productRepository;
	@Mock
	private ProductMapper productMapper;
	@Mock
	private ProductValidator productValidator;
	@Mock
	private ProductDefaulter productDefaulter;

	@Test
	void shouldReturnAllProducts() {
		ProductDTO productDTO = ProductDTO.builder().id(1L).name("Annual Service").build();
		when(productMapper.toProductDTOs(any())).thenReturn(Arrays.asList(productDTO));

		Collection<ProductDTO> productDTOs = service.findAll();

		assertThat(productDTOs.size()).isEqualTo(1);
	}

	@Test
	void shouldReturnProductById() {
		ProductDTO productDTO = ProductDTO.builder().id(1L).name("Annual Service").build();
		when(productRepository.findById(1L)).thenReturn(Optional.of(new Product()));
		when(productMapper.toProductDTO(any())).thenReturn(productDTO);

		Optional<ProductDTO> returnedProductDTO = service.findById(1L);

		assertThat(returnedProductDTO.isPresent()).isTrue();
		assertThat(returnedProductDTO).isEqualTo(Optional.of(productDTO));
	}

	@Test
	void shouldSaveProduct() {
		ProductDTO productDTO = ProductDTO.builder().id(1L).name("Annual Service").build();
		when(productMapper.toProductDTO(any())).thenReturn(productDTO);

		ProductDTO returnedProductDTO = service.save(productDTO);

		assertThat(returnedProductDTO).isNotNull();
		verify(productRepository).save(any());
	}

	@Test
	void shouldDeleteProductById() {
		service.deleteById(1L);

		verify(productRepository).deleteById(1L);
	}

}
