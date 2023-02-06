package com.hsmall.service;

import com.hsmall.dto.ProductFormDto;
import com.hsmall.dto.ProductImageDto;
import com.hsmall.dto.MainProductDto;
import com.hsmall.dto.ProductSearchDto;
import com.hsmall.entity.Product;
import com.hsmall.entity.ProductImage;
import com.hsmall.repository.ProductImageRepository;
import com.hsmall.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductImageService productImageService;

    private final ProductImageRepository productImageRepository;

    public Long saveProduct(ProductFormDto productFormDto, List<MultipartFile> productImageFileList) throws Exception {

        //상품 등록
        Product product = productFormDto.registerProduct();
        productRepository.save(product);

        //이미지 등록
        for (int i = 0; i < productImageFileList.size(); i++) {
            ProductImage productImage = new ProductImage();
            productImage.setProduct(product);

            if (i == 0)
                productImage.setRepImageYn("Y");
            else
                productImage.setRepImageYn("N");

            productImageService.saveProductImage(productImage, productImageFileList.get(i));
        }

        return product.getId();
    }

    @Transactional(readOnly = true)
    public ProductFormDto getProductDtl(Long productId) {
        List<ProductImage> productImageList = productImageRepository.findByProductIdOrderByIdAsc(productId);
        List<ProductImageDto> productImageDtoList = new ArrayList<>();
        for (ProductImage productImage : productImageList) {
            ProductImageDto productImageDto = ProductImageDto.of(productImage);
            productImageDtoList.add(productImageDto);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);
        ProductFormDto productFormDto = ProductFormDto.of(product);
        productFormDto.setProductImageDtoList(productImageDtoList);
        return productFormDto;
    }

    public Long updateProduct(ProductFormDto productFormDto, List<MultipartFile> productImageFileList) throws Exception {
        //상품 수정
        Product product = productRepository.findById(productFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        product.updateProduct(productFormDto);
        List<Long> productImageIds = productFormDto.getProductImagesIds();

        //이미지 등록
        for (int i = 0; i < productImageFileList.size(); i++) {
            productImageService.registerProductImage(productImageIds.get(i),
                    productImageFileList.get(i));
        }

        return product.getId();
    }

    @Transactional(readOnly = true)
    public Page<Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable){
        return productRepository.getAdminProductPage(productSearchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainProductDto> getMainProductPage(ProductSearchDto productSearchDto,Pageable pageable){
        return productRepository.getMainProductPage(productSearchDto, pageable);
    }
}
