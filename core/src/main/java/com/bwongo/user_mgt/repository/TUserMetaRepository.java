package com.bwongo.user_mgt.repository;

import com.bwongo.user_mgt.models.jpa.TUserMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 6/7/23
 **/
@Repository
public interface TUserMetaRepository extends JpaRepository<TUserMeta, Long> {
    boolean existsByEmail(String email);
    boolean existsByIdentificationNumber(String identificationNumber);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber2(String phoneNumber2);
    Optional<TUserMeta> findByPhoneNumber(String phoneNumber);
    Optional<TUserMeta> findByEmail(String email);
}
