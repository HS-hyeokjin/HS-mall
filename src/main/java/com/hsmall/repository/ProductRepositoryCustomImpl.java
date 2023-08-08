package com.hsmall.repository;

import com.hsmall.constant.ProductCategory;
import com.hsmall.constant.ProductStatus;
import com.hsmall.dto.MainProductDto;
import com.hsmall.dto.ProductSearchDto;
import com.hsmall.dto.QMainProductDto;
import com.hsmall.entity.Product;
import com.hsmall.entity.QProduct;
import com.hsmall.entity.QProductImage;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public ProductRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchProductStatusEq(ProductStatus searchProductStatus){
        return searchProductStatus == null ? null : QProduct.product.productStatus.eq(searchProductStatus);
    }
    private BooleanExpression regDtsAfter(String searchDateType){

        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }

        return QProduct.product.regTime.after(dateTime);
    }

    private com.querydsl.core.types.dsl.BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("productName", searchBy)){
            return QProduct.product.productName.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)){
            return QProduct.product.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Product> getAdminProductPage(ProductSearchDto productSearchDto, Pageable pageable) {

        QueryResults<Product> results = queryFactory
                .selectFrom(QProduct.product)
                .where(regDtsAfter(productSearchDto.getSearchDateType()),
                        searchProductStatusEq(productSearchDto.getProductStatus()),
                        searchByLike(productSearchDto.getSearchBy(), productSearchDto.getSearchQuery()))
                .orderBy(QProduct.product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Product> content = results.getResults();
        Long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression productNameLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QProduct.product.productName.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainProductDto> getMainProductPage(ProductSearchDto productSearchDto, Pageable pageable) {
        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        List<MainProductDto> content = queryFactory
                .select(
                        new QMainProductDto(
                                product.id,
                                product.productName,
                                product.productDetail,
                                productImage.imageUrl,
                                product.productPrice,
                                product.productCategory
                                )
                )
                .from(productImage)
                .join(productImage.product, product)
                .where(productImage.repImageYn.eq("Y"))
                .where(productNameLike(productSearchDto.getSearchQuery()))
                .where(product.productCategory.eq(ProductCategory.outer))
                .orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(productImage)
                .join(productImage.product, product)
                .where(productImage.repImageYn.eq("Y"))
                .where(productNameLike(productSearchDto.getSearchQuery()))
                .where(product.productCategory.eq(ProductCategory.outer))
                .fetchOne()
                ;

        return new PageImpl<>(content, pageable, total);
    }

}


