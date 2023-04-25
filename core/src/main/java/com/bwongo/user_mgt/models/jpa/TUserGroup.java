package com.bwongo.user_mgt.models.jpa;

import com.bwongo.base.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/21/23
 **/
@Entity
@Table(name = "t_user_group", schema = "core")
public class TUserGroup extends BaseEntity {
    private String name;
    private String note;

    @Column(name = "user_group_name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "group_note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
