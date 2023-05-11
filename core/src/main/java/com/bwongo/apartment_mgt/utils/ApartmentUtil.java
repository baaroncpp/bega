package com.bwongo.apartment_mgt.utils;

import com.bwongo.apartment_mgt.models.enums.ApartmentType;
import com.bwongo.apartment_mgt.models.enums.ManagementFeeType;
import com.bwongo.apartment_mgt.models.enums.RentPeriod;
import com.bwongo.commons.models.text.StringUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
public class ApartmentUtil {

    private ApartmentUtil() { }

    public static boolean isApartmentType(String value){
        List<String> apartmentTypes = Arrays.asList(
                ApartmentType.CONDO.name(),
                ApartmentType.CO_OP.name(),
                ApartmentType.LOW_RISE.name(),
                ApartmentType.MID_RISE.name(),
                ApartmentType.HIGH_RISE.name(),
                ApartmentType.DUPLEX.name(),
                ApartmentType.WALK_UP.name(),
                ApartmentType.RAIL_ROAD.name(),
                ApartmentType.PENTHOUSE.name(),
                ApartmentType.GARDEN.name(),
                ApartmentType.LOFT.name(),
                ApartmentType.STUDIO.name(),
                ApartmentType.OTHER.name()

        );

        return apartmentTypes.contains(value);
    }

    public static boolean isManagementFeeType(String value){
        List<String> managementFeeTypes = Arrays.asList(
                ManagementFeeType.FIXED_AMOUNT.name(),
                ManagementFeeType.PERCENTAGE.name()
        );

        return managementFeeTypes.contains(value);
    }

    public static boolean isRentPeriod(String value){
        List<String> rentPeriodList = Arrays.asList(
                RentPeriod.MONTHLY.name(),
                RentPeriod.WEEKLY.name()
        );

        return rentPeriodList.contains(value);
    }

    public static String generateHouseReferenceNumber(String apartmentName){
        return apartmentName.toLowerCase().substring(0, 3) +
                StringUtil.randomString().substring(0, 12);
    }
}
