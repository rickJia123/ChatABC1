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
        val cacheType = initialRequest.header(NetCacheKey.Key_Cache_Type)

        val strategy =
            DynamicCacheStrategy(cacheKey = DynamicCacheHelper.createCacheKey(initialRequest))

        val newRequest = initialRequest

        LogUtil.d(
            " === ============================\nrick NetCacheInterceptor begin key:" + cacheType + ":::" + newRequest.url
        )
        when (cacheType) {
            //有效期内只使用缓存
            NetCacheType.CACHE_NORMAL -> {
                val cacheResponse = DynamicCacheHelper.redCache(
                    getCurrentCache(newRequest.url.toString()),
                    strategy,
                    newRequest
                )
                LogUtil.d(
                    "rick NetCacheInterceptor 策略---先取缓存再取网络:" + GsonKits.toJson(
                        cacheResponse
                    )
                )
                if (cacheResponse != null) return cacheResponse
            }

            // 直接请求网络
            NetCacheType.NO_CACHE -> {
                return chain.proceed(newRequest)
            }
        }

        try {
            val response = chain.proceed(newRequest)
            LogUtil.d("rick NetCacheInterceptor 没命中策略 begin:" + response.isSuccessful + "：：：" + response.toString())
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
            }

            return response
        } catch (e: Throwable) {
            throw e
        }
    }


}