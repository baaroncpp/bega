package com.bwongo.apartment_mgt.models.jpa;

import com.bwongo.apartment_mgt.models.enums.VacateReason;
import com.bwongo.base.model.jpa.AuditEntity;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import jakarta.persistence.*;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/19/23
 **/
@Entity
@Table(name = "t_vacate_house", schema = "core")
@Setter
public class VacateHouse extends AuditEntity {
    private Tenant tenant;
    private House house;
    private VacateReason vacateReason;
    private String note;

    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public Tenant getTenant() {
        return tenant;
    }

    @JoinColumn(name = "house_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public House getHouse() {
        return house;
    }

    @Column(name = "vacate_reason")
    public VacateReason getVacateReason() {
        return vacateReason;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }
}
