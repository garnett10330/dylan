package com.momo.dylantest.util;


import com.alibaba.fastjson.JSONObject;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * 新版日期时间工具
 *
 * @auther wei
 */
public class DateTimeUtil {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    private static final DateTimeFormatter SHORT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter TINY_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATETIME_MILLISECOND_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    @Deprecated
    public static final String TIME_SPECIAL_GREP = "yyyy-MM-dd-HH-mm-ss";
    @Deprecated
    public static final String MILL_TIME_GREP = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final ZoneId BJ_ZONE_ID = ZoneId.of("Asia/Shanghai");

    private static final long HOUR12_MIN = 12 * 60;
    private static final long HOUR24_MIN = 24 * 60;

    /** 返回当前的日期 */
    public static LocalDate getCurrentLocalDate() {
        return LocalDate.now();
    }

    /**
     * 返回当前时间
     */
    public static LocalTime getCurrentLocalTime() {
        return LocalTime.now();
    }

    /** 返回当前日期时间 */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    /** yyyy-MM-dd  */
    public static String getCurrentDateStr() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    /** yyyyMMdd */
    public static String getCurrentShortDateStr() {
        return LocalDate.now().format(SHORT_DATE_FORMATTER);
    }

    /** yyMMdd */
    public static String getCurrentTinyDateStr() {
        return LocalDate.now().format(TINY_DATE_FORMATTER);
    }

    public static String getCurrentMonthStr() {
        return LocalDate.now().format(YEAR_MONTH_FORMATTER);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTimeStr() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }

    /** HH:mm:ss */
    public static String getCurrentTimeStr() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

    public static String getCurrentDateStr(String pattern) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getCurrentDateTimeStr(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getCurrentTimeStr(String pattern) {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate parseLocalDate(String dateStr, String pattern) {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern) {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalTime parseLocalTime(String timeStr, String pattern) {
        return LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalDate(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalDateTime(LocalDateTime datetime, String pattern) {
        return datetime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatLocalTime(LocalTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate parseLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DATETIME_FORMATTER);
    }

    /** HH:mm:ss */
    public static LocalTime parseLocalTime(String timeStr) {
        return LocalTime.parse(timeStr, TIME_FORMATTER);
    }

    public static String formatLocalDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static String formatLocalDateTime(LocalDateTime datetime) {
        return datetime.format(DATETIME_FORMATTER);
    }

    /** HH:mm:ss */
    public static String formatLocalTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }

    /** DateTime String (yyyy-MM-dd HH:mm:ss) */
    public static String formatDateTimeStr(TemporalAccessor temporal) {
        return DATETIME_FORMATTER.format(temporal);
    }

    /** Date String (yyyy-MM-dd) */
    public static String formatDateStr(TemporalAccessor temporal) {
        return DATE_FORMATTER.format(temporal);
    }

    /** Short Date String (yyyyMMdd) */
    public static String formatSortDateStr(TemporalAccessor temporal) {
        return SHORT_DATE_FORMATTER.format(temporal);
    }

    /** Tiny Date String (yyMMdd) */
    public static String formatTinyDateStr(TemporalAccessor temporal) {
        return TINY_DATE_FORMATTER.format(temporal);
    }

    /** DateTime MilliSecond String (yyyy-MM-dd HH:mm:ss.SSS) */
    public static String formatDateTimeMilliSecondStr(TemporalAccessor temporal) {
        return DATETIME_MILLISECOND_FORMATTER.format(temporal);
    }
    /**
     * 日期相隔秒
     */
    public static long periodHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Duration.between(startDateTime, endDateTime).get(ChronoUnit.SECONDS);
    }

    /**
     * 日期相隔天数
     */
    public static long periodDays(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.DAYS);
    }

    /**
     * 日期相隔周数
     */
    public static long periodWeeks(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.WEEKS);
    }

    /**
     * 日期相隔月数
     */
    public static long periodMonths(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.MONTHS);
    }

