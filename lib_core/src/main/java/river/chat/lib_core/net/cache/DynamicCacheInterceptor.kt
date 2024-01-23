package river.chat.lib_core.net.cache

import okhttp3.Interceptor
import okhttp3.Response
import river.chat.lib_core.net.cache.DynamicCacheHelper.cacheWritingResponse
import river.chat.lib_core.utils.common.GsonKits
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
        var cacheType = initialRequest.header(NetCacheKey.Key_Cache_Type)

        val strategy =
            DynamicCacheStrategy(cacheKey = DynamicCacheHelper.createCacheKey(initialRequest))

        val newRequest = initialRequest

        LogUtil.d(
            " === ============================\nrick NetCacheInterceptor begin key:" + cacheType + ":::" + newRequest.url
        )
        var cacheResponse: Response? = null

        //先使用缓存，再使用接口,本次接口数据下次使用，适用于
        cacheType = NetCacheType.CACHE_NORMAL
        when (cacheType) {
            NetCacheType.CACHE_NORMAL -> {
                cacheResponse = DynamicCacheHelper.redCache(
                    getCurrentCache(newRequest.url.toString()),
                    strategy,
                    newRequest
                )
                LogUtil.d(
                    "rick NetCacheInterceptor 策略---先取缓存再取网络:" + GsonKits.toJson(
                        cacheResponse
                    )
                )
            }

            // 直接请求网络
            NetCacheType.NO_CACHE -> {
                return chain.proceed(newRequest)
            }
        }

        try {
            val response = chain.proceed(newRequest)
            if (response.isSuccessful) {
                //对应策略的接口存入缓存
                if (DynamicCacheHelper.checkNeedStrategySave(cacheType ?: "")) {
                    return cacheWritingResponse(
                        getCurrentCache(newRequest.url.toString()).putCache(
                            strategy.cacheKey,
                            response
                        ),
                        response
                    )
                }
            } else {
                return cacheResponse ?: response
            }

            return response
        } catch (e: Throwable) {
            throw e
        }
    }


}