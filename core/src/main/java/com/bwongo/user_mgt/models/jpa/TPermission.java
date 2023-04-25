package com.bwongo.user_mgt.models.jpa;

import com.bwongo.base.model.BaseEntity;
import jakarta.persistence.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/21/23
 **/
@Entity
@Table(name = "t_permission", schema = "core")
public class TPermission extends BaseEntity {
    private TRole role;
    private String name;
    private Boolean isAssignable;

    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = true, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public TRole getRole() {
        return role;
    }

    public void setRole(TRole role) {
        this.role = role;
    }

    @Column(name = "permission_name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "is_assignable", nullable = false)
    public Boolean getAssignable() {
        return isAssignable;
    }

    public void setAssignable(Boolean assignable) {
        isAssignable = assignable;
    }
}
