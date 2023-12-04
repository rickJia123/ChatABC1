package river.chat.lib_core.net.bean


data class BaseRequestBean<T>
    (
    var data: T, var status: String, var msg: String, var page: PageModel
) {
    /**
     * 请求是否成功
     *
     *
     * @return
     */
    fun isSucc(): Boolean {
        return status.equals("0000")
    }

    /**
     * 是否需要重新登录
     * 208 该设备已被关进小黑屋
     * 234 accessToken验证失败
     * 235 长时间未登陆，请重新登录
     * 236 在其他设备登录，请重新登录
     *
     * @return
     */
    fun isTokenExpired(): Boolean {
        return status.equals("1111")
    }
}