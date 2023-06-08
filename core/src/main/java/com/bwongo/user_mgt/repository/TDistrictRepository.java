package com.bwongo.user_mgt.repository;

import com.bwongo.user_mgt.models.jpa.TDistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 6/8/23
 **/
@Repository
public interface TDistrictRepository extends JpaRepository<TDistrict, Long> {
}
