package com.bwongo.account_mgt.repository;

import com.bwongo.account_mgt.models.jpa.Account;
import com.bwongo.user_mgt.models.jpa.TUserMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/18/23
 **/
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserMeta(TUserMeta userMeta);
}
