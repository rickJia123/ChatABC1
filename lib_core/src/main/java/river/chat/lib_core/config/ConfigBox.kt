package river.chat.lib_core.config

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
        var list = box.all
        var item = list.find { it.key == key }
        return item?.value ?: default
    }

    fun getConfig(key: String): AppConfigBean? {
        var list = box.all
        var item = list.find { it.key == key }
        return item
    }

    fun putConfig(key: String, value: String): Long {
        var list = box.all
        var item = list.find { it.key == key } ?: AppConfigBean(
            key = key,
            value = value,
            updateTime = System.currentTimeMillis()
        )
        item.value = value
        return box?.put(
            item
        ) ?: 0
    }


}



