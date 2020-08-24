package com.apress.ws.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.apress.dto.LocationDTO;
import com.apress.service.LocationService;
import com.apress.service.mappers.LocationMapper;

import garage.services.geolocation.types.GetLocationRequest;
import garage.services.geolocation.types.GetLocationResponse;
import lombok.extern.slf4j.Slf4j;

@Endpoint
@Slf4j
public class GeoLocationEndpoint {

	@Autowired
	private LocationService locationService;
	@Autowired
	private LocationMapper locationMapper;

	@PayloadRoot(namespace = "urn:garage:services:geolocation:types", localPart = "GetLocationRequest")
	@ResponsePayload
	public GetLocationResponse getLocation(@RequestPayload GetLocationRequest getLocationRequest) {
		log.info("Starting endpoint getLocation");
		LocationDTO locationDTO = locationService.getLocationByIp(getLocationRequest.getIp());
		GetLocationResponse response = locationMapper.toGetLocationResponse(locationDTO);

		return response;

	}

}
