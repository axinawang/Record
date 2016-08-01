package com.xunbaola.record.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/31.
 */
public class DateTimeUtil {
    public static String getDateTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return sdf.format(date);
    }
}
