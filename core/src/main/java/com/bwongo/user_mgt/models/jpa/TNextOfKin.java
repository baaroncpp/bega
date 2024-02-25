package com.bwongo.user_mgt.models.jpa;

import com.bwongo.base.models.enums.IdentificationType;
import com.bwongo.base.models.jpa.AuditEntity;
import com.bwongo.base.models.enums.RelationShipType;
import jakarta.persistence.*;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "t_next_of_kin", schema = "core")
@Setter
public class TNextOfKin extends AuditEntity {
    private String firstName;
    private String secondName;
    private Date dateOfBirth;
    private String firstPhoneNumber;
    private String secondPhoneNumber;
    private RelationShipType relationShipType;
    private String relationShipNote;
    private String email;
    private IdentificationType identificationType;
    private String idNumber;
    private String idPhotoUrlPath;

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "second_name")
    public String getSecondName() {
        return secondName;
    }

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    @Column(name = "first_phone_number")
    public String getFirstPhoneNumber() {
        return firstPhoneNumber;
    }

    @Column(name = "second_phone_number")
    public String getSecondPhoneNumber() {
        return secondPhoneNumber;
    }

    @Column(name = "relation_ship_type")
    @Enumerated(EnumType.STRING)
    public RelationShipType getRelationShipType() {
        return relationShipType;
    }

    @Column(name = "relation_ship_note")
    public String getRelationShipNote() {
        return relationShipNote;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Column(name = "identification_type")
    public IdentificationType getIdentificationType() {
        return identificationType;
    }

    @Column(name = "id_number")
    public String getIdNumber() {
        return idNumber;
    }

    @Column(name = "id_photo_url_path")
    public String getIdPhotoUrlPath() {
        return idPhotoUrlPath;
    }
}
