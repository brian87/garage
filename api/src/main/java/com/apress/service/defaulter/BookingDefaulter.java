package com.apress.service.defaulter;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apress.client.GeoLocationClient;
import com.apress.dto.BookingDTO;

import garage.services.geolocation.types.GetLocationRequest;
import garage.services.geolocation.types.GetLocationResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Component
public class BookingDefaulter {

	@Autowired
	private GeoLocationClient geoLocationClient;

	public void populateDefaults(BookingDTO bookingDTO) {
		populateReference(bookingDTO);
		populateCountryAndCity(bookingDTO);
	}

	public void populateReference(BookingDTO bookingDTO) {
		if (bookingDTO.getReference() == null) {
			bookingDTO.setReference(UUID.randomUUID().toString());
		}
	}

	public void populateCountryAndCity(BookingDTO bookingDTO) {
		GetLocationRequest getLocationRequest = new GetLocationRequest();
		getLocationRequest.setIp(bookingDTO.getIpSource());
		GetLocationResponse getLocationResponse = geoLocationClient.getLocation(getLocationRequest);
		if (getLocationResponse != null && getLocationResponse.getStatus().equals("success")) {
			bookingDTO.setIpCountry(getLocationResponse.getCountry());
			bookingDTO.setIpCity(getLocationResponse.getCity());
		} else {
			bookingDTO.setIpCountry("Unknown");
			bookingDTO.setIpCity("Unknown");
		}

	}
}
