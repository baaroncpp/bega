package com.bwongo.landlord_mgt.models.jpa;

import com.bwongo.base.models.jpa.AuditEntity;
import com.bwongo.base.models.jpa.TDistrict;
import com.bwongo.user_mgt.models.jpa.TUserMeta;
import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
@Entity
@Table(name = "t_landlord", schema = "core")
@Setter
public class TLandlord extends AuditEntity {
    private String physicalAddress;
    private String username;
    private String loginPassword;
    private TDistrict district;
    private TUserMeta metaData;
    private String tin;
    private String ownerShipLCLetterUrlPath;
    private LocalDate agreedPaymentDate;
    private String businessAgreementPath;

    @Column(name = "physical_address")
    public String getPhysicalAddress() {
        return physicalAddress;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    @Column(name = "login_password")
    public String getLoginPassword() {
        return loginPassword;
    }

    @JoinColumn(name = "district_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.EAGER)
    public TDistrict getDistrict() {
        return district;
    }

    @JoinColumn(name = "user_meta_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUserMeta getMetaData() {
        return metaData;
    }

    @Column(name = "tin")
    public String getTin() {
        return tin;
    }

    @Column(name = "owner_ship_lc_letter_url_path")
    public String getOwnerShipLCLetterUrlPath() {
        return ownerShipLCLetterUrlPath;
    }

    @Column(name = "agreed_payment_date")
    public LocalDate getAgreedPaymentDate() {
        return agreedPaymentDate;
    }

    @Column(name = "business_agreement_path")
    public String getBusinessAgreementPath() {
        return businessAgreementPath;
    }
}
