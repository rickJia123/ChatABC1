package river.chat.lib_core.net.cache

import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.cache.CacheRequest
import java.io.IOException
/**
 * Created by beiyongChao on 2023/2/22
 * Description:
 */

interface ICache {

    fun init(url:String)

    @Throws(IOException::class)
    fun getCache(cacheKey: String?, request: Request): Response?

    @Throws(IOException::class)
    fun putCache(cacheKey: String?, response: Response): CacheRequest?

    @Throws(IOException::class)
    fun remove(cacheKey: String?)

    @Throws(IOException::class)
    fun removeAll()

}