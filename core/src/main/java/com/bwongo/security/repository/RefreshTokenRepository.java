package com.bwongo.security.repository;

import com.bwongo.security.models.jpa.TRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 12/17/23
 **/
@Repository
public interface RefreshTokenRepository extends JpaRepository<TRefreshToken, Long> {
    Optional<TRefreshToken> findByToken(String token);
}
