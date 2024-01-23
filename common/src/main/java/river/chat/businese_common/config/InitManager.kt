package river.chat.businese_common.config

import android.app.Application
import androidx.annotation.Nullable
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.tauth.Tencent
import com.umeng.commonsdk.UMConfigure
import com.xingin.xhssharesdk.callback.XhsShareRegisterCallback
import com.xingin.xhssharesdk.core.XhsShareSdk
import com.xingin.xhssharesdk.model.config.XhsShareGlobalConfig
import com.yl.lib.sentry.hook.PrivacySentry
import com.yl.lib.sentry.hook.PrivacySentryBuilder
import kotlinx.coroutines.GlobalScope
import river.chat.businese_common.report.UMTrackHandler
import river.chat.businese_common.utils.CrashHandler
import river.chat.lib_core.wx.WxManager
import river.chat.lib_core.storage.file.StorageUtil
import river.chat.lib_core.tracker.initTracker
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.utils.longan.application
import river.chat.lib_core.utils.longan.deviceOaid
import river.chat.lib_core.utils.longan.isAppDebug
import river.chat.lib_core.utils.longan.log
import river.chat.lib_core.utils.longan.workThread
import river.chat.lib_resource.AccountsConstants
import river.chat.lib_umeng.common.UmInitConfig


/**
 * Created by beiyongChao on 2023/6/7
 * Description:
 */
object InitManager {

    fun initSdk(application: Application) {
        initRouter()
        workThread(
            scope = GlobalScope,
            block = {
                initXhs(application)
                initUmeng(application)
                initPrivacy()
                WxManager.regToWx(application)
                initBusiness(application)
            },
            result = {
                if (it) {
                    "initSdk success".log()
                } else {
                    "initSdk failed".log()
                }
            }
        )

    }

    fun initBusiness(application: Application) {
        initConfig(application)
        application.initTracker(UMTrackHandler())
        if (!isAppDebug) {
            CrashHandler.getInstance().init(application)
        }

    }


    private fun initConfig(application: Application) {
        LogUtil.init(isAppDebug)
        StorageUtil.init(application)
    }

    private fun initRouter() {
        if (isAppDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug();  // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application) // 尽可能早，推荐在Application中初始化
    }

    private fun initUmeng(application: Application) {

        //设置LOG开关，默认为false
        if (isAppDebug) {
            UMConfigure.setLogEnabled(true)
        }


        //友盟预初始化
        UMConfigure.preInit(application, AccountsConstants.UMENG_KEY, "Umeng")


        /**
         * 打开app首次隐私协议授权，以及sdk初始化，判断逻辑请查看SplashTestActivity
         */
        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
        //友盟正式初始化
        val umInitConfig = UmInitConfig()
        umInitConfig.UMinit(application)
        //QQ官方sdk授权
        Tencent.setIsPermissionGranted(true)
        UMConfigure.getOaid(
            application
        ) { oaid ->
            deviceOaid = oaid ?: ""
            "initUmeng oaid".log()
        }


    }


    /**
     * 小红书分享
     */
    private fun initXhs(application: Application) {
        XhsShareSdk.registerApp(application, AccountsConstants.XHS_APP_KEY,
            XhsShareGlobalConfig()
                .setEnableLog(isAppDebug)
                .setFileProviderAuthority("river.chat.chatevery.fileprovider")
                .setClearCacheWhenShareComplete(true),
            object : XhsShareRegisterCallback {
                override fun onSuccess() {

                }

                override fun onError(
                    errorCode: Int,
                    errorMessage: String,
                    @Nullable exception: Exception?
                ) {


                }
            })
    }


    /**
     * 初始化隐私框架
     */
    fun initPrivacy() {
        // 完成功能的初始化
        var builder = PrivacySentryBuilder()
            // 自定义文件结果的输出名
            .configResultFileName("buyer_privacy")
            // 配置游客模式，true打开游客模式，false关闭游客模式
            .configVisitorModel(false)
            // 配置写入文件日志 , 线上包这个开关不要打开！！！！，true打开文件输入，false关闭文件输入
            .enableFileResult(isAppDebug)
            // 持续写入文件30分钟
            .configWatchTime(30 * 60 * 1000)
        // 文件输出后的回调
//            .configResultCallBack(object : PrivacySentryBuilder.ResultCallBack {
//                override fun onResult(result: String) {
//                    // 这里可以拿到文件输出的结果
//                }
//            }
        // 添加默认结果输出，包含log输出和文件输出
        PrivacySentry.Privacy.init(application, builder)
    }
}