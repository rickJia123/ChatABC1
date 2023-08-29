package river.chat.lib_core.utils.log;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import river.chat.lib_core.businese.BusineseDateUtils;
import river.chat.lib_core.utils.longan.ApplicationKt;

/**
 * Created by Circle on 2019-06-28
 */
public class LogUtil extends LogShow {
    private static final int DEBUG = 111;
    private static final int ERROR = 112;
    private volatile static boolean mDebug = false;//debug模式  true:在logcat显示
    private volatile static boolean mWriteFile = true;//保存日志
    private static String pkgName = "";
    public static String logcat = "xxxxx";
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH-mm:ss");
    private static String mUserId = "0";
    private static final Executor logExecutor = Executors.newSingleThreadExecutor();

    public static void init(boolean debug) {
        mDebug = debug;
        init(debug, false);
    }

    /**
     * 设置用户id
     */
    public static void setUserId(String uid) {
        mUserId = uid;
    }

    public static void d(String msg) {
        logShow(msg, DEBUG, false);
    }

    public static void e(String msg) {
        logShow(msg, ERROR, false);
    }

    public static void e(Throwable e) {
        e(e, mDebug);
    }

    public static void save(Throwable e) {
        e(e, mWriteFile);
    }

    public static void save(String msg) {
        logShow(msg, DEBUG, mWriteFile);
    }

