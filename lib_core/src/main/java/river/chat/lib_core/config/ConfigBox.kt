package river.chat.lib_core.config

import io.objectbox.kotlin.query
import river.chat.lib_core.storage.database.BaseBox
import river.chat.lib_resource.model.AppConfigBean
import river.chat.lib_resource.model.AppConfigBean_

/**
 * Created by beiyongChao on 2023/5/6
 * Description:
 */
object ConfigBox : BaseBox<AppConfigBean>() {

    override fun getEntityClass() = AppConfigBean::class.java

    override fun exit() {

    }


    fun getConfigValue(key: String, default: String): String {
        return box.query {
            AppConfigBean_.key.equal(key)
        }.findFirst()?.value ?: default
    }

    fun getConfig(key: String): AppConfigBean? {
        return box.query {
            AppConfigBean_.key.equal(key)
        }.findFirst()
    }

    fun putConfig(key: String, value: String): Long {
        box.query {
            AppConfigBean_.key.equal(key)
        }.findFirst()?.let { box.remove(it) }
        return box?.put(
            AppConfigBean(
                key = key,
                value = value,
                updateTime = System.currentTimeMillis()
            )
        ) ?: 0
    }


}



