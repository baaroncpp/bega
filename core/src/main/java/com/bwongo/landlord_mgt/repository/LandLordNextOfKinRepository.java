package com.bwongo.landlord_mgt.repository;

import com.bwongo.landlord_mgt.models.jpa.TLandlord;
import com.bwongo.landlord_mgt.models.jpa.TLandlordNextOfKin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LandLordNextOfKinRepository extends JpaRepository<TLandlordNextOfKin, Long> {
    List<TLandlordNextOfKin> findAllByLandlord(TLandlord landlord);
}
