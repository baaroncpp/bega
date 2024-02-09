package com.bwongo.security.service;

import com.bwongo.commons.models.exceptions.InsufficientAuthenticationException;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.security.models.dto.AuthenticationRequestDto;
import com.bwongo.security.models.dto.AuthenticationResponseDto;
import com.bwongo.security.models.CustomUserDetails;
import com.bwongo.security.models.dto.RefreshTokenRequestDto;
import com.bwongo.security.models.jpa.TRefreshToken;
import com.bwongo.user_mgt.repository.TUserRepository;
import com.bwongo.user_mgt.service.dto.UserMgtDtoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
    private final RefreshTokenService refreshTokenService;
    private final UserMgtDtoService userMgtDtoService;

    private static final String REFRESH_TOKEN_NOT_FOUND = "Refresh Token is not found";

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
        log.warn("authenticator reached");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDto.getEmail(),
                        authenticationRequestDto.getPassword()
                )
        );
        Validate.isTrue(authentication.isAuthenticated(), ExceptionType.BAD_CREDENTIALS, "Invalid user !");

        var user = userRepository.findByUsername(authenticationRequestDto.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        var username = user.getUsername();
        var authorities = customUserService.getCustomUserDetails(username).getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        var jwtToken = jwtService.generateToken(getMappedCustomUserDetails(username));
        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshTokenService.createRefreshToken(username).getToken())
                .id(user.getId())
                .userGroup(userMgtDtoService.mapTUserGroupToUserGroupResponseDto(user.getUserGroup()))
                .authorities(authorities)
                .build();
    }

    public AuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.refreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(TRefreshToken::getUser)
                .map(user -> {
                    var accessToken = jwtService.generateToken(getMappedCustomUserDetails(user.getUsername()));
                    var authorities = customUserService.getCustomUserDetails(user.getUsername()).getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toList();
                    return AuthenticationResponseDto.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenService.createRefreshToken(user.getUsername()).getToken())
                            .id(user.getId())
                            .userGroup(userMgtDtoService.mapTUserGroupToUserGroupResponseDto(user.getUserGroup()))
                            .authorities(authorities)
                            .build();
                })
                .orElseThrow(() -> new InsufficientAuthenticationException(REFRESH_TOKEN_NOT_FOUND));
    }

    private CustomUserDetails getMappedCustomUserDetails(String username){
        return customUserService.getCustomUserDetails(username);
    }

}
