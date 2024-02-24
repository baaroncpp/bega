package com.bwongo.tenant_mgt.repository;

import com.bwongo.tenant_mgt.models.jpa.TTenantNextOfKin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/24/24
 **/
@Repository
public interface TTenantNextOfKinRepository extends JpaRepository<TTenantNextOfKin, Long> {
}
