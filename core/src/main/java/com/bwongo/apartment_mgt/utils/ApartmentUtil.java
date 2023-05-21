package com.bwongo.apartment_mgt.utils;

import com.bwongo.apartment_mgt.models.enums.ApartmentType;
import com.bwongo.apartment_mgt.models.enums.ApprovalStatus;
import com.bwongo.apartment_mgt.models.enums.ManagementFeeType;
import com.bwongo.apartment_mgt.models.enums.RentPeriod;
import com.bwongo.apartment_mgt.models.jpa.House;
import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.commons.models.text.StringUtil;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.tenant_mgt.models.enums.BillingDuration;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.bwongo.apartment_mgt.utils.ApartmentMsgConstants.*;

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

    public static boolean isBillingDuration(String value){
        List<String> billingDurationList = Arrays.asList(
                BillingDuration.ANNUAL.name(),
                BillingDuration.QUARTERLY.name(),
                BillingDuration.MONTHLY.name(),
                BillingDuration.WEEKLY.name(),
                BillingDuration.DAILY.name()
        );

        return billingDurationList.contains(value);
    }

    public static boolean isApprovalStatus(String value){
        List<String> approvalStatusList = Arrays.asList(
                ApprovalStatus.APPROVED.name(),
                ApprovalStatus.REJECTED.name()
        );

        return approvalStatusList.contains(value);
    }

    public static String generateHouseReferenceNumber(String apartmentName){
        return apartmentName.toLowerCase().substring(0, 3) +
                StringUtil.randomString().substring(0, 12);
    }

    public static void isHouseAssignable(House house){
        Validate.isTrue(house.isActive(), ExceptionType.BAD_REQUEST, HOUSE_IS_INACTIVE);
        Validate.isTrue(house.getIsOccupied(), ExceptionType.BAD_REQUEST, HOUSE_IS_OCCUPIED);
        Validate.isTrue(house.getRentFee().compareTo(BigDecimal.ZERO) > 0, ExceptionType.BAD_REQUEST, RENT_FEE_NOT_ZERO);
    }

    public static boolean validateAssignHouseDate(String stringDate){

        SimpleDateFormat dateFormat = new SimpleDateFormat(PLACEMENT_DATE_FORMAT);
        dateFormat.setLenient(false);

        try{
            dateFormat.parse(stringDate);
        }catch (ParseException e){
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
