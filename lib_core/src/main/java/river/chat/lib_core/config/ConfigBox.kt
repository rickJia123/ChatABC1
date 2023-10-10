package river.chat.lib_core.config

import io.objectbox.kotlin.equal
import io.objectbox.kotlin.query
import river.chat.lib_core.storage.database.BaseBox
import river.chat.lib_resource.model.database.AppConfigBean
import river.chat.lib_resource.model.database.AppConfigBean_

/**
 * Created by beiyongChao on 2023/5/6
 * Description:
 */
object ConfigBox : BaseBox<AppConfigBean>() {

    override fun getEntityClass() = AppConfigBean::class.java

    override fun exit() {

    }


    fun getConfigValue(key: String, default: String): String {
        return all.firstOrNull() { it.key == key }?.value ?: default
    }

    fun getConfig(key: String): AppConfigBean? {
        return  all.firstOrNull() { it.key == key }
    }

    fun putConfig(key: String, value: String): Long {

        var config: AppConfigBean? = all.firstOrNull() { it.key == key }?: AppConfigBean(
            key = key,
            value = value,
            updateTime = System.currentTimeMillis()
        )

        config?.let {
            it.value = value
            it.updateTime = System.currentTimeMillis()
            return add(
                it
            )?:0
        }
        return 0
    }


}



