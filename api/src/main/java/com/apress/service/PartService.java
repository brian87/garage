package com.apress.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apress.domain.Part;
import com.apress.dto.PartDTO;
import com.apress.repository.PartRepository;
import com.apress.service.defaulter.PartDefaulter;
import com.apress.service.mappers.PartMapper;
import com.apress.validation.PartValidator;

@Service
public class PartService {
	@Autowired
	private PartRepository partRepository;
	@Autowired
	private PartMapper partMapper;
	@Autowired
	private PartDefaulter partDefaulter;
	@Autowired
	private PartValidator partValidator;

	public Collection<PartDTO> findAll() {
		Collection<Part> parts = partRepository.findAll();
		return partMapper.toPartDTOs(parts);
	}

	public Optional<PartDTO> findById(long id) {
		Optional<Part> Part = partRepository.findById(id);
		if (Part.isPresent()) {
			return Optional.of(partMapper.toPartDTO(Part.get()));
		}
		return Optional.empty();
	}

	public boolean existsById(Long id) {
		return partRepository.existsById(id);
	}

	@Transactional
	public PartDTO save(PartDTO partDTO) {
		partDefaulter.populateDefaults(partDTO);
		partValidator.validate(partDTO);
		if (partDTO.hasErrors()) {
			return partDTO;
		}
		Part part = partRepository.save(partMapper.toPart(partDTO));
		return partMapper.toPartDTO(part);
	}

	@Transactional
	public void deleteById(Long id) {
		partRepository.deleteById(id);
	}
}
