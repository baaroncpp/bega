package com.bwongo.apartment_mgt.models.jpa;

import com.bwongo.apartment_mgt.models.enums.PaymentType;
import com.bwongo.base.model.jpa.AuditEntity;
import com.bwongo.tenant_mgt.models.jpa.Tenant;
import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/18/23
 **/
@Entity
@Table(name = "t_rent_payment", schema = "core")
@Setter
public class RentPayment extends AuditEntity {
    private BigDecimal amount;
    private Tenant tenant;
    private PaymentType paymentType;
    private House house;
    private String note;

    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public Tenant getTenant() {
        return tenant;
    }

    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    public PaymentType getPaymentType() {
        return paymentType;
    }

    @JoinColumn(name = "house_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public House getHouse() {
        return house;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }
}
