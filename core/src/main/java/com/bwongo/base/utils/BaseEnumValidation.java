package com.bwongo.base.utils;

import com.bwongo.base.models.enums.FileType;
import com.bwongo.base.models.enums.IdentificationType;
import com.bwongo.base.models.enums.RelationShipType;
import com.bwongo.base.models.enums.UserTypeEnum;

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
                IdentificationType.DRIVING_PERMIT.name(),
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

    public static boolean isFileType(String value){
        List<String> fileTypeList = Arrays.asList(
                FileType.IMAGE.name(),
                FileType.VIDEO.name(),
                FileType.DOCUMENT.name()
        );
        return fileTypeList.contains(value);
    }

    public static boolean isUserType(String value){
        List<String> userTypeEnumList = List.of(
                UserTypeEnum.ADMIN.name(),
                UserTypeEnum.LANDLORD.name(),
                UserTypeEnum.TENANT.name(),
                UserTypeEnum.PROPERTY_MANAGER.name()
        );
        return userTypeEnumList.contains(value);
    }
}
