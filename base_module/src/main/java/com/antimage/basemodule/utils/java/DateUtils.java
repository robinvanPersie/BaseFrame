package com.antimage.basemodule.utils.java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

/**
 * Created by xuyuming on 2018/10/22.
 */

public class DateUtils {

    private static final SimpleDateFormat dateNormalFormat = new SimpleDateFormat("yyyy.MM.dd");

    public static String dateToString(Date date) {
        if (date == null) return "";
        try {
            String str = dateNormalFormat.format(date);
            return str;
        } catch (Exception e) {
            Timber.e("format date error");
        }
        return "";
    }

    public static Date stringToDate(String dateStr) {
        try {
            Date date = dateNormalFormat.parse(dateStr);
            return date;
        } catch (ParseException e) {
            return new Date(0);
        }
    }
}
