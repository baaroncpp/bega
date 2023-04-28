package com.bwongo.landlord_mgt.model.jpa;

import com.bwongo.base.model.enums.IdentificationType;
import com.bwongo.base.model.jpa.AuditEntity;
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
@Table(name = "t_landlord", schema = "core")
@Setter
public class Landlord extends AuditEntity {
    private String firstName;
    private String middleName;
    private String lastName;
    private IdentificationType identificationType;
    private String identificationNumber;
    private String phoneNumber;
    private String secondPhoneNumber;
    private String physicalAddress;
    private String email;
    private String loginPassword;

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    @Column(name = "middle_name")
    public String getMiddleName() {
        return middleName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    @Column(name = "identification_type")
    public IdentificationType getIdentificationType() {
        return identificationType;
    }

    @Column(name = "identification_number")
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Column(name = "second_phone_number")
    public String getSecondPhoneNumber() {
        return secondPhoneNumber;
    }

    @Column(name = "physical_address")
    public String getPhysicalAddress() {
        return physicalAddress;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Column(name = "login_password")
    public String getLoginPassword() {
        return loginPassword;
    }
}
