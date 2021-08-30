package com.chalyk.taskmanager.controller;

import com.chalyk.taskmanager.payload.request.LoginRequest;
import com.chalyk.taskmanager.payload.request.RegistrationRequest;
import com.chalyk.taskmanager.payload.response.JWTTokenSuccessResponse;
import com.chalyk.taskmanager.payload.response.MessageResponse;
import com.chalyk.taskmanager.security.JWTTokenProvider;
import com.chalyk.taskmanager.security.SecurityConstants;
import com.chalyk.taskmanager.service.AccountService;
import com.chalyk.taskmanager.util.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@PreAuthorize("permitAll()")
public class AuthController {

    private final ResponseErrorValidation errorValidation;
    private final AccountService accountService;
    private final JWTTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(ResponseErrorValidation errorValidation, AccountService accountService, JWTTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.errorValidation = errorValidation;
        this.accountService = accountService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticateAccount(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = errorValidation.mapValidationService(bindingResult);

        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getLogin(),
                loginRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerAccount(@Valid @RequestBody RegistrationRequest registrationRequest, BindingResult bindingResult) {
        ResponseEntity<Object> errors = errorValidation.mapValidationService(bindingResult);

        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }

        accountService.addAccount(registrationRequest);
        return ResponseEntity.ok(new MessageResponse("Account registered successfully"));
    }
}
