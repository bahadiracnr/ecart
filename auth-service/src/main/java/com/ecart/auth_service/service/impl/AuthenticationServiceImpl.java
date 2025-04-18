package com.ecart.auth_service.service.impl;

import com.ecart.auth_service.dto.AuthRequest;
import com.ecart.auth_service.dto.DtoUser;
import com.ecart.auth_service.model.User;
import com.ecart.auth_service.repository.UserRepository;
import com.ecart.auth_service.service.IAuthenticationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    private User createUser(AuthRequest input) {
        User user = new User();
        user.setCreateTime(new Date());
        user.setUsername(input.getUsername());
        return user;
    }

    @Override
    public DtoUser register(AuthRequest input){
        DtoUser dtoUser = new DtoUser();
        User savedUser = userRepository.save(createUser(input));

        BeanUtils.copyProperties(savedUser, dtoUser);
        return dtoUser;
    }


}
