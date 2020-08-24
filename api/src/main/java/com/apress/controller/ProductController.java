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

import com.apress.dto.ProductDTO;
import com.apress.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j

@RestController
@RequestMapping("/services")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping()
	public ResponseEntity<Collection<ProductDTO>> findAll() {
		Collection<ProductDTO> productDTOs = productService.findAll();
		return ResponseEntity.ok().body(productDTOs);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		Optional<ProductDTO> productDTO = productService.findById(id);
		if (!productDTO.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("%s not found", id));
		}
		return new ResponseEntity<>(productDTO.get(), HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<Void> create(@RequestBody ProductDTO productDTO) {
		productDTO = productService.save(productDTO.toBuilder().id(null).build());
		if (productDTO.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, productDTO.getErrors());
		} else {
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(buildLocationUri(productDTO.getId()));
			return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}
	}

	private URI buildLocationUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@RequestBody ProductDTO productDTO, @PathVariable Long id) {
		if (!productService.existsById(id)) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		productDTO.setId(id);
		productService.save(productDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!productService.existsById(id)) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		productService.deleteById(id);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

}