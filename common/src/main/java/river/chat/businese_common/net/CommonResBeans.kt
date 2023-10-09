package river.chat.businese_common.net


/**
 * 接口获取的配置信息
 */
data class ConfigResBean(
    var value: String = "",
) : java.io.Serializable

/**
 * 接口获取的默认配置信息
 */
data class  DefaultConfigResBean(
    var appUrl: String = "",
    var appVersion: String = "",
    var content: String = "",
    var isForce: Int = 0,
    var isRenew: Int = 0,
) : java.io.Serializable