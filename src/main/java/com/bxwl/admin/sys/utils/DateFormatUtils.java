package com.bxwl.admin.sys.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateFormatUtils {

    private static Logger logger = LoggerFactory.getLogger(DateFormatUtils.class);

    private static final String PATTERN_YYYYMMDD = "yyyy/MM/dd";
    private static final String PATTERN_YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";
    private static final String PATTERN_HHMMSS = "HH:mm:ss";

    /**
     * 转换为字符串，格式为yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String getYYYYMMDDStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYYMMDD);
        return sdf.format(date);
    }

    /**
     * 转换为字符串，格式为yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getYYYYMMDDHHMMSStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYYMMDDHHMMSS);
        return sdf.format(date);
    }

    /**
     * 转换为字符串，格式为HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getHHMMSSStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_HHMMSS);
        return sdf.format(date);
    }

    /**
     * 转换为date，格式为yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static Date getYYYYMMDDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYYMMDD);
        try {
            return sdf.parse(date);
        } catch (Exception ex) {
            logger.error("转换为date，格式为yyyy-MM-dd出错：", ex);
        }
        return null;
    }

    /**
     * 转换为date，格式为HH:mm:ss
     *
     * @param date
     * @return
     */
    public static Date getHHMMSSDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_HHMMSS);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            return sdf.parse(date);
        } catch (Exception ex) {
            logger.error("转换为date，格式为yyyy-MM-dd出错：", ex);
        }
        return null;
    }

    /**
     * 转换为date，格式为yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static Date getYYYYMMDDHHMMSSDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYYMMDDHHMMSS);
        try {
            return sdf.parse(date);
        } catch (Exception ex) {
            logger.error("转换为date，格式为yyyy-MM-dd HH:mm:ss出错：", ex);
        }
        return null;
    }

    /**
     * 转换为date，格式为yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static Date dateStrToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(date);
        } catch (Exception ex) {
            logger.error("转换为date，格式为yyyy-MM-dd HH:mm:ss出错：", ex);
        }
        return null;
    }

    /**
     * 计算两个日期差多少分钟
     *
     * @param endDate
     * @param nowDate
     * @return
     */
    public static long getDatePoor(Date endDate, Date nowDate) {
        long ns = 1000;
        long diff = endDate.getTime() - nowDate.getTime();
        long sec = diff / ns;
        return sec;
    }

    /**
     * 计算两个日期差多少分钟
     *
     * @param endDate
     * @param nowDate
     * @return
     */
    public static long getDatePoor(String endDate, String nowDate) {
        long ns = 1000;
        long diff = getHHMMSSDate(endDate).getTime() - getHHMMSSDate(nowDate).getTime();
        long sec = diff / ns;
        return sec;
    }


    /**
     * 一个时间段是否在另一个时间段内
     *
     * @param date1
     * @param date2
     * @param startTime
     * @param endDate
     * @return
     */
    public static Boolean timeSectionIntimeSection(String date1, String date2, String startTime, String endDate) {
        long d1 = getHHMMSSDate(date1).getTime();
        long d2 = getHHMMSSDate(date2).getTime();
        long start = getHHMMSSDate(startTime).getTime();
        long end = getHHMMSSDate(endDate).getTime();
        if (((d1 - start) >= 0) && ((end - d2) >= 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断curTime是否在startTime和endTime之间
     *
     * @param curTime
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean timeIntimeSection(String curTime, String startTime, String endTime) {
        long curStamp = getHHMMSSDate(curTime).getTime();
        long startStamp = getHHMMSSDate(startTime).getTime();
        long endStamp = getHHMMSSDate(endTime).getTime();
        if (startStamp <= curStamp && curStamp <= endStamp) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当天开始时间
     *
     * @return
     */
    public static String currentDayStart() {
        LocalDateTime today_start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        String start = today_start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return start;
    }

    /**
     * 获取当天结束时间
     *
     * @return
     */
    public static String currentDayEnd() {
        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        String end = today_end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return end;
    }

    /**
     * 获取当月开始时间
     *
     * @return
     */
    public static String currentMonthStart() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
        Date dates = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
        return sdf.format(dates);
    }

    /**
     * 获取当月结束时间
     *
     * @return
     */
    public static String currentMonthEnd() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX);
        Date dates = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
        return sdf.format(dates);
    }

    public static boolean compareToTime(String time1, String time2) {
        long time1Stamp = getHHMMSSDate(time1).getTime();
        long time2Stamp = getHHMMSSDate(time2).getTime();
        if (time1Stamp - time2Stamp > 0) {
            return true;
        } else {
            return false;
        }
    }

}
