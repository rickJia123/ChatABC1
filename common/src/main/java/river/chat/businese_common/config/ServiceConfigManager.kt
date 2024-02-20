package river.chat.businese_common.config

import river.chat.businese_common.constants.CommonConstants
import river.chat.businese_common.dataBase.AppUpdateConfigBox
import river.chat.businese_common.net.CommonRequestViewModel
import river.chat.lib_core.app.BaseApplication
import river.chat.lib_core.config.AppServerConfigKey
import river.chat.lib_core.config.ServiceConfigBox
import river.chat.lib_core.utils.exts.view.preLoad

/**
 * Created by beiyongChao on 2023/6/9
 * Description:服务端配置管理器
 */
object ServiceConfigManager {


    fun loadAllConfig() {
        loadConfig()
        loadUpdateConfig()
    }


    /**
     * 加载配置信息
     */
    fun loadConfig() {
        CommonRequestViewModel().requestConfig(AppServerConfigKey.REQUEST_PRIVACY_VERSION) {
            if (it.isSuccess) {
                it.data?.let {
                    ServiceConfigBox.updateConfig(it.apply {
                        //rick todo
                        this.activityTitle = "春节特惠"
                        this.activityEndTime = System.currentTimeMillis() + 10 * 60 * 1000
                    })
                    preLoad(BaseApplication.getInstance(), it.appShareBg)
                }

            }
        }
    }

    /**
     *  获取版本更新配置信息
     */
    private fun loadUpdateConfig() {
        CommonRequestViewModel().requestAppUpdateConfig() {
            if (it.isSuccess) {
                it.data?.let {
                    AppUpdateConfigBox.updateConfig(it.apply {
                    })
                }
            }
        }
    }


    /**
     * 获取关闭的模块字符串
     */
    fun getCloseModulesStr(closeModules: MutableList<String>): String {
        val sb = StringBuilder()
        for (i in closeModules.indices) {
            sb.append(closeModules[i])
            if (i != closeModules.size - 1) {
                sb.append(",")
            }
        }
        return sb.toString()
    }

    //是否需要隐藏会员入口
    fun isNeedHideVip(): Boolean {
//        return ServiceConfigBox.getConfig().closeModulesStr?.contains(CommonConstants.CLOSE_MODULE_VIP_ENTRANCE) == true
        //rick todo test
        return false
    }

    //是否需要隐藏分享入口
    fun isNeedHideShare(): Boolean {
        return ServiceConfigBox.getConfig().closeModulesStr?.contains(CommonConstants.CLOSE_MODULE_SHARE_ENTRANCE) == true
    }

    //是否需要隐藏兑换入口
    fun isNeedHideExchange(): Boolean {
//        return ServiceConfigBox.getConfig().closeModulesStr?.contains(CommonConstants.CLOSE_MODULE_EXCHANGE_ENTRANCE)==true


        //rick todo test
        return false

    }


}