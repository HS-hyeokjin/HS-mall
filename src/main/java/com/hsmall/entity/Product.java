package com.hsmall.entity;

import com.hsmall.constant.ProductCategory;
import com.hsmall.constant.ProductStatus;
import com.hsmall.dto.ProductFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="product")
@Getter
@Setter
@ToString
public class Product extends BaseEntity{

    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="product_category")
    private ProductCategory productCategory;

    private String productName;

    private Integer stockNumber;

    @Column(name="product_price", nullable = false)
    private int productPrice;

    private String productDetail;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    public void updateProduct(ProductFormDto productFormDto){
        this.productName = productFormDto.getProductName();
        this.productPrice = productFormDto.getProductPrice();
        this.stockNumber = productFormDto.getStockNumber();
        this.productDetail = productFormDto.getProductDetail();
        this.productStatus = productFormDto.getProductStatus();
        this.productCategory = productFormDto.getProductCategory();
    }

}
