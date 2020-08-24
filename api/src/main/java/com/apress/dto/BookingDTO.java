package com.apress.dto;

import java.time.LocalDate;
import java.util.Set;

import com.apress.domain.Part;
import com.apress.domain.Product;
import com.apress.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Setter
@Getter

public class BookingDTO extends BaseDTO {

	private Long id;

	@JsonProperty("reference")
	private String reference;

	@JsonProperty("vehicule_number_plate")
	private String vehiculeNumberPlate;

	@JsonProperty("vehicule_model")
	private String vehiculeModel;

	@JsonProperty("vehicule_brand")
	private String vehiculeBrand;

	@JsonProperty("vehicule_engine")
	private String vehiculeEngine;

	@JsonProperty("status")
	private String status;

	@JsonProperty("service_id")
	private Product serviceId;

	@JsonProperty("service_ids")
	private Set<Product> products;

	@JsonProperty("part_ids")
	private Set<Part> parts;

	@JsonProperty("mechanic_id")
	private User mechanicId;

	@JsonProperty("customer_id")
	private User customerId;

	private LocalDate date;

	private String comments;

	private String countryCode;

	private String vatNumber;

	@JsonIgnore
	private String ipSource;

	@JsonIgnore
	private String ipCountry;

	@JsonIgnore
	private String ipCity;
}
