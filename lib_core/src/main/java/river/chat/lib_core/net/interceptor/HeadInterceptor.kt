package river.chat.lib_core.net.interceptor


import okhttp3.Interceptor
import okhttp3.Request
import river.chat.lib_core.config.AppSystemConfigKey
import river.chat.lib_core.router.plugin.core.getPlugin
import river.chat.lib_core.router.plugin.module.UserPlugin
import river.chat.lib_core.utils.longan.appVersionName

/**
 * Created by beiyongChao on 2023/4/27
 * Description: 请求头 拦截器
 */


class HeadInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val token: String = getPlugin<UserPlugin>().getUser()
            .token
        val request: Request = chain.request()
            .newBuilder() //                        .header("Connection", "close")
            .header("header_token", token)
            .header("header_lng", "")
            .header("header_lat", "")
            .header("header_source", AppSystemConfigKey.PLATFORM + "")
            .header("header_version", appVersionName)
            .build()

        return chain.proceed(request)
    }
}