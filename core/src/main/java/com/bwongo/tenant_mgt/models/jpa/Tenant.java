package com.bwongo.tenant_mgt.models.jpa;

import com.bwongo.base.models.jpa.AuditEntity;
import com.bwongo.tenant_mgt.models.enums.OccupationStatus;
import com.bwongo.tenant_mgt.models.enums.TenantStatus;
import com.bwongo.user_mgt.models.jpa.TUserMeta;
import jakarta.persistence.*;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/17/23
 **/
@Entity
@Table(name = "t_tenant", schema = "core")
@Setter
@ToString
public class Tenant extends AuditEntity {
    private String username;
    private String password;
    private OccupationStatus occupationStatus;
    private String occupationLocation;
    private String occupationContactPhone;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private TenantStatus tenantStatus;
    private TUserMeta userMeta;

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    @Column(name = "occupation_status")
    public OccupationStatus getOccupationStatus() {
        return occupationStatus;
    }

    @Column(name = "occupation_location")
    public String getOccupationLocation() {
        return occupationLocation;
    }

    @Column(name = "occupation_contact_phone")
    public String getOccupationContactPhone() {
        return occupationContactPhone;
    }

    @Column(name = "emergency_contact_name")
    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    @Column(name = "emergency_contact_phone")
    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    @Column(name = "tenant_status")
    @Enumerated(EnumType.STRING)
    public TenantStatus getTenantStatus() {
        return tenantStatus;
    }

    @JoinColumn(name = "user_meta_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUserMeta getUserMeta() {
        return userMeta;
    }
}
