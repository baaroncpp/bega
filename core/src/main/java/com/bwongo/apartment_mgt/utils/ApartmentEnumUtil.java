package com.bwongo.apartment_mgt.utils;

import com.bwongo.apartment_mgt.models.enums.ApartmentType;
import com.bwongo.apartment_mgt.models.enums.ManagementFeeType;
import com.bwongo.apartment_mgt.models.enums.RentPeriod;
import com.bwongo.tenant_mgt.models.enums.OccupationStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 4/28/23
 **/
public class ApartmentEnumUtil {

    private ApartmentEnumUtil() { }

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
}
