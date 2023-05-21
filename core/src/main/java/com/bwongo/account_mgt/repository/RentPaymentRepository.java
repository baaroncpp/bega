package com.bwongo.apartment_mgt.repository;

import com.bwongo.apartment_mgt.models.jpa.RentPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/18/23
 **/
@Repository
public interface RentPaymentRepository extends JpaRepository<RentPayment, Long> {
}
