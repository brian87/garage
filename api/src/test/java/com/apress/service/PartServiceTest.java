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

import com.apress.domain.Part;
import com.apress.dto.PartDTO;
import com.apress.repository.PartRepository;
import com.apress.service.defaulter.PartDefaulter;
import com.apress.service.mappers.PartMapper;
import com.apress.validation.PartValidator;

@ExtendWith(MockitoExtension.class)
public class PartServiceTest {

	@InjectMocks
	private PartService service;

	@Mock
	private PartRepository partRepository;
	@Mock
	private PartMapper partMapper;
	@Mock
	private PartDefaulter partDefaulter;
	@Mock
	private PartValidator partValidator;

	@Test
	void shouldReturnAllParts() {
		PartDTO partDTO = PartDTO.builder().id(1L).name("Engine motor oil").build();
		when(partMapper.toPartDTOs(any())).thenReturn(Arrays.asList(partDTO));

		Collection<PartDTO> partDTOs = service.findAll();

		assertThat(partDTOs.size()).isEqualTo(1);
	}

	@Test
	void shouldReturnPartById() {
		PartDTO partDTO = PartDTO.builder().id(1L).name("Engine motor oil").build();
		when(partRepository.findById(1L)).thenReturn(Optional.of(new Part()));
		when(partMapper.toPartDTO(any())).thenReturn(partDTO);

		Optional<PartDTO> returnedPartDTO = service.findById(1L);

		assertThat(returnedPartDTO.isPresent()).isTrue();
		assertThat(returnedPartDTO).isEqualTo(Optional.of(partDTO));
	}

	@Test
	void shouldSavePart() {
		PartDTO partDTO = PartDTO.builder().id(1L).name("Engine motor oil").build();
		when(partMapper.toPartDTO(any())).thenReturn(partDTO);

		PartDTO returnedPartDTO = service.save(partDTO);

		assertThat(returnedPartDTO).isNotNull();
		verify(partRepository).save(any());
	}

	@Test
	void shouldDeletePartById() {
		service.deleteById(1L);

		verify(partRepository).deleteById(1L);
	}

}
