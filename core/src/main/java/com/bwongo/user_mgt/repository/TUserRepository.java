package com.bwongo.user_mgt.repository;

import com.bwongo.base.models.enums.UserTypeEnum;
import com.bwongo.user_mgt.models.jpa.TUser;
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
    Optional<TUser> findByUsername(String username);

    Long countByUserType(UserTypeEnum userTypeEnum);
}
