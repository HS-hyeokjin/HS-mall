package com.hsmall.controller;

import com.hsmall.constant.ProductCategory;
import com.hsmall.dto.MainProductDto;
import com.hsmall.dto.ProductFormDto;
import com.hsmall.dto.ProductSearchDto;
import com.hsmall.entity.Product;
import com.hsmall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    //상품등록
    @GetMapping(value = "admin/product/register")
    public String productForm(Model model) {
        model.addAttribute("productFormDto", new ProductFormDto());
        return "product/productRegisterForm";
    }

    @PostMapping(value = "/admin/product/register")
    public String productRegister(@Valid ProductFormDto productFormDto, BindingResult bindingResult, Model model,
                                  @RequestParam("productImageFile") List<MultipartFile> productImageFileList) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "에러가 발생했습니다.");
            return "product/productRegisterForm";
        }
        if (productImageFileList.get(0).isEmpty() && productFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "product/productRegisterForm";
        }

        try {
            productService.saveProduct(productFormDto, productImageFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "product/productRegisterForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "admin/product/{productId}")
    public String productDtl(@PathVariable("productID") Long productId, Model model) {

        try {
            ProductFormDto productFormDto = productService.getProductDtl(productId);
            model.addAttribute("productFormDto", productFormDto);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("productFormDto", new ProductFormDto());
            return "product/productForm";
        }

        return "product/productForm";
    }

    @PostMapping(value = "/admin/product/{productId}")
    public String productUpdate(@Valid ProductFormDto productFormDto, BindingResult bindingResult,
                             @RequestParam("productImageFile") List<MultipartFile> productImageFileList, Model model){
        if(bindingResult.hasErrors()){
            return "product/productForm";
        }

        if(productImageFileList.get(0).isEmpty() && productFormDto.getId() == null){
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "product/productForm";
        }

        try {
            productService.updateProduct(productFormDto, productImageFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "product/productForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = {"/admin/products", "/admin/products/{page}"})
    public String productManage(ProductSearchDto productSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<Product> products = productService.getAdminProductPage(productSearchDto, pageable);
        model.addAttribute("products", products);
        model.addAttribute("productSearchDto", productSearchDto);
        model.addAttribute("maxPage", 5);
        return "product/productManage";

    }

    @GetMapping(value = "product/{productCategory}")
    public String main(@PathVariable("productCategory") String productCategory, ProductSearchDto productSearchDto, Optional<Integer> page, Model model) {
        if (!StringUtils.isEmpty(productCategory)) {
            productSearchDto.setProductCategory(ProductCategory.fromValue(productCategory));
        }

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainProductDto> products = productService.getMainProductPage(productSearchDto, pageable);

        model.addAttribute("products", products);
        model.addAttribute("productSearchDto", productSearchDto);
        model.addAttribute("maxPage", 5);

        return "product/outer";
    }


}