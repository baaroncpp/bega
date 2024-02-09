package com.bwongo.apartment_mgt.repository;

import com.bwongo.apartment_mgt.models.jpa.Apartment;
import com.bwongo.apartment_mgt.models.jpa.House;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findAllByApartment(Apartment apartment);
    List<House> findAllByIsOccupied(boolean isOccupied, Pageable pageable);
}
