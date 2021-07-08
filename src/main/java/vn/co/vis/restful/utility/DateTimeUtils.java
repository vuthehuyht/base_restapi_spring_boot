package vn.co.vis.restful.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static vn.co.vis.restful.constant.DateTime.YYYY_MM_DD_SLASH;

/**
 * @author Giang Thanh Quang
 * @since 10/16/2020
 */
public class DateTimeUtils {
    public static Date getCurrentTime() {
        return new Date();
    }

    public static Date toDateFromStr(String dateString, String format) {
        DateFormat dateTimeFormat = new SimpleDateFormat(format);
        try {
            Date date = dateTimeFormat.parse(dateString);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int compareDate(Date date1, Date date2, String format) {
        String date1Str = toDateString(date1, format);
        Date dateOne = toDateFromStr(date1Str, format);
        String date2Str = toDateString(date2, format);
        Date dateTwo = toDateFromStr(date2Str, format);
        return dateOne.compareTo(dateTwo);
    }

    public static int compareDate(Date date1, Date date2) {
        return compareDate(date1, date2, YYYY_MM_DD_SLASH);
    }

    public static String toDateString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date addDayToDate(Date date, int value) {
        return getFutureDate(date, value, Calendar.DAY_OF_MONTH);
    }

    public static Date getFutureDate(Date date, int value, int unit) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(unit, value);
        return calendar.getTime();
    }
}
