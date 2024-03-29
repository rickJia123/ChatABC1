package river.chat.lib_core.config

/**
 * Created by beiyongChao on 2023/3/6
 * Description: app内部自己使用的configKey
 */
object AppLocalConfigKey {
    const val PLATFORM="ANDROID"

    /**
     * 是否同意隐私协议
     */
    const val PRIVACY_AGREE = "PRIVACY_AGREE"

    /**
     * 上次提示更新的时间
     */
    const val UPDATE_DIALOG_SHOW_TIME = "UPDATE_DIALOG_SHOW_TIME"

    /**
     * 上次提示没有聊天权限时间
     */
    const val UPDATE_CHAT_LIMIT_TIME = "UPDATE_CHAT_LIMIT_TIME"

    /**
     * 上次活动弹窗更新的时间
     */
    const val ACTIVITY_DIALOG_SHOW_TIME = "UPDATE_DIALOG_SHOW_TIME"
    /**
     * 上次活动弹窗内容
     */
    const val ACTIVITY_CONTENT = "ACTIVITY_CONTENT"

    /**
     * 是否点击过活动弹窗
     */
    const val ACTIVITY_HAS_CLICK = "ACTIVITY_HAS_CLICK"
    /**
     * 设备id
     */
    const val DEVICE_ID = "DEVICE_ID"

    /**
     * 设备
     */
    const val DEVICE_MODE = "DEVICE_MODE"
}


/**
 * 服务端返回的配置
 */
object AppServerConfigKey {
    const val SUPPLY_PLATFORM="ANDROID"


    /**
     * 隐私协议版本号
     */
    const val REQUEST_PRIVACY_VERSION = "REQUEST_PRIVACY_VERSION"

    /**
     * 用户更新类型：0不更新 1建议更新 2强制更新
     */
//    const val REQUEST_APP_UPDATE_TYPE = "REQUEST_APP_UPDATE_TYPE"



    /**
     * 用户版本更新下载内容
     */
//    const val REQUEST_APP_UPDATE_CONTENT = "REQUEST_APP_UPDATE_CONTENT"

}