package com.bwongo.landlord_mgt.models.jpa;

import com.bwongo.base.models.jpa.AuditEntity;
import com.bwongo.user_mgt.models.jpa.TNextOfKin;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "t_landlord_next_of_kin", schema = "core",
        uniqueConstraints = @UniqueConstraint(columnNames = {"landlord_id", "next_of_kin_id"}))
@Setter
public class TLandlordNextOfKin extends AuditEntity {
    private TLandlord landlord;
    private TNextOfKin nextOfKin;

    @JoinColumn(name = "landlord_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public TLandlord getLandlord() {
        return landlord;
    }

    @JoinColumn(name = "next_of_kin_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    public TNextOfKin getNextOfKin() {
        return nextOfKin;
    }
}
