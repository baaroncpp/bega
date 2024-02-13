package com.bwongo.landlord_mgt.repository;

import com.bwongo.landlord_mgt.models.jpa.TLandLordBankDetails;
import com.bwongo.landlord_mgt.models.jpa.TLandlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandlordBankDetailsRepository extends JpaRepository<TLandLordBankDetails, Long> {
    List<TLandLordBankDetails> findAllByLandlord(TLandlord landlord);
}
