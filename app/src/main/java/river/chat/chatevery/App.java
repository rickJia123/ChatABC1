package river.chat.chatevery;

import river.chat.lib_core.app.AppManager;
import river.chat.lib_core.app.BaseApplication;
import river.chat.lib_core.storage.StorageUtil;

public class App extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        initConfig();
    }

    private void initConfig() {
        StorageUtil.init(this);

//        ApiConfig.init(BuildConfig.HOST_TYPE);
//
//        BoxManager.getInstance()
//                .init();
//
//        LogUtil.init(BuildConfig.DEBUG);
//
//        CrashHandler.getInstance()
//                .init();

//        if (BuildConfig.DEBUG) {
//            LeakCanary.install(this);
//        }

//        initBugly();

    }

    /**
     * bugly初始化
     */
//    private void initBugly() {
//        String packageName = getPackageName();
//        String processName = AppUtil.getProcessName(android.os.Process.myPid());
//        // 设置是否为上报进程
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
//        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        strategy.setAppChannel(Config.getChannel());
//        // 初始化Bugly
//        CrashReport.initCrashReport(this, BuildConfig.BUGLY_APPID, BuildConfig.DEBUG, strategy);
//    }

    @Override
    public void reloadApp() {
        super.reloadApp();
//        try {
//            LogUtil.save("重新启动app");
//            if (AppManager.getInstance()
//                    .getCurrentActivity() instanceof SplashActivity) {
//                LogUtil.save("当前正在登录界面");
//                return;
//            }
//            AppManager.getInstance()
//                    .finishAllActivity();
//            Intent intent = new Intent(this, SplashActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void tokenExpire(int code, String msg) {
//        try {
//            LogUtil.save("重新登录：" + code + " " + msg);
//            RongPushClient.clearAllNotifications(this);
//            IMManager.getInstance()
//                    .logout();
//            Cache.setUserInfo(null);
//            if (AppManager.getInstance()
//                    .getCurrentActivity() instanceof LoginActivity) {
//                LogUtil.save("当前正在登录界面");
//                return;
//            }
//            super.tokenExpire(code, msg);
//            Intent intent = new Intent(this, LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra(LoginActivity.PARAM_MSG, msg);
//            startActivity(intent);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    /**
     * 退出程序
     */
    @Override
    public void exitApp() {
        try {
            AppManager.getInstance()
                    .finishAllActivity();
            System.exit(0);
        } catch (Exception e) {

        }
        super.exitApp();
    }
}
