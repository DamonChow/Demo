package com.damon.test;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2015/12/17 11:31
 */
public class DateTest {

    public static void main(String[] args) {
        String[] dateFomat = new String[]{"yyyy-MM-dd HH:mm", "yyyy/MM/dd HH:mm"};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            DateUtils.parseDate("2015/12/08 13:45", dateFomat);
            DateUtils.parseDate("2015-12-06 18:43", dateFomat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
