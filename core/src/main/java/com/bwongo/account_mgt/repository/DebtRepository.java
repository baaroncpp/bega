package com.bwongo.account_mgt.repository;

import com.bwongo.account_mgt.models.jpa.Account;
import com.bwongo.account_mgt.models.jpa.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/28/23
 **/
@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
    Optional<Debt> findByAccount(Account account);
}
