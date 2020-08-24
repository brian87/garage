package com.garage.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class AuditDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("booking_id")
	private Long id;

	@JsonProperty("vehicle_number_plate")
	private String vehiculeNumberPlate;

}
