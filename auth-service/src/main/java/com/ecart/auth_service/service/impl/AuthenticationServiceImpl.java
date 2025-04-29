package com.ecart.auth_service.service.impl;


import com.ecart.auth_service.dto.*;
import com.ecart.auth_service.producer.RegisterEventProducer;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private JWTService jwtService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private RegisterEventProducer registerEventProducer; // ✅ Kafka Producer eklendi

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

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiredDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4)); // 4 saat geçerli
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        return refreshToken;
    }

    @Override
    public DtoUser register(AuthRequest input) {
        DtoUser dtoUser = new DtoUser();
        User savedUser = userRepository.save(createUser(input));
        BeanUtils.copyProperties(savedUser, dtoUser);


        RegisterEvent registerEvent = new RegisterEvent(
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName()
        );
        registerEventProducer.sendRegisterEvent(registerEvent);

        return dtoUser;
    }

    @Override
    public AuthResponse authenticate(LoginRequest input) {
        Optional<User> optUser = userRepository.findByUsername(input.getUsername());

        if (optUser.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, "Kullanıcı bulunamadı"));
        }

        User user = optUser.get();

        if (!passwordEncoder.matches(input.getPassword(), user.getPassword())) {
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, "Şifre yanlış"));
        }

        String accessToken = jwtService.generateToken(
                UserJwtDto.builder().username(user.getUsername()).build()
        );

        RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(user));

        return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
    }

    private boolean isValidRefreshToken(Date expiredDate) {
        return new Date().before(expiredDate);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest input) {
        Optional<RefreshToken> optRefreshToken = refreshTokenRepository.findByRefreshToken(input.getRefreshToken());

        if(optRefreshToken.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_NOT_FOUND, input.getRefreshToken()));
        }

        if(!isValidRefreshToken(optRefreshToken.get().getExpiredDate())) {
            throw new BaseException(new ErrorMessage(MessageType.REFRESH_TOKEN_IS_EXPIRED, input.getRefreshToken()));
        }

        User user = optRefreshToken.get().getUser();

        String accessToken = jwtService.generateToken(
                UserJwtDto.builder().username(user.getUsername()).build()
        );

        RefreshToken savedRefreshToken = refreshTokenRepository.save(createRefreshToken(user));

        return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
    }

    @Override
    public DtoUser getUserByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsernameAndIsDeletedFalse(username);

        if (userOpt.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_NOT_FOUND, username));
        }

        User user = userOpt.get();
        DtoUser dto = new DtoUser();
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        return dto;
    }

    @Override
    public DtoUser updateUser(String username, UserUpdateRequest input) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.USERNAME_NOT_FOUND, username)));

        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());

        if (input.getCurrentPassword() != null && input.getNewPassword() != null) {
            if (!passwordEncoder.matches(input.getCurrentPassword(), user.getPassword())) {
                throw new BaseException(new ErrorMessage(MessageType.USERNAME_OR_PASSWORD_INVALID, "Mevcut şifre hatalı"));
            }
            user.setPassword(passwordEncoder.encode(input.getNewPassword()));
        }

        User updatedUser = userRepository.save(user);

        DtoUser dtoUser = new DtoUser();
        BeanUtils.copyProperties(updatedUser, dtoUser);
        return dtoUser;
    }

    @Override
    public void deleteCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.USERNAME_NOT_FOUND, username)));

        user.setIsDeleted(true);
        userRepository.save(user);
    }
}
