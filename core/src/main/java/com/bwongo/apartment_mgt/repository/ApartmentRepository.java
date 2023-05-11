package com.bwongo.apartment_mgt.repository;

import com.bwongo.apartment_mgt.models.jpa.Apartment;
import com.bwongo.landlord_mgt.model.jpa.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    boolean existsByApartmentName(String apartmentName);
    List<Apartment> findAllByApartmentOwner(Landlord landlord);
}
