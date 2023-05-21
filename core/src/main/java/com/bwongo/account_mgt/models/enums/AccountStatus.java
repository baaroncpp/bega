package com.bwongo.account_mgt.models.enums;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/18/23
 **/
public enum AccountStatus {
    ACTIVE("Account is assigned to an agent or product already"),
    NOT_ACTIVE("Account is not assigned to any party and can be used"),
    CLOSED("Account closed for ever and cannot be used"),
    SUSPENDED("Account is temporarily suspended but not closed");

    private final String description;

    AccountStatus(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }
}
