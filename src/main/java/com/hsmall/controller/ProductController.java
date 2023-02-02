package com.hsmall.controller;

import com.hsmall.dto.ProductFormDto;
import com.hsmall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

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
}