package com.bwongo.base.utils;

import com.bwongo.base.models.enums.IdentificationType;
import com.bwongo.user_mgt.models.enums.RelationShipType;

import java.util.Arrays;
import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 5/7/23
 **/
public class BaseEnumValidation {

    private BaseEnumValidation() { }

    public static boolean isValidIdentificationType(String value){
        List<String> identificationTypes = Arrays.asList(
                IdentificationType.DRIVING_LICENSE.name(),
                IdentificationType.OTHER.name(),
                IdentificationType.PASSPORT.name(),
                IdentificationType.NATIONAL_ID.name()
        );
        return identificationTypes.contains(value);
    }

    public static boolean isRelationShipType(String value){
        List<String> relationShipTypes = Arrays.asList(
                RelationShipType.AUNT.name(),
                RelationShipType.HUSBAND.name(),
                RelationShipType.WIFE.name(),
                RelationShipType.SON.name(),
                RelationShipType.DAUGHTER.name(),
                RelationShipType.FATHER.name(),
                RelationShipType.MOTHER.name(),
                RelationShipType.GRANDFATHER.name(),
                RelationShipType.GRANDMOTHER.name(),
                RelationShipType.UNCLE.name(),
                RelationShipType.SISTER.name(),
                RelationShipType.BROTHER.name(),
                RelationShipType.COUSIN.name(),
                RelationShipType.NEPHEW.name(),
                RelationShipType.NIECE.name(),
                RelationShipType.OTHERS.name()
        );
        return relationShipTypes.contains(value);
    }
}
