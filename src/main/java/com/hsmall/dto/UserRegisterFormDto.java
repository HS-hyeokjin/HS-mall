package com.hsmall.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//이름, 이메일, 비밀번호, 주소
public class UserRegisterFormDto {
    private String name;
    private String email;
    private String password;
    private String address;

}
