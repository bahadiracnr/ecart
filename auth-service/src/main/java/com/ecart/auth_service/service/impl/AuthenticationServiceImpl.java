package com.ecart.auth_service.service.impl;

import com.ecart.auth_service.dto.AuthRequest;
import com.ecart.auth_service.dto.AuthResponse;
import com.ecart.auth_service.dto.DtoUser;
import com.ecart.auth_service.model.RefreshToken;
import com.ecart.auth_service.model.User;
import com.ecart.auth_service.repository.RefreshTokenRepository;
import com.ecart.auth_service.repository.UserRepository;
import com.ecart.auth_service.service.IAuthenticationService;
import com.ecart.exceptioncommon.base.BaseException;
import com.ecart.exceptioncommon.err.ErrorMessage;
import com.ecart.exceptioncommon.model.MessageType;
import com.ecart.tokencommon.dtos.UserJwtDto;
import com.ecart.tokencommon.service.JWTService;
import jakarta.persistence.AssociationOverride;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JWTService jwtService;


    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private User createUser(AuthRequest input) {
        User user = new User();
        user.setCreateTime(new Date());
        user.setUsername(input.getUsername());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setEmail(input.getEmail());
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        return user;
    }

    private RefreshToken createRefreshToken(User user){
        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setCreateTime(new Date());
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        return refreshToken;
    }

    @Override
    public DtoUser register(AuthRequest input){
        DtoUser dtoUser = new DtoUser();
        User savedUser = userRepository.save(createUser(input));

        BeanUtils.copyProperties(savedUser, dtoUser);
        return dtoUser;
    }

    @Override
    public AuthResponse authenticate(AuthRequest input) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword());
            authenticationProvider.authenticate(authenticationToken);

            Optional<User> optUser = userRepository.findByUsername(input.getUsername());

            if (optUser.isPresent()) {
                String accessToken = jwtService.generateToken(UserJwtDto.builder().username(optUser.get().getUsername()).build());
                RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(optUser.get()));
                return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
            }

            throw new Exception(MessageType.USERNAME_OR_PASSWORD_INVALID.getMessage());

        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, e.getMessage()));
        }
    }


}
