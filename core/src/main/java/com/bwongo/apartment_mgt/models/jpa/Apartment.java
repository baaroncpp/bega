package com.bwongo.apartment_mgt.models.jpa;

import com.bwongo.apartment_mgt.models.enums.ApartmentType;
import com.bwongo.apartment_mgt.models.enums.ManagementFeeType;
import com.bwongo.base.model.jpa.AuditEntity;
import com.bwongo.landlord_mgt.model.jpa.Landlord;
import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
@Entity
@Table(name = "t_apartment", schema = "core")
@Setter
public class Apartment extends AuditEntity {
    private String apartmentName;
    private ApartmentType apartmentType;
    private String location;
    private Landlord apartmentOwner;
    private String apartmentDescription;
    private BigDecimal managementFee;
    private ManagementFeeType managementFeeType;

    @Column(name = "apartment_type")
    public String getApartmentName() {
        return apartmentName;
    }

    @Column(name = "apartment_type")
    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    @JoinColumn(name = "landlord_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public Landlord getApartmentOwner() {
        return apartmentOwner;
    }

    @Column(name = "apartment_description")
    public String getApartmentDescription() {
        return apartmentDescription;
    }

    @Column(name = "management_fee")
    public BigDecimal getManagementFee() {
        return managementFee;
    }

    @Column(name = "management_fee_type")
    @Enumerated(EnumType.STRING)
    public ManagementFeeType getManagementFeeType() {
        return managementFeeType;
    }
}
