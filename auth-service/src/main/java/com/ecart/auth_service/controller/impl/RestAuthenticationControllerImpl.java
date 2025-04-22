package com.ecart.auth_service.controller.impl;

import com.ecart.auth_service.controller.IRestAuthenticationController;
import com.ecart.auth_service.dto.*;
import com.ecart.auth_service.service.IAuthenticationService;
import com.ecart.exceptioncommon.rootRest.RestBaseController;
import com.ecart.exceptioncommon.rootRest.RootEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestAuthenticationControllerImpl extends RestBaseController implements IRestAuthenticationController {

    @Autowired
    private IAuthenticationService authenticationService;

    @PostMapping("/register")
    @Override
    public RootEntity<DtoUser> register(@Valid @RequestBody AuthRequest input) {
        return ok(authenticationService.register(input));
    }

    @PostMapping("/authenticate")
    @Override
    public RootEntity<AuthResponse> authenticate(@Valid @RequestBody LoginRequest input) {
        return ok(authenticationService.authenticate(input));
    }

    @PostMapping("/refreshToken")
    @Override
    public RootEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest input) {
        return ok(authenticationService.refreshToken(input));
    }

    @GetMapping("/user/me")
    public RootEntity<DtoUser> getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ok(authenticationService.getUserByUsername(username));
    }

    @PutMapping("/user/update")
    public RootEntity<DtoUser> updateUser(@Valid @RequestBody UserUpdateRequest input) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ok(authenticationService.updateUser(username, input));
    }

    @DeleteMapping("/user/delete")
    public RootEntity<String> deleteCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        authenticationService.deleteCurrentUser(username);
        return ok("Kullanıcı başarıyla silindi.");
    }



}
