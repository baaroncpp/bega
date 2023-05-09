package com.bwongo.apartment_mgt.models.dto.request;

import com.bwongo.commons.models.utils.Validate;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.*;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
public record HouseTypeRequestDto(String name,
                                  String note) {
    public void validate(){
        Validate.notEmpty(name, NULL_HOUSE_TYPE_NAME);
        Validate.notEmpty(note, NULL_HOUSE_TYPE_NOTE);
    }
}
