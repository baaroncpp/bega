package com.bwongo.account_mgt.models.enums;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/18/23
 **/
public enum AccountType {
    MAIN ("MN"),
    COLLECTION("CL"),
    BULK_PAYMENT ("BP"),
    COMMISSION ("CM"),
    PAYOUT("PO"),
    BUSINESS("BS");

    private final String acronym;

    AccountType(String acronym){
        this.acronym = acronym;
    }

    public String getAcronym(){
        return this.acronym;
    }
}
