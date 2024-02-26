package com.bwongo.user_mgt.repository;

import com.bwongo.user_mgt.models.jpa.TRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/26/24
 **/
@Repository
public interface TRoleRepository extends JpaRepository<TRole, Long> {
    Optional<TRole> findByName(String name);
}