    private static void e(Throwable e, boolean isWriteFile) {
        if (!mDebug && !isWriteFile) {
            return;
        }
        StringBuffer exceptionStr = new StringBuffer();
        StackTraceElement[] elements = e.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            exceptionStr.append(elements[i].toString() + "\r\n");
        }
        logShow(exceptionStr.toString(), ERROR, isWriteFile);
    }

    private static void logShow(String msg, int style, boolean isWriteFile) {
        if (!mDebug && !isWriteFile) {
            return;
        }
        String name = getFunctionName();
        String Message = (name == null ? msg : (msg));
        String tag = pkgName;
        switch (style) {
            case DEBUG:
                if (isWriteFile) {
                    writeFileSdcard(name + " - " + Message);
                }
                if (mDebug) {
                    Log.d(tag, logcat + " - " + Message);
                }
                break;
            case ERROR:
                if (isWriteFile) {
                    writeFileSdcard(name + " - " + Message);
                }
                if (mDebug) {
                    Log.e(tag, logcat + " - " + Message);
                }
                break;
        }

    }

    private static String getFunctionName() {
        StackTraceElement[] sts = Thread.currentThread()
                .getStackTrace();
        if (sts == null) {
            return null;
        }

        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }

            if (st.getClassName()
                    .equals(Thread.class.getName())) {
                continue;
            }

            if (st.getClassName() != null && st.getClassName()
                    .contains("LogUtil")) {
                continue;
            }

            getPkgName(st.getClassName());
            String filename = getTagName(st.getClassName());

            logcat = "[" + Thread.currentThread()
                    .getName() + filename + ":" + st.getLineNumber() + "]";
            return "[" + simpleDateFormat.format(new Date()) + " " + Thread.currentThread()
                    .getName() + "(" + Thread.currentThread()
                    .getId() + "): " + filename + ":" + st.getLineNumber() + "]";
        }
        return null;
    }

    private static String getPkgName(String name) {
        // "com.xingxiu.kela.xxx.xx";
        if (pkgName.equals("") || pkgName.equals("default")) {
            String tmp = new String(name);
            String[] arrar = tmp.split("\\.");
            if (arrar != null && arrar[0] != null && arrar[1] != null && arrar[2] != null) {
                pkgName = arrar[0] + "." + arrar[1] + "." + arrar[2] + "";
            } else {
                pkgName = "default";
            }
        }
        return pkgName;
    }

    private static String getTagName(String name) {
        String reatmp = "";
        if (!name.equals("")) {
            String tmp = new String(name);
            int i;
            String[] arrar = tmp.split("\\.");
            for (i = 3; arrar != null && i < arrar.length; i++) {
                reatmp = reatmp + "." + arrar[i];
            }
        }
        if (reatmp.equals("")) {
            reatmp = "default";
        }
        return reatmp;
    }

    private static File getLogFileDir() {
        try {
            if (!Environment.getExternalStorageState()
                    .equals(Environment.MEDIA_MOUNTED)) {
                return null;
            }

            File fileDir = new File(Environment.getExternalStorageDirectory(), "kela/log");
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            return fileDir;
        } catch (Throwable e) {
            return null;
        }

    }

    private static File mLogFile;

    private static void writeFileSdcard(final String message) {
        if (isUpload) {
            return;
        }
        logExecutor.execute(() -> {
            try {
                if (mLogFile == null) {
                    mLogFile = getLogFileDir();
                }

                FileOutputStream fout = new FileOutputStream(mLogFile.getPath() + File.separator + getLogFileName(),
                                                             true);
                fout.write((message + "\r\n").getBytes("UTF-8"));
                fout.flush();
                fout.close();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        });

    }

    private static String verionName = "";


    public static String getLogFileName() {
        if (TextUtils.isEmpty(verionName)) {
            verionName = ApplicationKt.getAppVersionName();
        }
        return mUserId + "_android_" + verionName + "_" + BusineseDateUtils.INSTANCE.getYearDate(System.currentTimeMillis()) + ".log";
    }

    private static boolean isUpload = false;

    /**
     * @param isForce true强制上传
     */
    public static void upload(boolean isForce) {
//        if (isForce || !DateUtil.isSameDay(SpCache.getUploadLogTime())) {
//            if (isUpload) return;
//            RxUtil.doInIOThreadDelay(new RxIOTask<String>("") {
//                @Override
//                public Void doInIOThread(String s) {
//                    isUpload = true;
//                    LogUtil.save("开始上报日志=" + isForce);
//                    getToken(isForce);
//                    return null;
//                }
//            }, 2);
//            return;
//        }

    }
//
//
//    private static void getToken(boolean isForce) {
//        Api.getApi().getQiniuToken("log")
//                .compose(RxHelper.rxSchedulerHelperIO())
//                .subscribe(new BaseObserver<BaseResult<QNTokenModel>>() {
//                    @Override
//                    public void onSuccess(BaseResult<QNTokenModel> baseResult) {
//                        String token = baseResult.getData().getQinToken();
//                        isUpload = false;
//                        if (TextUtils.isEmpty(token)) {
//                            return;
//                        }
//                        RxUtil.doInIOThread(new RxIOTask<String>("") {
//                            @Override
//                            public Void doInIOThread(String s) {
//                                prepareLogFile(isForce, token);
//                                uploadRongLog(token);
//                                return null;
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onFail(int code, String message) {
//                        isUpload = false;
//                    }
//                });
//    }

    private static void prepareLogFile(boolean isForce, String token) {
        try {
            File fileDir = getLogFileDir();
            if (fileDir == null) {
                return;
            }
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            } else {
                FileFilter filter = pathname -> {
                    String date =
                            BusineseDateUtils.INSTANCE.getYearDate(System.currentTimeMillis());
                    String fileName = pathname.getName();
                    return fileName.endsWith(".log") && !fileName.contains(date);
                };
                File[] list = fileDir.listFiles(filter);
                //rick todo
//                for (File file : list) {
//                    if (!file.getName()
//                            .startsWith("log_")) {
//                        file = FileUtil.renameFile(file, "log_" + file.getName());
//                    }
//                    upload(isForce, file, token);
//                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始上传日志
     */
    private static void upload(boolean isForce, File file, String token) {
//        UploadManager uploadManager = new UploadManager();
//        String fileName = file.getName();
//        if (fileName.startsWith("log_0_")) {
//            fileName = fileName.replaceFirst("log_0", "log_" + mUserId + "_1");
//        }
//        String key = fileName;
//        if (isForce) {
//            key = "server_" + fileName.substring(0, fileName.indexOf(".log")) + System.currentTimeMillis() + ".log";
//        }
//        uploadManager.put(file.getAbsolutePath(), key, token, (key1, info, response) -> {
//            try {
//                if (info.isOK()) {
//                    SpCache.setUploadLogTime();
//                    file.delete();
//                    LogUtil.save("日志上传成功，并删除本地日志");
//                } else {
//                    LogUtil.save("日志上传失败==" + info.error);
//                }
//            } catch (Exception e) {
//
//            }
//        }, new UploadOptions(null, "test-type", true, null, null));
    }

    /**
     * 上报融云日志
     */
    private static void uploadRongLog(String token) {
//        try {
//            File file = new File(Environment.getExternalStorageDirectory(), "android/data/com.xingxuan.kela/files/rong_log/rong_sdk.log");
//            if (file.exists()) {
//                UploadManager uploadManager = new UploadManager();
//                String key = "log_rong_" + mUserId + "_android_" + System.currentTimeMillis() + ".log";
//
//                uploadManager.put(file.getAbsolutePath(), key, token, new UpCompletionHandler() {
//                    @Override
//                    public void complete(String key, ResponseInfo info, JSONObject response) {
//                    }
//                }, new UploadOptions(null, "test-type", true, null, null));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
