package com.hsmall.service;

import com.hsmall.dto.UserRegisterFormDto;
import com.hsmall.entity.User;
import com.hsmall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(User user){
        validateUserEmail(user);
        return userRepository.save(user);
    }

    private void validateUserEmail(User user) {
        User findUser = userRepository.findByEmail(user.getEmail());
        if(findUser != null){
            throw new IllegalStateException("이미 가입된 회원");
        }
    }

}
