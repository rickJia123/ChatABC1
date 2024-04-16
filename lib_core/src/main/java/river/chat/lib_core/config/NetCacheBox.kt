package river.chat.lib_core.config

import river.chat.lib_core.storage.database.BaseBox
import river.chat.lib_core.utils.common.GsonKits
import river.chat.lib_resource.model.database.NetCacheBean

/**
 * Created by beiyongChao on 2023/5/6
 * Description:
 */
object NetCacheBox : BaseBox<NetCacheBean>() {

    override fun getEntityClass() = NetCacheBean::class.java

    override fun exit() {

    }



    fun getCache(key: String): NetCacheBean? {
        return all?.firstOrNull() { it.key == key }
    }

    fun putCache(key: String, value: String): Long {
        var cache: NetCacheBean? = all?.firstOrNull() { it.key == key }?: NetCacheBean(
            key = key,
            data = value,
            date = System.currentTimeMillis().toString()
        )

        cache?.let {
            it.data = value
            it.date = System.currentTimeMillis().toString()
            return add(
                it
            )?:0
        }
        return 0
    }



}



