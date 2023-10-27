package river.chat.businese_common.net




/**
 * 接口获取的默认配置信息
 */
data class  AppUpdateConfigResBean(
    var appUrl: String = "",
    var appVersion: String = "",
    var content: String = "",
    var isForce: Int = 0,
    var isRenew: Int = 0,
) : java.io.Serializable