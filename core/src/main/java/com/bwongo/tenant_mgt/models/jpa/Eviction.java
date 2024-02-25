package com.bwongo.tenant_mgt.models.jpa;

import com.bwongo.base.models.enums.EvictionStatus;
import com.bwongo.base.models.enums.EvictionUpdate;
import com.bwongo.base.models.jpa.AuditEntity;
import com.bwongo.user_mgt.models.jpa.TUser;
import jakarta.persistence.*;
import lombok.Setter;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 9/15/23
 **/
@Entity
@Table(name = "t_eviction", schema = "core")
@Setter
public class Eviction extends AuditEntity {
    private Tenant tenant;
    private TUser evictedBy;
    private Date evictionDate;
    private String reason;
    private String evictionNoticeUrl;
    private String witnessFullName;
    private String witness2FullName;
    private EvictionUpdate evictionUpdate;
    private String evictionUpdateNote;
    private EvictionStatus evictionStatus;

    @JoinColumn(name = "tenant_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.EAGER)
    public Tenant getTenant() {
        return tenant;
    }

    @JoinColumn(name = "evicted_by_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.EAGER)
    public TUser getEvictedBy() {
        return evictedBy;
    }

    @Column(name = "eviction_date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEvictionDate() {
        return evictionDate;
    }

    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    @Column(name = "eviction_notice_url")
    public String getEvictionNoticeUrl() {
        return evictionNoticeUrl;
    }

    @Column(name = "witness_full_name")
    public String getWitnessFullName() {
        return witnessFullName;
    }

    @Column(name = "witness_2_full_name")
    public String getWitness2FullName() {
        return witness2FullName;
    }

    @Column(name = "eviction_update")
    @Enumerated(EnumType.STRING)
    public EvictionUpdate getEvictionUpdate() {
        return evictionUpdate;
    }

    @Column(name = "eviction_update_note")
    public String getEvictionUpdateNote() {
        return evictionUpdateNote;
    }

    @Column(name = "eviction_status")
    @Enumerated(EnumType.STRING)
    public EvictionStatus getEvictionStatus() {
        return evictionStatus;
    }
}
