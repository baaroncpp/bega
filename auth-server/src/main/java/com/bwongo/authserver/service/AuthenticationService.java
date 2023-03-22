package com.bwongo.authserver.service;

import com.bwongo.authserver.model.AuthenticationRequest;
import com.bwongo.authserver.model.AuthenticationResponse;
import com.bwongo.authserver.model.CustomUserDetails;
import com.bwongo.authserver.model.ValidateTokenRequest;
import com.bwongo.authserver.repository.TUserRepository;
import com.bwongo.commons.models.utils.Validate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/22/23
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TUserRepository userRepository;
    private final CustomUserService customUserService;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(authenticationRequest.getEmail());
        var jwtToken = jwtService.generateToken(getMappedCustomUserDetails(user.orElseThrow().getUsername()));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public String validateToken(ValidateTokenRequest validateTokenRequest) {

        var token = validateTokenRequest.getToken();
        var username = jwtService.extractUsername(token);

        userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        Validate.isTrue(jwtService.isTokenValid(token, getMappedCustomUserDetails(username)), "Invalid token");

        return username;
    }

    private CustomUserDetails getMappedCustomUserDetails(String username){
        return customUserService.getCustomUserDetails(username);
    }

}
