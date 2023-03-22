package com.bwongo.authserver.repository;

import com.bwongo.commons.models.jpa.security.TUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/21/23
 **/
@Repository
public interface TUserRepository extends JpaRepository<TUser, Long> {
    Optional<TUser> findByEmail(String email);
}
