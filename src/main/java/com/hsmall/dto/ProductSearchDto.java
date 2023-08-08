package com.hsmall.dto;

import com.hsmall.constant.ProductCategory;
import com.hsmall.constant.ProductStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductSearchDto {

    private ProductCategory productCategory;

    private String searchDateType;

    private ProductStatus productStatus;

    private String searchBy;

    private String searchQuery = "";

}
