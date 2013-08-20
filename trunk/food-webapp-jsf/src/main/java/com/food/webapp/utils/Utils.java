/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.food.webapp.utils;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author daniyar.artykov
 */
public class Utils {

    public static synchronized boolean checkDateEquals(Date d1, Date d2) {
        Calendar c1 = getCalendarWithDateOnly(d1);
        Calendar c2 = getCalendarWithDateOnly(d2);

        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DATE) == c2.get(Calendar.DATE)) {
            return true;
        }

        return false;
    }

    public static synchronized boolean checkDateAfter(Date d1, Date d2) {
        Calendar c1 = getCalendarWithDateOnly(d1);
        Calendar c2 = getCalendarWithDateOnly(d2);

        return c1.after(c2);
    }

    public static synchronized boolean checkDateBefore(Date d1, Date d2) {
        Calendar c1 = getCalendarWithDateOnly(d1);
        Calendar c2 = getCalendarWithDateOnly(d2);

        return c1.before(c2);
    }

    public static synchronized Calendar getCalendarWithDateOnly(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c;
    }
}