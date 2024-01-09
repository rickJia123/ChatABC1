package river.chat.businese_common.net

import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin

/**
 * Created by beiyongChao on 2023/2/24
 * Description:
 */
object ApiStorage {



    /**
     * 添加appId和token
     */
    fun getBasedBody(): MutableMap<String, Any> {
        val body: MutableMap<String, Any> = HashMap()
        val token: String = getPlugin<UserPlugin>().getUser()
            .token
        if (!token.isNullOrEmpty()) {
            body["header_token"] = token
        }
//        body["header_lng"] = ""
//        body["header_lat"] = ""
//        body["header_source"] = AppConfig.PLATFORM
//        body["header_version"] =DeviceUtil.getVersionName(BaseApplication.getInstance())
        return body
    }

}