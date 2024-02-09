package com.bwongo.apartment_mgt.models.jpa;

import com.bwongo.apartment_mgt.models.enums.ApartmentType;
import com.bwongo.apartment_mgt.models.enums.ManagementFeeType;
import com.bwongo.base.models.jpa.AuditEntity;
import com.bwongo.landlord_mgt.models.jpa.TLandlord;
import com.bwongo.base.models.jpa.TDistrict;
import jakarta.persistence.*;
import lombok.Setter;

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
    private TLandlord apartmentOwner;
    private String apartmentDescription;
    private ManagementFeeType managementFeeType;
    private TDistrict district;
    private boolean isRenovationServiced;

    @Column(name = "apartment_name")
    public String getApartmentName() {
        return apartmentName;
    }

    @Column(name = "apartment_type")
    @Enumerated(EnumType.STRING)
    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    @JoinColumn(name = "landlord_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TLandlord getApartmentOwner() {
        return apartmentOwner;
    }

    @Column(name = "apartment_description")
    public String getApartmentDescription() {
        return apartmentDescription;
    }

    @Column(name = "management_fee_type")
    @Enumerated(EnumType.STRING)
    public ManagementFeeType getManagementFeeType() {
        return managementFeeType;
    }

    @JoinColumn(name = "district_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TDistrict getDistrict() {
        return district;
    }

    @Column(name = "is_renovation_serviced")
    public boolean isRenovationServiced() {
        return isRenovationServiced;
    }
}
