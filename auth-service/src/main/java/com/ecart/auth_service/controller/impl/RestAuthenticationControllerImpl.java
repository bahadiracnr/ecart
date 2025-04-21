package com.ecart.auth_service.controller.impl;

import com.ecart.auth_service.controller.IRestAuthenticationController;
import com.ecart.auth_service.dto.AuthRequest;
import com.ecart.auth_service.dto.AuthResponse;
import com.ecart.auth_service.dto.DtoUser;
import com.ecart.auth_service.service.IAuthenticationService;
import com.ecart.exceptioncommon.rootRest.RestBaseController;
import com.ecart.exceptioncommon.rootRest.RootEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthenticationControllerImpl extends RestBaseController implements IRestAuthenticationController {
    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/register")
    @Override
    public RootEntity<DtoUser> register(@Valid @RequestBody AuthRequest input){
        return ok(authenticationService.register(input));
    }

    @PostMapping("/authenticate")
    @Override
    public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest input) {
        return ok(authenticationService.authenticate(input));
    }

}
