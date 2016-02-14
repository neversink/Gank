package com.neversink.gank.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by never on 16/1/27.
 */
public class DateUtil {

    public static String toDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }

    public static boolean isTheSameDay(Date one, Date another) {
        return toDate(one).equals(toDate(another));
    }

    public static String toDate(Date date, int add) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, add);
        calendar.get(Calendar.DATE);
        return toDate(calendar.getTime());
    }

    public static int calDays(Date one, Date another) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(one);
        calendar2.setTime(another);
        int delta = calendar1.get(Calendar.DAY_OF_YEAR) - calendar2.get(Calendar.DAY_OF_YEAR)
                + (calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR)) * (calendar2.get(Calendar.YEAR) % 4 == 0 ? 366 : 365);
        com.neversink.gank.util.LogUtil.w("delta" + delta );
        return delta;
    }
}
