package com.bwongo.base.model.jpa;

import com.bwongo.user_mgt.models.jpa.TUser;
import jakarta.persistence.*;
import lombok.Setter;

import java.util.Date;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/20/23
 **/
@MappedSuperclass
@Setter
public class AuditEntity extends BaseEntity {
    private TUser modifiedBy;
    private TUser createdBy;
    private boolean isActive;

    @JoinColumn(name = "modified_by_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getModifiedBy() {
        return modifiedBy;
    }

    @JoinColumn(name = "created_by_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.LAZY)
    public TUser getCreatedBy() {
        return createdBy;
    }

    @Column(name = "is_active")
    public boolean isActive() {
        return isActive;
    }
}
