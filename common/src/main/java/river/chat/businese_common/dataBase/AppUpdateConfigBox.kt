package river.chat.businese_common.dataBase

import river.chat.lib_core.storage.database.BaseBox
import river.chat.lib_core.utils.exts.ifEmptyOrBlank
import river.chat.lib_resource.model.database.AppUpdateConfigResBean

/**
 * Created by beiyongChao on 2024/2/6
 * Description:
 */
object AppUpdateConfigBox : BaseBox<AppUpdateConfigResBean>() {

    override fun getEntityClass() = AppUpdateConfigResBean::class.java

    override fun exit() {

    }

    fun getConfig(): AppUpdateConfigResBean {
        return all?.firstOrNull() ?: AppUpdateConfigResBean()
    }

    fun updateConfig(config: AppUpdateConfigResBean): Long {
        var localConfig = all?.firstOrNull() ?: AppUpdateConfigResBean(
            updateTime = System.currentTimeMillis()
        )
        localConfig.apply {
            this.appUrl = config.appUrl.ifEmptyOrBlank("")
            this.appVersion = config.appVersion.ifEmptyOrBlank("")
            this.content = config.content.ifEmptyOrBlank("")
            this.isForce = config.isForce
            this.isRenew = config.isRenew
            this.updateTime = System.currentTimeMillis()
        }

        localConfig.let {
            return AppUpdateConfigBox.add(
                it
            ) ?: 0
        }
        return 0
    }


}


