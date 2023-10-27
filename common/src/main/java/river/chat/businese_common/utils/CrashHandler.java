package river.chat.businese_common.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import river.chat.businese_common.report.ReportManager;
import river.chat.businese_common.report.TrackerEventName;
import river.chat.businese_common.report.TrackerKeys;
import river.chat.lib_core.utils.log.LogUtil;

/**
 * Created by CH3CHO on 2016/1/29.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String LOG_TAG = CrashHandler.class.getSimpleName();
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd_HH-mm-ss.SSS",
            Locale.CHINA);

    private static CrashHandler instance; // 单例模式

    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }

        return instance;
    }

    private Context context; // 程序Context对象
    private Thread.UncaughtExceptionHandler defaultHandler; // 系统默认的UncaughtException处理类

    private CrashHandler() {
    }

    /**
     * 初始化异常处理器
     */
    public void init(Context context) {
        this.context = context;
        // 获取系统默认的UncaughtException处理器
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

//    java.lang.NullPointerException: Attempt to invoke interface method 'int java.lang.CharSequence.length()' on a null object reference
    /**
     * 处理未被应用捕获的异常
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        boolean res = handleException(ex);
        if ( defaultHandler != null) {
            Map params = new HashMap();
            params.put(TrackerKeys.CRASH_MSG, ex.getMessage());
            params.put(TrackerKeys.CRASH_DEVICE, collectDeviceInfo(context));
            LogUtil.e(LOG_TAG, "crash:" + ex.getMessage());
            ReportManager.INSTANCE.reportEvent(TrackerEventName.CRASH, params);
            // 如果用户没有处理则让系统默认的异常处理器来处理
            defaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LogUtil.e(LOG_TAG, "error : " + e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

    }

    /**
     * 自定义错误处理，收集错误信息、发送错误报告等操作均在此完成。
     *
     * @return 如果处理了该异常信息，返回true；反之返回false。
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }

        Map<String, String> deviceInfo = collectDeviceInfo(context);
        return true;
    }

    /**
     * 收集设备参数信息
     */
    public Map<String, String> collectDeviceInfo(Context ctx) {

        Map<String, String> deviceInfo = new HashMap<>();

        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                                               PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName =
                        pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                deviceInfo.put("versionName", versionName);
                deviceInfo.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(LOG_TAG,
                      "Error occurred when collecting package info." + e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                deviceInfo.put(field.getName(),
                               field.get(null)
                                       .toString());
                Log.d(LOG_TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                LogUtil.e(LOG_TAG,
                          "Error occurred when collecting device info." + e);
            }
        }

        return deviceInfo;
    }

}