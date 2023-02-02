package com.hsmall.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="product_image")
@Getter @Setter
public class ProductImage extends BaseEntity {

    @Id
    @Column(name = "product_image_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imageName;

    private String originalImageName;

    private String imageUrl;

    private String repImageYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void registerProductImage(String originalImageName, String imageName, String imageUrl){
        this.originalImageName = originalImageName;
        this.imageName = imageName;
        this.imageUrl = imageUrl;

    }
}
