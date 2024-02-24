package com.bwongo.tenant_mgt.models.jpa;

import com.bwongo.base.models.jpa.AuditEntity;
import com.bwongo.user_mgt.models.jpa.TNextOfKin;
import jakarta.persistence.*;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 2/24/24
 **/
@Entity
@Setter
@Table(name = "t_tenant_next_of_kin", schema = "core")
public class TTenantNextOfKin extends AuditEntity {
    private Tenant tenant;
    private TNextOfKin nextOfKin;

    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public Tenant getTenant() {
        return tenant;
    }

    @JoinColumn(name = "next_of_kin_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TNextOfKin getNextOfKin() {
        return nextOfKin;
    }
}
