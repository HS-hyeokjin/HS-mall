package com.hsmall.service;

import com.hsmall.entity.ProductImage;
import com.hsmall.repository.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImageService {

    @Value("${productImageLocation}")
    private String productImageLocation;

    private final ProductImageRepository productImageRepository;

    private final FileService fileService;

    public void saveProductImage(ProductImage productImage, MultipartFile productImageFile) throws Exception{
        String originalImageName = productImageFile.getOriginalFilename();
        String imageName = "";
        String imageUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(originalImageName)){
            imageName = fileService.uploadFile(productImageLocation, originalImageName,
                    productImageFile.getBytes());
            imageUrl = "/images/product/" + imageName;
        }

        //상품 이미지 정보 저장
        productImage.registerProductImage(originalImageName, imageName, imageUrl);
        productImageRepository.save(productImage);
    }
    public void registerProductImage(Long productImageId, MultipartFile productImageFile) throws Exception{
        if(!productImageFile.isEmpty()){
            ProductImage savedProductImage = productImageRepository.findById(productImageId)
                    .orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedProductImage.getImageName())) {
                fileService.deleteFile(productImageLocation+"/"+
                        savedProductImage.getImageName());
            }

            String originalImageName = productImageFile.getOriginalFilename();
            String imageName = fileService.uploadFile(productImageLocation, originalImageName, productImageFile.getBytes());
            String imageUrl = "/images/product/" + imageName;
            savedProductImage.registerProductImage(originalImageName, imageName, imageUrl);
        }
    }
}
