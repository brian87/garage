package com.apress.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Setter
@Getter

public class PartDTO extends BaseDTO {

	private Long id;

	private String name;

	private Integer price;

	private String sku;

}