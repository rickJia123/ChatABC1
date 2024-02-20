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
        var endTime = ServiceConfigBox.getConfig().activityEndTime
        var activityTitle = ServiceConfigBox.getConfig().activityTitle

        //rick todo
        if (endTime == 0L || endTime < System.currentTimeMillis()) {
            return false
        }
        return true

    }

}