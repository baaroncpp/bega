package com.bwongo.landlord_mgt.repository;

import com.bwongo.landlord_mgt.models.jpa.TBankDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankDetailRepository extends JpaRepository<TBankDetail, Long> {
    Optional<TBankDetail> findByAccountNameAndAccountNumber(String accountName, String accountNumber);
}
