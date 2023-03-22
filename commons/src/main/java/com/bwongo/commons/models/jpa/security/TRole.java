package com.bwongo.commons.models.jpa.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/21/23
 **/
@Entity
@Table(name = "t_role", schema = "core")
public class TRole extends BaseEntity{
    private String name;
    private String note;

    @Column(name = "name", unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
