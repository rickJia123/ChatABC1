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


    fun getConfig(key: String,default:String): String {
        return box.query {
            AppConfigBean_.key.equal(key)
        }.findFirst()?.value ?: default
    }

    fun putConfig(key: String, value: String): Long {
        return box?.put(AppConfigBean(key = key, value = value, updateTime = System.currentTimeMillis())) ?: 0
    }


}