    /**
     * 日期相隔年数
     */
    public static long periodYears(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.YEARS);
    }

    /**
     * 是否当天
     */
    public static boolean isToday(LocalDate date) {
        return getCurrentLocalDate().equals(date);
    }

    /**
     * 获取当前毫秒数
     */
    public static Long toEpochMilli(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 判断是否为闰年
     */
    public static boolean isLeapYear(LocalDate localDate) {
        return localDate.isLeapYear();
    }

    /** 取得当月有几天 */
    public static int getCurrentMonthTotalDays() {
        return LocalDate.now().lengthOfMonth();
    }

    /** 取得当月有几天 */
    public static int getCurrentMonthTotalDays(LocalDate localDate) {
        return localDate.lengthOfMonth();
    }


    /** 取得当地今日日期 */
    public static LocalDate getCurrentLocalDateByZoneId(String zoneId) {
        return LocalDateTime.now().atZone(BJ_ZONE_ID).withZoneSameInstant(ZoneId.of(zoneId)).toLocalDate();
    }

    /** 取得当地本地时间 */
    public static LocalDateTime getCurrentLocalDateTimeByZoneId(String zoneId) {
        return LocalDateTime.now().atZone(BJ_ZONE_ID).withZoneSameInstant(ZoneId.of(zoneId)).toLocalDateTime();
    }

    /** 北京時區轉換到其它時區 */
    public static LocalDateTime convertBjTimeToOtherZoneId(LocalDateTime bjDateTime, String otherZoneId) {
        return bjDateTime.atZone(BJ_ZONE_ID).withZoneSameInstant(ZoneId.of(otherZoneId)).toLocalDateTime();
    }

    /** 其它時區轉換到北京時間 */
    public static LocalDateTime convertOtherTimeToBjZoneId(LocalDateTime otherDateTime, String otherZoneId) {
        return otherDateTime.atZone(ZoneId.of(otherZoneId)).withZoneSameInstant(BJ_ZONE_ID).toLocalDateTime();
    }

    /** 取得某日期时区所在的那一周的第一天和最后一天 */
    public static LocalDateTime[] getWeekRangeWithTimeZone(LocalDateTime someTime, String zoneId) {
        LocalDate otherDate = DateTimeUtil.convertBjTimeToOtherZoneId(someTime, zoneId).toLocalDate();
        LocalDateTime firstDateTime = otherDate.with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime lastDateTime = otherDate.with(DayOfWeek.SUNDAY).atStartOfDay();
        LocalDateTime[] arr = {DateTimeUtil.convertOtherTimeToBjZoneId(firstDateTime, zoneId),
                DateTimeUtil.convertOtherTimeToBjZoneId(lastDateTime, zoneId)};
        return arr;
    }

    /** 取得某日期时区所在的那一个月的第一天和最后一天 */
    public static LocalDateTime[] getMonthRangeWithTimeZone(LocalDateTime someTime, String zoneId) {
        LocalDate otherDate = DateTimeUtil.convertBjTimeToOtherZoneId(someTime, zoneId).toLocalDate();
        LocalDateTime firstDateTime = otherDate.withDayOfMonth(1).atStartOfDay();
        LocalDateTime lastDateTime = otherDate.withDayOfMonth(otherDate.lengthOfMonth()).atStartOfDay();
        LocalDateTime[] arr = {DateTimeUtil.convertOtherTimeToBjZoneId(firstDateTime, zoneId),
                DateTimeUtil.convertOtherTimeToBjZoneId(lastDateTime, zoneId)};
        return arr;
    }

    /**
     * 计算出某个日期所在的那一个月的第一天和最后一天
     * @param someDay 傳入日期
     * @return LocalDate[]
     */
    public static LocalDate[] getMonthFirstAndLastDay(LocalDate someDay){
        LocalDate firstDay = someDay.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDay = someDay.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate[] arr = new LocalDate[2];
        arr[0] = firstDay;
        arr[1] = lastDay;
        return arr;
    }

    /**
     * 计算出某个日期所在的那一周的第一天和最后一天
     * @param someDay 傳入日期
     * @return LocalDate[]
     */
    public static LocalDate[] getWeekFirstAndLastDay(LocalDate someDay){
        LocalDate monday = someDay.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);
        LocalDate sunday = someDay.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).minusDays(1);
        LocalDate[] arr = new LocalDate[2];
        arr[0] = monday;
        arr[1] = sunday;
        return arr;
    }

    public static LocalDate maxDate(LocalDate dataA, LocalDate dateB) {
        if (!dataA.isBefore(dateB)) {
            return dataA;
        } else {
            return dateB;
        }
    }


    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static void main(String[] args) {

        long timestamp = 1695398399563L;
        Timestamp ts = new Timestamp(timestamp);
        LocalDateTime receivedTime =  ts.toLocalDateTime();
        JSONObject json = new JSONObject();
        json.put("objDate", DateTimeUtil.asDate(receivedTime));
        json.put("objTimestamp",Timestamp.valueOf(receivedTime));
        System.out.println("obj = " + JSONObject.toJSONString(json));

        System.out.println("new Date = " + new Date());
        System.out.println("new Date JSONObject= " + JSONObject.toJSONString(new Date()));
    }

}
