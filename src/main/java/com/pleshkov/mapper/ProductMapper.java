package com.pleshkov.mapper;

import com.pleshkov.model.dto.ProductDto;
import com.pleshkov.model.entity.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    Product productDtoToProduct(ProductDto productDto);

    ProductDto productToProductDto(Product product);
}
