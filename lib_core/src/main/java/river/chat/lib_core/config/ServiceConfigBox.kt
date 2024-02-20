package river.chat.lib_core.config

import river.chat.lib_core.storage.database.BaseBox
import river.chat.lib_resource.model.database.ServiceConfigBean

/**
 * Created by beiyongChao on 2023/10/16
 * Description:
 */
object ServiceConfigBox : BaseBox<ServiceConfigBean>() {

    override fun getEntityClass() = ServiceConfigBean::class.java

    override fun exit() {

    }

    fun getConfig(): ServiceConfigBean {
        return all?.firstOrNull() ?: ServiceConfigBean()
    }

    fun updateConfig(config: ServiceConfigBean): Long {
//        return add(config) ?: 0
        var localConfig = all?.firstOrNull() ?: ServiceConfigBean(
            updateTime = System.currentTimeMillis()
        )
        localConfig.apply {
            this.appDownUrl = config.appDownUrl
            this.appPolicyUrl = config.appPolicyUrl
            this.appPrivacyPolicy = config.appPrivacyPolicy
            this.appShareBg = config.appShareBg
            this.appDownLink = config.appDownLink
            this.closeModulesStr = config.closeModulesStr
            this.updateTime = System.currentTimeMillis()
            this.activityTitle = config.activityTitle
            this.activityEndTime = config.activityEndTime
        }

        localConfig.let {
            return add(
                it
            ) ?: 0
        }
        return 0
    }


}


