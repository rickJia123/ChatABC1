package river.chat.businese_common.config

import android.app.Application
import com.tencent.tauth.Tencent
import com.umeng.commonsdk.UMConfigure
import river.chat.lib_umeng.common.UmInitConfig

/**
 * Created by beiyongChao on 2023/6/7
 * Description:
 */
object InitManager {

    fun initSdk(application: Application) {
        initUmeng(application)
    }

    private fun initUmeng(application: Application) {

        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true)

        //友盟预初始化
        UMConfigure.preInit(application, "59892f08310c9307b60023d0", "Umeng")


        /**
         * 打开app首次隐私协议授权，以及sdk初始化，判断逻辑请查看SplashTestActivity
         */
        //判断是否同意隐私协议，uminit为1时为已经同意，直接初始化umsdk
        //友盟正式初始化
        val umInitConfig = UmInitConfig()
        umInitConfig.UMinit(application)
        //QQ官方sdk授权
        Tencent.setIsPermissionGranted(true)

    }
}