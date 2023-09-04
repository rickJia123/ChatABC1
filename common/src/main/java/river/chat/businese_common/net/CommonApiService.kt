package river.chat.businese_common.net

import river.chat.lib_core.wx.WxAccessTokenBean
import river.chat.lib_core.net.bean.BaseRequestBean
import river.chat.lib_core.net.retrofit.BaseApiService
import river.chat.lib_resource.model.CreateOrderRequestBean
import river.chat.lib_resource.model.CreateOrderResBean
import river.chat.lib_resource.model.QueryOrderRequestBean
import river.chat.lib_resource.model.QueryOrderResBean

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

    /**
     * 创建订单
     */
    suspend fun createPayOrder(payRequestBean: CreateOrderRequestBean): BaseRequestBean<CreateOrderResBean> =
        commonApi.createPayOrder(
            ApiStorage.getBasedBody().apply {
                this["payType"] = payRequestBean.payType
                this["price"] = payRequestBean.price
                this["skuId"] = payRequestBean.skuId
            }
        )

    /**
     * 查询订单状态
     */
    suspend fun queryOrder(queryOrderRequestBean: QueryOrderRequestBean): BaseRequestBean<QueryOrderResBean> =
        commonApi.queryOrder(
            ApiStorage.getBasedBody().apply {
                this["id"] = queryOrderRequestBean.id
            }
        )


}