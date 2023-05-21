package com.bwongo.account_mgt.repository;

import com.bwongo.account_mgt.models.jpa.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/20/23
 **/
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
