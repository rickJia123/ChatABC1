//package river.chat.lib_core.net.retrofit
//
//import okhttp3.Interceptor
//import okhttp3.Response
//import okhttp3.ResponseBody
//import okio.Buffer
//import river.chat.lib_core.utils.longan.isAppDebug
//import river.chat.lib_core.utils.other.CharacterUtil
//import java.io.IOException
//import java.nio.charset.StandardCharsets
//import java.util.concurrent.TimeUnit
//
///**
// * ================================================
// * Copyright (c) 2021 All rights reserved
// * 描述：解析框架中的网络请求和响应结果,并以日志形式输出
// * Author: Scott
// * ================================================
// */
//
//class RequestInterceptor : Interceptor {
//
//    lateinit var mPrinter: FormatPrinter
//
//
//    enum class Level {
//        NONE,  //不打印log
//        REQUEST,  //只打印请求信息
//        RESPONSE,  //只打印响应信息
//        ALL //所有数据全部打印
//    }
//
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//        val logRequest = isAppDebug
//        if (logRequest) {
//            //打印请求信息
//            if (request.body != null) {
//                mPrinter.printJsonRequest(request, CharacterUtil.parseParams(request))
//            } else {
//                mPrinter.printFileRequest(request)
//            }
//        }
//        val logResponse = isAppDebug
//        val t1 = if (logResponse) System.nanoTime() else 0
//        val originalResponse: Response = try {
//            chain.proceed(request)
//        } catch (e: Exception) {
////            LogUtil.w("Http Error: $e", LoganConstant.LogType.NET_WORK)
//            throw e
//        }
//        val t2 = if (logResponse) System.nanoTime() else 0
//        val responseBody = originalResponse.body
//
//        //打印响应结果
//        var bodyString: String? = null
//        if (responseBody != null && CharacterUtil.isParseAble(responseBody.contentType())) {
//            bodyString = printResult(originalResponse)
//        }
//        if (logResponse) {
//            val segmentList = request.url
//                .encodedPathSegments
//            val header = originalResponse.headers
//                .toString()
//            val code = originalResponse.code
//            val isSuccessful = originalResponse.isSuccessful
//            val message = originalResponse.message
//            val url = originalResponse.request
//                .url
//                .toString()
//            if (responseBody != null) {
//                mPrinter.printJsonResponse(
//                    TimeUnit.NANOSECONDS.toMillis(t2 - t1),
//                    isSuccessful,
//                    code,
//                    header,
//                    responseBody.contentType(),
//                    bodyString,
//                    segmentList,
//                    message,
//                    url
//                )
//            } else {
//                mPrinter.printFileResponse(
//                    TimeUnit.NANOSECONDS.toMillis(t2 - t1),
//                    isSuccessful, code, header, segmentList, message, url
//                )
//            }
//        }
//
//        return originalResponse
//    }
//
//    /**
//     * 打印响应结果
//     */
//    private fun printResult(response: Response): String? {
//        return try {
//            //读取服务器返回的结果
//            val responseBody = response.newBuilder()
//                .build()
//                .body
//            val source = responseBody!!.source()
//            source.request(Long.MAX_VALUE) // Buffer the entire body.
//            val buffer = source.buffer()
//
//            //获取content的压缩类型
//            val encoding = response
//                .headers["Content-Encoding"]
//            val clone = buffer.clone()
//            //解析response content
//            parseContent(responseBody, encoding, clone)
//        } catch (e: IOException) {
//            e.printStackTrace()
//            "{\"error\": \"" + e.message + "\"}"
//        }
//    }
//
//    /**
//     * 解析服务器响应的内容
//     */
//    private fun parseContent(
//        responseBody: ResponseBody?,
//        encoding: String?,
//        clone: Buffer
//    ): String {
//        var charset = StandardCharsets.UTF_8
//        val contentType = responseBody!!.contentType()
//        if (contentType != null) {
//            charset = contentType.charset(charset)
//        }
//        return clone.readString(charset)
//    }
//}