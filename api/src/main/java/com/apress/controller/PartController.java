package com.apress.controller;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apress.dto.PartDTO;
import com.apress.service.PartService;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@RestController
@RequestMapping("/vehicles/parts")
public class PartController {

	@Autowired
	private PartService partService;

	@GetMapping()
	public ResponseEntity<Collection<PartDTO>> findAll() {
		Collection<PartDTO> partDTOs = partService.findAll();
		return new ResponseEntity<>(partDTOs, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PartDTO> findById(@PathVariable Long id) {
		Optional<PartDTO> partDTO = partService.findById(id);
		if (!partDTO.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("%s not found", id));
		}
		return new ResponseEntity<>(partDTO.get(), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<Void> create(@RequestBody PartDTO partDTO) {
		partDTO = partService.save(partDTO.toBuilder().id(null).build());
		if (partDTO.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, partDTO.getErrors());
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(buildLocationUri(partDTO.getId()));
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}
	}

	private URI buildLocationUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody PartDTO partDTO, @PathVariable Long id) {
		if (!partService.existsById(id)) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		partDTO.setId(id);
		partService.save(partDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!partService.existsById(id)) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		partService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}