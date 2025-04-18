package com.ecart.auth_service.service;

import com.ecart.auth_service.dto.AuthRequest;
import com.ecart.auth_service.dto.DtoUser;

public interface IAuthenticationService {
    public DtoUser register(AuthRequest input);
}
