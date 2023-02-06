package com.hsmall.repository;

import com.hsmall.dto.MainProductDto;
import com.hsmall.dto.ProductSearchDto;
import com.hsmall.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable);

    Page<MainProductDto> getMainProductPage(ProductSearchDto productSearchDto, Pageable pageable);


}
