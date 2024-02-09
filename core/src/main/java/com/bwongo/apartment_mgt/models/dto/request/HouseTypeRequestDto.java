package com.bwongo.apartment_mgt.models.dto.request;

import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.text.StringRegExUtil;
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
        Validate.isTrue(name.length() > 3, ExceptionType.BAD_REQUEST, NAME_TOO_SHORT);
        //StringRegExUtil.stringOfOnlyCharsNoneCaseSensitive(name, NAME_ONLY_CHARACTERS);
        Validate.notEmpty(note, NULL_HOUSE_TYPE_NOTE);
    }
}
