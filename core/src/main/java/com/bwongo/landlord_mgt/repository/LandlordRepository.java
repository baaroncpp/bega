package com.bwongo.landlord_mgt.repository;

import com.bwongo.landlord_mgt.models.jpa.Landlord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/7/23
 **/
@Repository
public interface LandlordRepository extends JpaRepository<Landlord, Long> {
    List<Landlord> findAllByActive(boolean isActive, Pageable pageable);
    boolean existsByUsername(String username);
}
