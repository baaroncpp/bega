package com.bwongo.apartment_mgt.models.jpa;

import com.bwongo.base.models.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
@Entity
@Table(name = "t_house_type", schema = "core")
@Setter
public class HouseType extends BaseEntity {
    private String name;
    private String note;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "note")
    public String getNote() {
        return note;
    }
}
