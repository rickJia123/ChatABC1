package river.chat.lib_core.router.plugin.module

import com.alibaba.android.arouter.facade.template.IProvider
import river.chat.lib_core.event.BaseConstants
import river.chat.lib_resource.model.database.MessageBean

/**
 * Created by beiyongChao on 2023/3/7
 * Description:
 */

object HomeRouterConstants : BaseConstants() {
    private const val GROUP_HOME = "/home"
    private const val GROUP_COMMON = "/common"
    const val HOME_PLUGIN = GROUP_HOME + "/plugin"

    /**
     * 首页
     */
    const val HOME_MAIN = GROUP_HOME + "/main"


    /**
     * 设置页
     */
    const val HOME_SETTINGS = GROUP_HOME + "/settings"

    /**
     * app分享页
     */
    const val SHARE_APP = GROUP_HOME + "/shareApp"

    /**
     * 意见反馈页
     */
    const val HOME_FEEDBACK = GROUP_HOME + "/feedback"

    /**
     * vip 开通页
     */
    const val VIP_OPEN = GROUP_HOME + "/vipOpen"

    const val VIP_EXCHANGE = GROUP_HOME + "/vipExchange"

    /**
     * 支付结果页
     */
    const val VIP_PAY_RESULT = GROUP_COMMON + "/vipResult"

    /**
     * 收藏页
     */
    const val HOME_COLLECTION = GROUP_HOME + "/collection"

    /**
     * 收藏页详情
     */
    const val HOME_COLLECTION_DETAIL = GROUP_HOME + "/collectionDetail"

    /**
     * 意见反馈页
     */
    const val HOME_PICTURE_PREVIEW = GROUP_HOME + "/picturePreviewActivity"


    object Params {
        const val KEY_QUESTION_ID = "KEY_QUESTION_ID"

        //需要预览的图片id
        const val PICTURE_PRELOAD_ID = "PICTURE_PRELOAD_ID"
    }
}


interface HomePlugin : IProvider {

    fun test()
    fun onMsgReceive(msg: MessageBean)
    fun onComplete()

}