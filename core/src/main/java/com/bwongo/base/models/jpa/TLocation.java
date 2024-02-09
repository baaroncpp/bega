package com.bwongo.base.models.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Setter;

@Entity
@Setter
@Table(name = "t_location", schema = "core")
public class TLocation extends AuditEntity{
    private Double latitudeCoordinate;
    private Double longitudeCoordinate;

    @Column(name = "latitude_coordinate")
    public Double getLatitudeCoordinate() {
        return latitudeCoordinate;
    }

    @Column(name = "longitude_coordinate")
    public Double getLongitudeCoordinate() {
        return longitudeCoordinate;
    }
}
