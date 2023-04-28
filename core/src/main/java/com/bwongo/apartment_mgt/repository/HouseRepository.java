package com.bwongo.apartment_mgt.repository;

import com.bwongo.apartment_mgt.models.jpa.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
}
