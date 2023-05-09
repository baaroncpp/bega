package com.bwongo.apartment_mgt.models.jpa;

import com.bwongo.base.model.jpa.AuditEntity;
import com.bwongo.tenant_mgt.models.enums.BillingDuration;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/10/23
 **/
@Entity
@Table(name = "t_assign_house", schema = "core")
@Setter
public class AssignHouse extends AuditEntity {
    private House house;
    private BigDecimal predefinedRent;
    private BillingDuration billingDuration;
    private Tenant tenant;
    private BigDecimal depositAmount;
    private BigDecimal rentAmountPaid;
    private Date placementDate;

    @JoinColumn(name = "house_number", referencedColumnName = "house_number")
    @OneToOne(fetch = FetchType.LAZY)
    public House getHouse() {
        return house;
    }

    @Column(name = "predefined_rent")
    public BigDecimal getPredefinedRent() {
        return predefinedRent;
    }

    @Column(name = "billing_duration")
    @Enumerated(EnumType.STRING)
    public BillingDuration getBillingDuration() {
        return billingDuration;
    }

    @JoinColumn(name = "identification_number", referencedColumnName = "identification_number")
    @OneToOne(fetch = FetchType.LAZY)
    public Tenant getTenant() {
        return tenant;
    }

    @Column(name = "deposit_amount")
    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    @Column(name = "rent_amount_paid")
    public BigDecimal getRentAmountPaid() {
        return rentAmountPaid;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "placement_date")
    public Date getPlacementDate() {
        return placementDate;
    }
}
