package com.apress.service.mappers;

import java.util.Collection;

import org.mapstruct.Mapper;

import com.apress.domain.Product;
import com.apress.dto.ProductDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	public ProductDTO toProductDTO(Product product);

	public Product toProduct(ProductDTO productDTO);

	public Collection<ProductDTO> toProductDTOs(Collection<Product> products);

}
