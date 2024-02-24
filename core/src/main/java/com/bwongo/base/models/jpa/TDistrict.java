package com.bwongo.base.models.jpa;

import jakarta.persistence.*;
import lombok.Setter;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/18/23
 **/
@Entity
@Table(name = "t_district", schema = "core")
@Setter
public class TDistrict extends BaseEntity {
    private TCountry country;
    private String name;
    private String region;

    @JoinColumn(name = "country_id",referencedColumnName = "id",insertable = false,updatable = false)
    @OneToOne(fetch = FetchType.EAGER)
    public TCountry getCountry() {
        return country;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "region")
    public String getRegion() {
        return region;
    }

}
