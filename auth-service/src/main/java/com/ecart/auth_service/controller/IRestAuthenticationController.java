package com.ecart.auth_service.controller;

import com.ecart.auth_service.dto.AuthRequest;
import com.ecart.auth_service.dto.AuthResponse;
import com.ecart.auth_service.dto.DtoUser;
import com.ecart.exceptioncommon.rootRest.RootEntity;

public interface IRestAuthenticationController {
    public RootEntity<DtoUser> register(AuthRequest input);

    public RootEntity<AuthResponse> authenticate(AuthRequest input);
}
