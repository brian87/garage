package com.apress.service.mappers;

import org.mapstruct.Mapper;

import com.apress.dto.LocationDTO;

import garage.services.geolocation.types.GetLocationResponse;

@Mapper(componentModel = "spring")
public interface LocationMapper {

	GetLocationResponse toGetLocationResponse(LocationDTO LocationDTO);
}
