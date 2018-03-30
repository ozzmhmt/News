package co.appstorm.newsx.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
/**
 * Created by ozzmhmt on 4/2/2018.
 */

public class DateUtils {
    public static SimpleDateFormat formatDateDefault = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);
    private static SimpleDateFormat formatDate = new SimpleDateFormat(
            "MMM dd", Locale.ENGLISH);
    private static SimpleDateFormat formatDateHour = new SimpleDateFormat(
            "MMM dd, HH:mm a", Locale.ENGLISH);
    private static SimpleDateFormat formatToday = new SimpleDateFormat(
            "HH:mm a", Locale.ENGLISH);
    private static SimpleDateFormat formatYesterday = new SimpleDateFormat(
            "HH:mm a", Locale.ENGLISH);
    private static SimpleDateFormat formatToWeek = new SimpleDateFormat(
            "EEE", Locale.ENGLISH);
    private static SimpleDateFormat formatToWeekHour = new SimpleDateFormat(
            "EEE HH:mm a", Locale.ENGLISH);
    private static SimpleDateFormat formatToWeekTimer = new SimpleDateFormat(
            "MMM dd", Locale.ENGLISH);
    private static SimpleDateFormat formatDatePreYear = new SimpleDateFormat(
            "MMM dd, yyyy", Locale.ENGLISH);
    private static SimpleDateFormat formatDatePreYearHour = new SimpleDateFormat(
            "MM/dd/yy HH:mm a", Locale.ENGLISH);

    public static String getDateTime() {
        Calendar calendar = Calendar.getInstance();
        return formatDateDefault.format(calendar.getTime());
    }

    public static String displayTodayDateList(String date) {
        if (date == null)
            return "";
        String resultDate = date;
        SimpleDateFormat formatCatchAll;
        String formattedString = determineDateFormat(date);
        if (formattedString != null){
            formatCatchAll = new SimpleDateFormat(formattedString,Locale.ENGLISH);
            try {
                Date parsedDate = formatCatchAll.parse(date);
                if (android.text.format.DateUtils.isToday(parsedDate.getTime())) {
                    resultDate = formatToday.format(parsedDate);
                } else if (checkIsYesterday(parsedDate)) {
                    resultDate = "Yesterday";
                } else if (isDateInCurrentWeek(parsedDate)) {
                    resultDate = formatToWeek.format(parsedDate);
                } else if (checkIsPreYear(parsedDate)) {
                    resultDate = formatDatePreYear.format(parsedDate);
                } else {
                    resultDate = formatCatchAll.format(parsedDate);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            resultDate = date;
        }


        return resultDate;
    }

    public static String displayTodayDateDetails(String date) {
        String resultDate = date;
        try {
            Date date1 = formatDateDefault.parse(date);
            if (android.text.format.DateUtils.isToday(date1.getTime())) {
                resultDate = formatToday.format(date1);
            } else if (checkIsYesterday(date1)) {
                resultDate = "Yesterday, " + formatYesterday.format(date1);
            } else if (isDateInCurrentWeek(date1)) {
                resultDate = formatToWeekHour.format(date1);
            } else if (checkIsPreYear(date1)) {
                resultDate = formatDatePreYearHour.format(date1);
            } else {
                resultDate = formatDateHour.format(date1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultDate;
    }

    private static boolean checkIsYesterday(Date date) {
        Calendar c1 = Calendar.getInstance(); // today
        c1.add(Calendar.DAY_OF_YEAR, -1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date);
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
    }

    private static boolean checkIsPreYear(Date date) {
        Calendar appYear = Calendar.getInstance(); // current year
        Calendar curYear = Calendar.getInstance();
        curYear.setTime(date);
        return appYear.get(Calendar.YEAR) > curYear.get(Calendar.YEAR);
    }

    private static boolean isDateInCurrentWeek(Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        int week = currentCalendar.get(Calendar.WEEK_OF_YEAR);
        int year = currentCalendar.get(Calendar.YEAR);
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(date);
        int targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR);
        int targetYear = targetCalendar.get(Calendar.YEAR);
        return week == targetWeek && year == targetYear;
    }

    
    /** Source code from https://stackoverflow.com/questions/3389348/parse-any-date-in-java*/
    private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{8}$", "yyyyMMdd");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{12}$", "yyyyMMddHHmm");
        put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
        put("^\\d{14}$", "yyyyMMddHHmmss");
        put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
    }};

    /**
     * Determine SimpleDateFormat pattern matching with the given date string. Returns null if
     * format is unknown. You can simply extend DateUtil with more formats if needed.
     * @param dateString The date string to determine the SimpleDateFormat pattern for.
     * @return The matching SimpleDateFormat pattern, or null if format is unknown.
     * @see SimpleDateFormat
     */
    public static String determineDateFormat(String dateString) {
        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                return DATE_FORMAT_REGEXPS.get(regexp);
            }
        }
        return null; // Unknown format.
    }
}
