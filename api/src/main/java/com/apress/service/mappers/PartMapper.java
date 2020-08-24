package com.apress.service.mappers;

import java.util.Collection;

import org.mapstruct.Mapper;

import com.apress.domain.Part;
import com.apress.dto.PartDTO;

@Mapper(componentModel = "spring")
public interface PartMapper {

	public PartDTO toPartDTO(Part part);

	public Part toPart(PartDTO partDTO);

	public Collection<PartDTO> toPartDTOs(Collection<Part> parts);

}