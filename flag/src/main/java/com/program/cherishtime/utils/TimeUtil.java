package com.program.cherishtime.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

//@SuppressWarnings("unused")
public class TimeUtil {

    /**
     * 得到当前时刻的年月日时间
     * @return yyyyMMdd
     */
    public static String getYearMonthDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return format.format(System.currentTimeMillis());
    }

    /**
     * 将时分转换成当天对应的毫秒数
     * @param time HH:mm
     * @return 毫秒数
     */
    public static long time2Milliseconds(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH:mm", Locale.getDefault());
        SimpleDateFormat base = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        long ms = 0;
        try {
            Date date = format.parse(base.format(new Date()) + time);
            ms = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ms;
    }

    /**
     * 将当天时间毫秒数转换成对应的时分
     * @param ms 毫秒数
     * @return HH:mm
     */
    public static String milliseconds2Time(long ms) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return format.format(ms);
    }

    /**
     * 得到某一天某一时刻的毫秒数。
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 时
     * @param minute 分
     * @param second 秒
     * @return 毫秒数
     */
    public static long getMilliseconds(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(year, month, day, hour, minute, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 得到当前时刻时分的毫秒数。
     * @return 毫秒数
     */
    public static long getMilliseconds() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 得到某一时分在当天的毫秒数
     * @param hour 时
     * @param minute 分
     * @return 毫秒数
     */
    public static long getMilliseconds(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hour, minute, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getMilliseconds(String hour, String minute) {
        int h = Integer.valueOf(hour);
        int m = Integer.valueOf(minute);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), h, m, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 得到当前时分在次日同一时刻的毫秒数
     * @param hour 时
     * @param minute 分
     * @return 毫秒数
     */
    public static long getTomorrow(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), hour, minute, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTimeInMillis();
    }

    /**
     * 比较当前系统时间与指定时分大小
     * @param hour 时
     * @param minute 分
     * @return true : false ? 指定时分毫秒数 > 当前系统时间毫秒数
     */
    public static boolean compareTime(int hour, int minute) {
        return getMilliseconds(hour, minute) > System.currentTimeMillis();
    }

    /**
     * 得到当前时刻的日期
     * @return yyyyMMdd
     */
    public static int getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        long time = calendar.getTimeInMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return Integer.parseInt(format.format(time));
    }

    /**
     * 得到明天的日期
     * @return yyyyMMdd
     */
    public static int getDateOfTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return Integer.parseInt(format.format(calendar.getTimeInMillis()));
    }

    /**
     * 得到昨天的日期
     * @return yyyyMMdd
     */
    public static int getDateOfYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return Integer.parseInt(format.format(calendar.getTimeInMillis()));
    }
}
