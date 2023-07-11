package river.chat.businese_common.constants

import river.chat.businese_common.config.AppConfigKey

/**
 * Created by beiyongChao on 2023/3/6
 * Description: app内动态存取的配置
 */
class CommonAppConfig : AppConfigKey() {
    companion object {
        /**
         * 是否同意隐私协议
         */
        const val PRIVACY_AGREE = "PRIVACY_AGREE"
    }

}