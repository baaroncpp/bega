package com.bwongo.security.service;

import com.bwongo.commons.models.exceptions.InsufficientAuthenticationException;
import com.bwongo.security.models.jpa.TRefreshToken;
import com.bwongo.security.repository.RefreshTokenRepository;
import com.bwongo.user_mgt.repository.TUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

import static com.bwongo.commons.models.text.StringUtil.randomString;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 12/17/23
 **/
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final TUserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final String EXPIRED_REFRESH_TOKEN = " Refresh token is expired. Please make a new login..!";

    public TRefreshToken createRefreshToken(String username){
        var refreshToken = TRefreshToken.builder()
                .user(userRepository.findByUsername(username).get())
                .token(randomString())
                .expiryDate(Instant.now().plusMillis(600000)) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public TRefreshToken verifyExpiration(TRefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new InsufficientAuthenticationException(token.getToken() + EXPIRED_REFRESH_TOKEN);
        }
        return token;
    }

    public Optional<TRefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }
}
