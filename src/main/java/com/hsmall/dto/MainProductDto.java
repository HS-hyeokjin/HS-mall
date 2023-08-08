package com.hsmall.dto;

import com.hsmall.constant.ProductCategory;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainProductDto {
    private Long id;
    private String productName;
    private String productDetail;
    private String imageUrl;
    private Integer productPrice;
    private ProductCategory productCategory;

    @QueryProjection
    public MainProductDto(Long id, String productName, String productDetail, String imageUrl, Integer productPrice, ProductCategory productCategory)
    {this.id = id;
    this.productName = productName;
    this.productDetail = productDetail;
    this.imageUrl = imageUrl;
    this.productPrice = productPrice;
    this.productCategory = productCategory;
    }

}
