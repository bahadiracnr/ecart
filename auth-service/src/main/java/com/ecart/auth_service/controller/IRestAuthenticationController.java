package com.ecart.auth_service.controller;

import com.ecart.auth_service.dto.*;
import com.ecart.exceptioncommon.rootRest.RootEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IRestAuthenticationController {

    RootEntity<DtoUser> register(@RequestBody AuthRequest input);

    RootEntity<AuthResponse> authenticate(@RequestBody LoginRequest input);

    RootEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest input);
}
