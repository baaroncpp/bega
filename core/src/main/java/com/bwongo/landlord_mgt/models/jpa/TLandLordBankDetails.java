package com.bwongo.landlord_mgt.models.jpa;

import com.bwongo.base.models.jpa.AuditEntity;
import jakarta.persistence.*;
import lombok.Setter;

@Setter
@Entity
@Table(name = "t_landlord_bank_details", schema = "core")
public class TLandLordBankDetails extends AuditEntity {
    private TBankDetail bankDetail;
    private TLandlord landlord;

    @JoinColumn(name = "bank_detail_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TBankDetail getBankDetail() {
        return bankDetail;
    }

    @JoinColumn(name = "landlord_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TLandlord getLandlord() {
        return landlord;
    }
}
