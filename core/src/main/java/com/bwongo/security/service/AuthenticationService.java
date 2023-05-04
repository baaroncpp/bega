package com.bwongo.security.service;

import com.bwongo.commons.models.exceptions.ResourceNotFoundException;
import com.bwongo.security.models.AuthenticationRequest;
import com.bwongo.security.models.AuthenticationResponse;
import com.bwongo.security.models.CustomUserDetails;
import com.bwongo.user_mgt.repository.TUserRepository;
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
        log.warn("authenticator reached");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );

        var user = userRepository.findByUsername(authenticationRequest.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        var jwtToken = jwtService.generateToken(getMappedCustomUserDetails(user.getUsername()));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private CustomUserDetails getMappedCustomUserDetails(String username){
        return customUserService.getCustomUserDetails(username);
    }

}
