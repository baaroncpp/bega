package com.bwongo.landlord_mgt.repository;

import com.bwongo.landlord_mgt.model.jpa.Landlord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/7/23
 **/
@Repository
public interface LandlordRepository extends JpaRepository<Landlord, Long> {
    Optional<Landlord> findByIdentificationNumber(String identificationNumber);
    List<Landlord> findAllByActive(boolean isActive, Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByIdentificationNumber(String identificationNumber);
}
