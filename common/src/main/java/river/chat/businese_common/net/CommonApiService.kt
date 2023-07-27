package river.chat.businese_common.net

import river.chat.businese_common.wx.WxAccessTokenBean
import river.chat.lib_core.net.bean.BaseRequestBean
import river.chat.lib_core.net.retrofit.BaseApiService
import river.chat.lib_core.storage.database.model.MessageBean

/**
 * Created by beiyongChao on 2023/2/24
 * Description:
 */
object CommonApiService  : BaseApiService() {

    /**
     * 获取主业务网络仓库
     */
    private val commonApi: CommonApi
        get() = retrofit().create(CommonApi::class.java)



    /**
     * 获取配置信息
     */
    suspend fun requestConfig(
        key: String
    ): BaseRequestBean<ConfigResBean> =
        commonApi.requestConfig(
            ApiStorage.getBasedBody().apply {
                this["key"] = key
            }
        )


    /**
     * 获取配置信息
     */
    suspend fun requestDefaultConfig(
    ): BaseRequestBean<DefaultConfigResBean> =
        commonApi.requestDefaultConfig(
            ApiStorage.getBasedBody()
        )


    /**
     * 获取微信登录信息
     */
    suspend fun requestWechatAccessToken(
        appid: String,
        secret: String,
        code: String,
    ): WxAccessTokenBean =
        commonApi.requestWechatAccessToken(
            appid,
            secret,
            code
        )

}