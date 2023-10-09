package river.chat.lib_core.net.cache

/**
 * Created by beiyongChao on 2023/9/19
 * Description:
 */
object NetCacheType {
    const val NO_CACHE = "NO_CACHE"

    //有效期内只使用缓存
    const val CACHE_NORMAL = "CACHE_NORMAL"

    //失败后使用缓存
    const val CACHE_ON_FAIL = "CACHE_ON_FAIL"
}

object NetCacheKey {
    const val Key_Cache_Type = "Key_Cache_Type"

}