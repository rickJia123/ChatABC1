package river.chat.businese_main.manager


import river.chat.lib_core.config.ServiceConfigBox
import river.chat.lib_resource.model.VipRightsBean
import river.chat.lib_resource.model.VipSkuBean

/**
 * Created by beiyongChao on 2024/3/12
 * Description:
 */
object MainCommonHelper {

    /**
     * 活动弹窗最少间隔时间
     */
    const val UPDATE_SHOW_LIMIT_TIME = 24 * 60 * 60 * 1000L

    /**
     * 支付列表
     */
    var mSkuPayList: MutableList<VipSkuBean> ?= mutableListOf()

    /**
     * vip权益
     */
    var mVipRightsBean: VipRightsBean? = null



    /**
     * 检测是否需要展示活动
     */
    fun checkNeedPayActivity(): Boolean {
//        var startTime = ServiceConfigBox.getConfig().activityStartTime
//        var endTime = ServiceConfigBox.getConfig().activityEndTime
        var activityTitle = ServiceConfigBox.getConfig().activityTitle
        if (activityTitle.isNullOrEmpty()) {
            return false
        }
        return true

    }

    private fun checkNeedRefreshLocalActivity()
    {
        var activityTitle = ServiceConfigBox.getConfig().activityTitle


    }

}