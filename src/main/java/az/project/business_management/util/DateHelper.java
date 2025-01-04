package az.project.business_management.util;

import java.util.Date;

public class DateHelper {
    public static Long convertUTCDateToAzeDateWithMillis(Date date){
        return date.getTime()+14400000L;

    }
    public static Date convertAzeDateToUTC(Long azeDateWithMillis) {
        return new Date(azeDateWithMillis-14400000L);
    }

}
