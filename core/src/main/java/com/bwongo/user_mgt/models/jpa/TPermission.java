package com.bwongo.user_mgt.models.jpa;

import com.bwongo.base.models.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/21/23
 **/
@Entity
@Table(name = "t_permission", schema = "core")
@Setter
public class TPermission extends BaseEntity {
    private TRole role;
    private String name;
    private Boolean isAssignable;

    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = true, updatable = false)
    @OneToOne(fetch = FetchType.LAZY)
    public TRole getRole() {
        return role;
    }

    @Column(name = "permission_name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "is_assignable", nullable = false)
    public Boolean getIsAssignable() {
        return isAssignable;
    }
}
