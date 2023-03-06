package river.chat.lib_core.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;


public class BaseApplication extends Application {
    private static BaseApplication baseApplication;
    private ActivityLifecycleCallbacks mActivityLifecycleCallbacks;
    private AppLifeCycleImpl mAppLifeCycle;

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        registerActivityListener();

        mAppLifeCycle.onCreate(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mAppLifeCycle=new AppLifeCycleImpl(this);
    }

    public static BaseApplication getInstance() {
        return baseApplication;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mAppLifeCycle.onTerminate(this);
    }

    public void reloadApp(){

    }

    /**
     * token过期
     *
     * @param code
     * @param msg
     */
    public void tokenExpire(int code, String msg) {
        AppManager.getInstance().finishAllActivity();
    }

    /**
     * 退出程序
     */
    public void exitApp() {
        if (mActivityLifecycleCallbacks != null) {
            unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
        }
    }


    private void registerActivityListener() {
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//                AppManager.getInstance().addActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
//                AppManager.getInstance().removeActivity(activity);
            }
        });
    }




}
