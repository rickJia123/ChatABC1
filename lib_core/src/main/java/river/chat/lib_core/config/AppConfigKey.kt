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
    const val REQUEST_APP_UPDATE_TYPE = "REQUEST_APP_UPDATE_TYPE"

    /**
     * 用户版本更新下载链接
     */
    const val REQUEST_APP_UPDATE_URL = "REQUEST_APP_UPDATE_URL"

    /**
     * 用户版本更新下载内容
     */
    const val REQUEST_APP_UPDATE_CONTENT = "REQUEST_APP_UPDATE_CONTENT"

}