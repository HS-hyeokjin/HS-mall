package com.hsmall.controller;

import com.hsmall.dto.UserRegisterFormDto;
import com.hsmall.entity.User;
import com.hsmall.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

        @GetMapping(value = "/register")
        public String userRegisterForm(Model model){
            model.addAttribute("userRegisterFormDto", new UserRegisterFormDto());
            return "user/userRegisterForm";
        }

        @PostMapping(value = "/register")
        public String userRegisterForm(UserRegisterFormDto userRegisterFormDto){

            User user = User.createUser(userRegisterFormDto, passwordEncoder);
            userService.registerUser(user);
            return "redirect:/";
        }

        @GetMapping(value = "/login")
        public String loginUser(){
            return "/user/userLoginForm";
        }
        @GetMapping(value = "/login/error")
        public String loginError(Model model){
            model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해 주세요");
            return "user/userLoginForm";
        }
}
