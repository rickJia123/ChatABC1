package river.chat.lib_core.net.cache

import com.google.android.material.circularreveal.CircularRevealHelper.Strategy
import okhttp3.Interceptor
import okhttp3.Response
import river.chat.lib_core.net.cache.DynamicCacheHelper.cacheWritingResponse
import river.chat.lib_core.net.cache.NetCacheKey.Key_Cache_Type
import river.chat.lib_core.utils.log.LogUtil

/**
 * Created by beiyongChao on 2023/2/22
 * Description:
 */
class DynamicCacheInterceptor(
    private val mCache: ICache
) : Interceptor {

    private fun getCurrentCache(url: String): ICache {
        mCache.init(url)
        return mCache
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val initialRequest = chain.request()

        // 读取请求体配置的缓存相关字段
        val cacheType = initialRequest.header(NetCacheKey.Key_Cache_Type)

        val strategy =DynamicCacheStrategy(cacheKey = DynamicCacheHelper.createCacheKey(initialRequest))

        val newRequest = initialRequest


        val cacheResponse = DynamicCacheHelper.redCache(
            getCurrentCache(newRequest.url.toString()),
            strategy,
            newRequest
        )

        return cacheResponse ?: chain.proceed(newRequest)
    }


}