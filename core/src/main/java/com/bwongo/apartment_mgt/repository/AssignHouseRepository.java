package com.bwongo.apartment_mgt.repository;

import com.bwongo.apartment_mgt.models.jpa.AssignHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/19/23
 **/
@Repository
public interface AssignHouseRepository extends JpaRepository<AssignHouse, Long> {
}
