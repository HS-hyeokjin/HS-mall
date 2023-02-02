package com.hsmall.dto;

import com.hsmall.entity.ProductImage;
import org.modelmapper.ModelMapper;

public class ProductImageDto {

    private Long id;

    private String imageName;

    private String originalImageName;

    private String imageUrl;

    private String repImageYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ProductImageDto of(ProductImage productImage){
        return modelMapper.map(productImage,ProductImageDto.class);
    }

}
