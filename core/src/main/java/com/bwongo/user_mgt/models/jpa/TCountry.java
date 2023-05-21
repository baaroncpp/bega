package com.bwongo.user_mgt.models.jpa;

import com.bwongo.base.model.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/18/23
 **/
@Entity
@Table(name = "t_country", schema = "core")
@Setter
public class TCountry extends BaseEntity {
    private String name;
    private String isoAlpha2;
    private String isoAlpha3;
    private Integer countryCode;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "iso_alpha_2")
    public String getIsoAlpha2() {
        return isoAlpha2;
    }

    @Column(name = "iso_alpha_3")
    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    @Column(name = "iso_numeric")
    public Integer getCountryCode() {
        return countryCode;
    }
}
