package com.hsmall.entity;

import com.hsmall.constant.ProductStatus;
import com.hsmall.constant.ProductType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="product")
@Getter
@Setter
public class Product{

    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    private String productName;

    private int productPrice;

    private String productDetail;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

}
