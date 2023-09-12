package river.chat.lib_core.utils.common;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;

/**
 * 时间相关辅助工具
 */
public class TimeUtils {

    public static final String PATTERN_Y_TEXT_M_TEXT_D_TEXT = "yyyy年MM月dd日";
    public static final String TEXT_M_TEXT_D_TEXT = "MM月dd日";

    public static final String PATTERN_Y_SLASH_M_SLASH_D_H_COMMA_M = "yyyy/MM/dd HH:mm";
    public static final String PATTERN_Y_SLASH_M_SLASH_D_H_COMMA_M_S = "yyyy/MM/dd HH:mm:ss";
    public static final String PATTERN_Y_SLASH_M_SLASH_D = "yyyy/MM/dd";
    public static final String PATTERN_Y_POINT_M_POINT_D = "yyyy.MM.dd";

    private final static int SECOND_IN_HOUR = 3600;//1h=3600s
    private final static int SECOND_IN_MINUTE = 60;//1m=60s
    private final static int MINUTE_IN_HOUR = 60;//1h=60m
    /**
     * 一天秒数
     */
    private final static int SECONDS_IN_DAYS = 24 * SECOND_IN_HOUR;

    public static final long MILLISECONDS_IN_DAYS = SECONDS_IN_DAYS * 1000;
    /**
     * 一小时毫秒数
     */
    public static final int MILLISECONDS_IN_HOUR = 60 * 60 * 1000;

    /**
     * 格式化视频录制时间（时间格式：hh:mm:ss'）
     *
     * @param seconds 视频录制时间（单位秒）
     */
    public static String getFormatRecordTime(long seconds) {
        long hh = seconds / SECOND_IN_HOUR;
        long mm = seconds % SECOND_IN_HOUR;
        long ss = mm % SECOND_IN_MINUTE;
        mm = mm / SECOND_IN_MINUTE;

        return (hh == 0 ? "" : (hh < 10 ? "0" + hh : hh) + ":") +
                (mm == 0 ? "00:" : (mm < 10 ? "0" + mm : mm) + ":")
                + (ss == 0 ? "00" : (ss < 10 ? "0" + ss : ss));
    }

    /**
     * 获取时间时分秒：时间格式：hh:mm:ss
     */
    public static String getFormatHMSTime(long seconds) {
        long hh = seconds / SECOND_IN_HOUR;
        long mm = seconds % SECOND_IN_HOUR;
        long ss = mm % SECOND_IN_MINUTE;
        mm = mm / SECOND_IN_MINUTE;

        return (hh == 0 ? "00:" : (hh < 10 ? "0" + hh : hh) + ":") +
                (mm == 0 ? "00:" : (mm < 10 ? "0" + mm : mm) + ":")
                + (ss == 0 ? "00" : (ss < 10 ? "0" + ss : ss));
    }

    /**
     * 转时间显示不用0补充单个数字的前面
     */
    public static String millsecondsToStrNoZeroHead(int seconds) {
        seconds = seconds / 1000;
        String result = "";
        int min = 0, second = 0;
        min = (seconds) / 60;
        second = seconds - min * 60;
        if (min < 10) {
            result += +min + ":";
        } else {
            result += min + ":";
        }
        if (second < 10) {
            result += "0" + second;
        } else {
            result += second;
        }
        return result;
    }

    public static String millsecondsToStr(int seconds) {
        seconds = seconds / 1000;
        String result = "";
        int min = 0, second = 0;
        min = (seconds) / 60;
        second = seconds - min * 60;
        if (min < 10) {
            result += "0" + min + ":";
        } else {
            result += min + ":";
        }
        if (second < 10) {
            result += "0" + second;
        } else {
            result += second;
        }
        return result;
    }

    public static String formatMillsecondsToStr(int seconds) {
        seconds = seconds / 1000;
        String result = "";
        int min = 0, second = 0;
        min = (seconds) / 60;
        second = seconds - min * 60;
        if (min < 10) {
            result += "0" + min + "分";
        } else {
            result += min + "分";
        }
        if (second < 10) {
            result += "0" + second + "秒";
        } else {
            result += second + "秒";
        }
        return result;
    }

    /**
     * 计算时长不能超过最大时长
     *
     * @param seconds 当前时长
     * @param max     最大时长
     */
    public static String millsecondsToStr(int seconds, int max) {
        if (max > 0 && seconds > max) {
            seconds = max;
        }
        seconds = seconds / 1000;
        String result = "";
        int min = 0, second = 0;
        min = (seconds) / 60;
        second = seconds - min * 60;
        if (min < 10) {
            result += "0" + min + ":";
        } else {
            result += min + ":";
        }
        if (second < 10) {
            result += "0" + second;
        } else {
            result += second;
        }
        return result;
    }

