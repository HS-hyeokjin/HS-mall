package com.hsmall.dto;

import com.hsmall.constant.ProductCategory;
import com.hsmall.constant.ProductStatus;
import com.hsmall.entity.Product;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ProductFormDto {
    private Long id;

    private ProductCategory productCategory;

    @NotBlank(message = "상품명을 입력해 주세요")
    private String productName;

    @NotNull(message = "상품가격을 입력해 주세요.")
    private Integer productPrice;

    @NotBlank(message = "상품설명을 입력해 주세요.")
    private String productDetail;

    @NotNull(message = "상품재고량을 입력해 주세요.")
    private Integer stockNumber;    //재고

    private ProductStatus productStatus;

    private List<ProductImageDto> productImageDtoList = new ArrayList<>();

    private List<Long> productImagesIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Product registerProduct(){
        return modelMapper.map(this, Product.class);
    }

    public static ProductFormDto of(Product product){
        return modelMapper.map(product, ProductFormDto.class);
    }
}
