package river.chat.lib_core.utils.system;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by Circle on 2018/11/1
 */
public class DeviceUtil {

    /**
     * 随机生成设备号
     */
    private static String getCustomDeviceId() {
//        String deviceId = CacheUtil.getCache("custom_deviceId");
//        if (TextUtils.isEmpty(deviceId)) {
//            DEVICE_ID = EncryptKt.encryptMD5(System.nanoTime() + "@kela");
//            if (TextUtils.isEmpty(DEVICE_ID)) {
//                DEVICE_ID = UUID.randomUUID() + "";
//                if (DEVICE_ID.contains("-")) {
//                    DEVICE_ID = DEVICE_ID.replace("-", "");
//                }
//
//            }
//            CacheUtil.saveCache("tag_deviceid", DEVICE_ID);
//        }
//        return DEVICE_ID;
        return "";
    }

    /**
     * 获取手机号
     */
    public static String getPhoneNumber(Context context) {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                return "";
//            }
//        }
//        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        return tm.getLine1Number();
        return "";
    }

    /**
     * 获取手机号
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getNetworkOperatorName();
    }

    /**
     * 获取手机品牌
     */
    public static String getPhoneBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机Android API等级（22、23 ...）
     */
    public static int getBuildLevel() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机Android 版本（4.4、5.0、5.1 ...）
     */
    public static String getBuildVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取当前App进程的id
     */
    public static int getAppProcessId() {
        return android.os.Process.myPid();
    }

    /**
     * 获取AndroidManifest.xml里 的值
     */
    public static String getMetaData(Context context, String name) {
        String value = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static boolean isRunningForeground(Context context) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ComponentName cn = am.getRunningTasks(1)
                    .get(0).topActivity;
            String currentPackageName = cn.getPackageName();
            if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }

    }
}