    public static String secondsToStr(long seconds) {
        int hours = (int) (seconds / 3600);
        int minutes = (int) ((seconds - hours * 3600) / 60);
        seconds = seconds % 60;
        String result = "";
        if (hours >= 10) {
            result += hours + ":";
        } else if (hours > 0) {
            result += "0" + hours + ":";
        }
        if (minutes < 10) {
            result += "0" + minutes + ":";
        } else {
            result += minutes + ":";
        }
        if (seconds < 10) {
            result += "0" + seconds;
        } else {
            result += seconds;
        }
        return result;
    }

    public static String secondsTomMMDDStr(long seconds) {
        int minutes = (int) seconds / 60;
        seconds = seconds % 60;
        String result = "";
        if (minutes < 10) {
            result += "0" + minutes + ":";
        } else {
            result += minutes + ":";
        }
        if (seconds < 10) {
            result += "0" + seconds;
        } else {
            result += seconds;
        }
        return result;
    }

    public static String secondsToMinute(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds - hours * 3600) / 60;
        seconds = seconds % 60;
        String result = "时长 ";
        if (hours > 0) {
            result += hours + ":";
        }
        result += minutes + "'" + seconds + "''";
        return result;
    }

    public static String millSecondsToChineseStr(int seconds) {
        seconds = seconds / 1000;
        String result = "";
        int min = 0, second = 0;
        min = (seconds) / 60;
        second = seconds - min * 60;
        if (min < 10) {
            result += "0" + min + "分";
        } else {
            result += min + "分";
        }
        if (second < 10) {
            result += "0" + second + "秒";
        } else {
            result += second + "秒";
        }
        return result;
    }

    /**
     * 两个时间相隔秒数
     */
    public static long betweenSecs(long time1, long time2) {
        return Math.abs(time1 - time2) / 1000;
    }


    /**
     * 当天凌晨时间戳
     */
    public static long getStartTimestamp() {
        long now = System.currentTimeMillis() / 1000L;
        long daySecond = 60 * 60 * 24;
        long dayTime = now - (now + 8 * 3600) % daySecond;
        return dayTime * 1000;
    }

    /**
     * 获取固定日期时间戳
     *
     * @param timeStr 固定日期
     *
     * @return 时间戳
     */
    public static long getRegularTimeStamp(String timeStr) {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = df.parse(timeStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            long timestamp = cal.getTimeInMillis();
            return timestamp;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 计算两个日期之间相差多少天/多少小时多少分钟
     * 大于24小时：天
     * 小于24小时：小时分钟
     */
    public static String getDifferenceDayOrHM(long millis1, long millis2) {
        long time = Math.abs(millis1 - millis2) / (1000 * 60 * 60);
        long days = time / 24;
        if (days > 0) {
            return days + "天";
        }
        long hours = time % 24;
        long minute = Math.abs(millis1 - millis2) / (1000 * 60) - hours * 60;
        return String.format("%02d", hours) + "小时" + String.format("%02d",
                                                                     minute) + "分钟";
    }

    /**
     * 计算两个日期之间相差多少天
     * 大于24小时：天
     * 小于24小时：小时分钟
     */
    public static String getDifferenceDay(long millis1, long millis2) {
        long time = Math.abs(millis1 - millis2) / (1000 * 60 * 60);
        long days = time / 24;
        if (days > 0) {
            return days + "天";
        } else {
            return "1天";
        }
    }

    /**
     * 时间字符串转换成long类型秒数
     * 首先判断 : 出现的次数
     *
     * @param dataTime hh:mm:ss 或者 mm:ss
     */
    public static long getSecondsByTime(@NonNull String dataTime) {
        String key = ":";
        long result = 0L;
        int count = 0;   //出现次数
        int[] keyPosition = new int[2]; //出现位置

        for (int i = 0; i < dataTime.length(); i++) {
            if (key.equals(String.valueOf(dataTime.charAt(i)))) {
                count++;
                keyPosition[count] = i;
            }
        }

        if (count == 1) {
            //分
            long mm = Integer.parseInt(dataTime.substring(0,
                                                          dataTime.indexOf(key)));
            result += mm * 60;
            //秒
            int ss = Integer.parseInt(dataTime.substring(dataTime.indexOf(key) + 1));
            result += ss;

        } else if (count == 2) {
            //时
            long hh = Integer.parseInt(dataTime.substring(0,
                                                          dataTime.indexOf(
                                                                  keyPosition[0])));
            result += hh * 3600;
            //分
            long mm = Integer.parseInt(dataTime.substring(dataTime.indexOf(
                    keyPosition[0] + 1,
                    dataTime.indexOf(keyPosition[1]))));
            result += mm * 60;
            //秒
            long ss = Integer.parseInt(dataTime.substring(dataTime.indexOf(
                    keyPosition[1]) + 1));
            result += ss;
        }
        keyPosition = null;
        return result;
    }

    /**
     * 拼接当前系统时间为:202071418524
     */
    public static String montageSystemTime() {
        Calendar calendar = Calendar.getInstance();
        //获取系统的日期
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH) + 1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //获取系统时间
        //小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //分钟
        int minute = calendar.get(Calendar.MINUTE);
        //秒
        int second = calendar.get(Calendar.SECOND);
        return year + month + day + hour + minute + second + "";
    }

    /**
     * 返回类似 "(DD天)HH时MM分SS秒"的格式
     * 天数为0不显示
     */
    @SuppressLint("DefaultLocale")
    public static String getChiDDHHMMSS(long millis) {
        int seconds = (int) (millis / 1000);

        // day
        int days = seconds / SECONDS_IN_DAYS;
        seconds -= days * SECONDS_IN_DAYS;

        // hour
        int hour = seconds / SECOND_IN_HOUR;
        seconds -= hour * SECOND_IN_HOUR;

        // min
        int min = seconds / SECOND_IN_MINUTE;

        // second
        seconds -= min * SECOND_IN_MINUTE;

        StringBuilder builder = new StringBuilder();
        if (days > 0) {
            builder.append(days)
                    .append("天");
        }
        builder.append(String.format("%02d", hour))
                .append("时")
                .append(String.format("%02d", min))
                .append("分")
                .append(String.format("%02d", seconds))
                .append("秒");

        return builder.toString();
    }

    /**
     * 标准展示时间逻辑
     *
     * @param millis 展示毫秒数
     */
    @SuppressLint("SimpleDateFormat")
    public static String getChiDefaultYYYYOrMMDDOrDDHHMM(long millis) {
        Calendar target = Calendar.getInstance();
        target.setTime(new Date(millis));

        Calendar current = Calendar.getInstance();

        // 同年
        if (target.get(Calendar.YEAR) == current.get(Calendar.YEAR)) {
            // 今天
            if (target.get(Calendar.DAY_OF_YEAR) == current.get(Calendar.DAY_OF_YEAR)) {
                return "今天 " + new SimpleDateFormat("HH:mm").format(target.getTime());
            } else if (target.get(Calendar.DAY_OF_YEAR) == current.get(Calendar.DAY_OF_YEAR) - 1) {
                return "昨天 " + new SimpleDateFormat("HH:mm").format(target.getTime());
            } else {
                return new SimpleDateFormat("MM月dd日").format(target.getTime());
            }
        }
        // 隔年
        else {
            return new SimpleDateFormat(PATTERN_Y_TEXT_M_TEXT_D_TEXT,
                                        Locale.CHINA).format(target.getTime());
        }
    }

    /**
     * 当前时间的yyyy-MM-dd格式字符串
     */
    public static String getTimeStamp() {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前的日期
        String todayTime = df.format(new Date());
        return todayTime;
    }

    /**
     * {@code milli1}是否和{@code milli2}是一天, 不计算时分秒
     */
    public static boolean isSameDay(long milli1, long milli2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTimeInMillis(milli1);
        c2.setTimeInMillis(milli2);

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.DATE) == c2.get(Calendar.DATE);
    }

    /**
     * 目标的年月日是否小于当前年月日
     *
     * @param current 当前时间
     * @param target  目标时间
     */
    public static boolean isPastDay(long current, long target) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTimeInMillis(current);
        c2.setTimeInMillis(target);

        return c1.get(Calendar.YEAR) > c2.get(Calendar.YEAR) ||
                c1.get(Calendar.MONTH) > c2.get(Calendar.MONTH) ||
                c1.get(Calendar.DATE) > c2.get(Calendar.DATE);
    }

    /**
     * 返回 "6天2小时49分钟的格式
     */
    @SuppressLint("DefaultLocale")
    public static String getDHHMMSS(long millis) {
        int seconds = (int) (millis / 1000);

        // day
        int days = seconds / SECONDS_IN_DAYS;
        seconds -= days * SECONDS_IN_DAYS;

        // hour
        int hour = seconds / SECOND_IN_HOUR;
        seconds -= hour * SECOND_IN_HOUR;

        // min
        int min = seconds / SECOND_IN_MINUTE;

        // second
        seconds -= min * SECOND_IN_MINUTE;

        StringBuilder builder = new StringBuilder();
        if (days > 0) {
            builder.append(days)
                    .append("天");
        }
        builder.append(hour)
                .append("小时")
                .append(min)
                .append("分钟");
        if (days == 0) {
            builder.append(seconds)
                    .append("秒");
        }

        return builder.toString();
    }

}
