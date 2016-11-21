package com.example.liujiachao.gank.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by liujiachao on 2016/10/14.
 */
public class Dater {

    public final static String DATE_FORMAT = "yyyy-MM-dd";
    public final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public final static int TIME_DAY_MILLISECOND = 86400000;

    public static String parseTime(String time) {
        String[] newStr = time.split("T");
        String publishDate = newStr[0];
        Date dt = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        String currentDate = formatter.format(dt);
        int intval = getDaysBetweenDates(publishDate,currentDate);
        if (intval == 0) {
            return "今天";
        } else if (intval  == 1) {
            return "昨天";
        } else if (intval == 2) {
            return "前天";
        } else if (intval > 2 && intval < 31) {
            return intval + "天前";
        } else {
            return publishDate;
        }
    }

    public static String getDate(String time) {
        String[] newStr = time.split("T");
        String publishDate = newStr[0];
        return publishDate;
    }

    public static int getDaysBetweenDates(String first, String second) {
        Date d1 = getFormatDateTime(first,DATE_FORMAT);
        Date d2 = getFormatDateTime(second,DATE_FORMAT);
        Long mils = (d2.getTime() - d1.getTime())/(TIME_DAY_MILLISECOND);
        return mils.intValue();
    }

    public static Date getFormatDateTime(String currDate,String format) {
        if (currDate == null) {
            return null;
        }

        SimpleDateFormat dtFormatdB;
        try {
            dtFormatdB = new SimpleDateFormat(format);
            return dtFormatdB.parse(currDate);
        } catch (Exception e) {
            dtFormatdB = new SimpleDateFormat(TIME_FORMAT);
            try {
                return dtFormatdB.parse(currDate);
            } catch (Exception ex) {

            }
        }
        return null;
    }
}
