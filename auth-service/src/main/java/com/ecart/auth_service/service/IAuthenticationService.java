package com.ecart.auth_service.service;

import com.ecart.auth_service.dto.*;

public interface IAuthenticationService {

    DtoUser register(AuthRequest input);

    AuthResponse authenticate(LoginRequest input);

    AuthResponse refreshToken(RefreshTokenRequest input);

    DtoUser getUserByUsername(String username);

    DtoUser updateUser(String username, UserUpdateRequest input);

    void deleteCurrentUser(String username);


}
