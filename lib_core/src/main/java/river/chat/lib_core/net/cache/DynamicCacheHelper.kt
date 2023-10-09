package river.chat.lib_core.net.cache

import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.cache.CacheRequest
import okhttp3.internal.closeQuietly
import okhttp3.internal.discard
import okhttp3.internal.http.ExchangeCodec
import okhttp3.internal.http.RealResponseBody
import okio.Buffer
import okio.Source
import okio.buffer
import river.chat.lib_core.utils.log.LogUtil
import java.io.EOFException
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by beiyongChao on 2023/2/22
 * Description:
 */

object DynamicCacheHelper {






    /**
     * 生成缓存Key
     */
    fun createCacheKey(request: Request): String {
        val requestBody = request.body ?: return request.url.toString()
        val buffer = Buffer()
        requestBody.writeTo(buffer)

        val contentType = requestBody.contentType()
        val charset = contentType?.charset(Charsets.UTF_8) ?: Charsets.UTF_8

        if (isProbablyUtf8(buffer)) {
            val questParam = buffer.readString(charset)
            buffer.close()
            if (questParam.isBlank()) return request.url.toString()
            val builder = request.url.newBuilder()

            kotlin.runCatching {
                builder.addQueryParameter("${request.method.lowercase()}param", questParam)
                return builder.build().toString()
            }.onFailure {
                return ""
            }
        }
        LogUtil.d("rick DynamicCacheHelper createCacheKey url:" + request.url.toString())
        return request.url.toString()
    }


    /**
     * 读取有效缓存(未过期)
     */
    @Throws(IOException::class)
    fun redCache(cache: ICache, strategy: DynamicCacheStrategy, request: Request): Response? {
        val cacheResponse = cache.getCache(strategy.cacheKey, request)
        if (cacheResponse != null) {

            var valid = checkCacheValid(
                cacheResponse.receivedResponseAtMillis,
                strategy.duration
            )
            if (valid) {
                return cacheResponse
            } else {
                cacheResponse.body?.closeQuietly()
            }
        }
        return null
    }

    /**
     * 检查缓存有效性
     */
    fun checkCacheValid(cacheCreateTime: Long, duration: Long): Boolean {
        val responseMillis = cacheCreateTime
        val now = System.currentTimeMillis()
        var usedTime = now - responseMillis
        //已流逝的时间不超过duration
        var hasDurationValid = usedTime <= duration
        return hasDurationValid
    }


    /**
     * 开始缓存
     */
    @Throws(IOException::class)
    fun cacheWritingResponse(cacheRequest: CacheRequest?, response: Response): Response {
        if (cacheRequest == null) return response
        val cacheBodyUnbuffered = cacheRequest.body()

        val source = response.body!!.source()
        val cacheBody = cacheBodyUnbuffered.buffer()

        val cacheWritingSource = object : Source {
            private var cacheRequestClosed = false

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead: Long
                try {
                    bytesRead = source.read(sink, byteCount)
                } catch (e: IOException) {
                    if (!cacheRequestClosed) {
                        cacheRequestClosed = true
                        cacheRequest.abort()
                    }
                    throw e
                }

                if (bytesRead == -1L) {
                    if (!cacheRequestClosed) {
                        cacheRequestClosed = true
                        cacheBody.close()
                    }
                    return -1
                }

                sink.copyTo(cacheBody.buffer, sink.size - bytesRead, bytesRead)
                cacheBody.emitCompleteSegments()
                return bytesRead
            }

            override fun timeout() = source.timeout()

            @Throws(IOException::class)
            override fun close() {
                if (!cacheRequestClosed &&
                    !discard(ExchangeCodec.DISCARD_STREAM_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                ) {
                    cacheRequestClosed = true
                    cacheRequest.abort()
                }
                source.close()
            }
        }

        val contentType = response.header("Content-Type")
        val contentLength = response.body!!.contentLength()
        return response.newBuilder()
            .body(RealResponseBody(contentType, contentLength, cacheWritingSource.buffer()))
            .build()
    }


    private fun isProbablyUtf8(buffer: Buffer): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = buffer.size.coerceAtMost(64)
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0 until 16) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (_: EOFException) {
            return false
        }
    }


    /**
     * 判断对应策略是否需要保存
     */
    fun checkNeedStrategySave(cacheMode: String): Boolean {
        return cacheMode == NetCacheType.CACHE_NORMAL
    }



}