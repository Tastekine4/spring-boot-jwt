package com.codespace.controller;

import com.codespace.authentication.TokenManager;
import com.codespace.request.LoginRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    // Inject spring web authentication manager
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenManager;

    /**
     * It will return token to user if validation succeed.
     *
     * @param loginRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            return ResponseEntity.ok(tokenManager.generateToken(loginRequest.getUsername()));
        } catch (Exception e) {
            throw e;
        }
    }
}
