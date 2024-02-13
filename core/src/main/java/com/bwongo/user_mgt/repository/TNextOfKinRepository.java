package com.bwongo.user_mgt.repository;

import com.bwongo.user_mgt.models.jpa.TNextOfKin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TNextOfKinRepository extends JpaRepository<TNextOfKin, Long> {
    boolean existsByIdNumber(String idNumber);
    boolean existsByEmail(String email);
}
