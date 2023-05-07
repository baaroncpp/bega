package com.bwongo.tenant_mgt.repository;

import com.bwongo.tenant_mgt.models.jpa.Tenant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/22/23
 **/
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findByIdAndActive(Long id, boolean isActive);
    Optional<Tenant> findByIdentificationNumber(String identificationNumber);
    List<Tenant> findAllByActive(boolean isActive, Pageable pageable);
    boolean existsByEmail(String email);
    boolean existsByIdentificationNumber(String identificationNumber);
}
