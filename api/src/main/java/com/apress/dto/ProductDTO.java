package com.apress.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO extends BaseDTO {

	private Long id;

	private String name;

	private String category;

	private String reference;

	private Integer price;

}
