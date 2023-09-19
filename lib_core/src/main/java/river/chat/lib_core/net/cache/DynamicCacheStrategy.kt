package river.chat.lib_core.net.cache


/**
 * Created by beiyongChao on 2023/2/22
 * Description:
 */
data class DynamicCacheStrategy(
    var cacheKey: String = "", //缓存key
    //缓存有效期
    var duration: Long = 0,
)


