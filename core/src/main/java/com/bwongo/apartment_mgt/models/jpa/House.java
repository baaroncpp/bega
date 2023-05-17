package com.bwongo.apartment_mgt.models.jpa;

import com.bwongo.apartment_mgt.models.enums.RentPeriod;
import com.bwongo.base.model.jpa.AuditEntity;
import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
@Entity
@Table(name = "t_house", schema = "core")
@Setter
public class House extends AuditEntity {
    private String houseNumber;
    private HouseType houseType;
    private Apartment apartment;
    private BigDecimal rentFee;
    private RentPeriod rentPeriod;
    private String note;
    private Boolean isOccupied;
    private String referenceNumber;
    private boolean isRenovationChargeBilled;

    @Column(name = "house_number")
    public String getHouseNumber() {
        return houseNumber;
    }

    @JoinColumn(name = "house_type_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public HouseType getHouseType() {
        return houseType;
    }

    @JoinColumn(name = "apartment_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public Apartment getApartment() {
        return apartment;
    }

    @Column(name = "rent_fee")
    public BigDecimal getRentFee() {
        return rentFee;
    }

    @Column(name = "rent_period")
    @Enumerated(EnumType.STRING)
    public RentPeriod getRentPeriod() {
        return rentPeriod;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    @Column(name = "is_occupied")
    public Boolean getIsOccupied() {
        return isOccupied;
    }

    @Column(name = "reference_number")
    public String getReferenceNumber() {
        return referenceNumber;
    }

    @Column(name = "is_renovation_charge_billed")
    public boolean isRenovationChargeBilled() {
        return isRenovationChargeBilled;
    }
}
