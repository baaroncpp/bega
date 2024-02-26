package com.bwongo.user_mgt.repository;

import com.bwongo.user_mgt.models.jpa.TPermission;
import com.bwongo.user_mgt.models.jpa.TRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/26/24
 **/
@Repository
public interface TPermissionRepository extends JpaRepository<TPermission, Long> {
    List<TPermission> findByIsAssignableEquals(Boolean isAssignable);
    Optional<TPermission> findByName(String name);
    List<TPermission> findAllByRole(TRole role);
}
