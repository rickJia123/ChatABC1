package river.chat.lib_core.net.retrofit

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import river.chat.lib_core.R
import river.chat.lib_core.net.cache.DynamicCacheInterceptor
import river.chat.lib_core.net.cache.DynamicCacheManager
import river.chat.lib_core.net.common.ApiConfig
import river.chat.lib_core.net.common.OperationException
import river.chat.lib_core.net.convert.ConverterFactory
import river.chat.lib_core.net.interceptor.FlowResponseInterceptor
import river.chat.lib_core.net.interceptor.HeadInterceptor
import river.chat.lib_core.net.request.TrusHttpClient
import river.chat.lib_core.utils.log.LogUtil
import river.chat.lib_core.utils.longan.encryptMD5
import river.chat.lib_core.utils.longan.isNetworkAvailable
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * Created by beiyongChao on 2023/2/24
 * Description:
 */
open class BaseApiService {


    private var client: OkHttpClient? = null
    private var flowClient: OkHttpClient? = null

    val DEFAULT_CONNECT_TIMEOUT: Long = 0 //默认超时时间为0，在OkHttpClientWrapper里面会重新设置超时时间


    fun retrofit(): Retrofit {
        return retrofit(DEFAULT_CONNECT_TIMEOUT)
    }

    /**
     * 带设置超时时间的retrofit方法
     */
    fun retrofit(
        connectTimeout: Long
    ): Retrofit {
        //对主动设置超时时间的清空client重新走client配置流程
        if (connectTimeout != DEFAULT_CONNECT_TIMEOUT) {
            client = null
        }
        if (client == null) {
            synchronized(BaseApi::class.java) {
                if (client == null) {
                    val clientBuilder = TrusHttpClient().trusClient
                        .readTimeout(
                            ApiConfig.READ_TIME_OUT.toLong(),
                            TimeUnit.SECONDS
                        )
                        .connectTimeout(
                            ApiConfig.CONNECT_TIME_OUT.toLong(),
                            TimeUnit.SECONDS
                        )
                        .writeTimeout(
                            ApiConfig.WRITE_TIME_OUT.toLong(),
                            TimeUnit.SECONDS
                        )
                        .addInterceptor(HeadInterceptor())
                        .addInterceptor(DynamicCacheInterceptor(DynamicCacheManager()))
                        .followRedirects(false)
                        .followSslRedirects(false)
                    //                            //ipv6域名切换
//                            .addInterceptor(new Ipv6SwitchHostInterceptor(context))
//                            .addInterceptor(new UBTIpv6SwitchHostInterceptor())//UBT埋点ivp6切换拦截器
                    if (ApiConfig.isDebug()) {
                        val loggingInterceptor = HttpLoggingInterceptor { message: String ->
                            LogUtil.d(
                                "river 网络日志： $message"
                            )
                        }
                        clientBuilder.addInterceptor(
                            loggingInterceptor.setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                        )
                    } else {
                        clientBuilder.proxy(Proxy.NO_PROXY)
                    }
                    client = clientBuilder.build()
                }
            }
        }
        return Retrofit.Builder()
            .client(client)
            .baseUrl(ApiConfig.getHost())
            .addConverterFactory(ConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    /**
     * 带设置超时时间的retrofit方法
     */
    fun retrofitFlow(

    ): Retrofit {
        //对主动设置超时时间的清空client重新走client配置流程

        if (flowClient == null) {
            synchronized(BaseApi::class.java) {
                if (flowClient == null) {
                    val clientBuilder = TrusHttpClient().trusClient
                        .readTimeout(
                            ApiConfig.READ_TIME_OUT.toLong(),
                            TimeUnit.SECONDS
                        )
                        .connectTimeout(
                            ApiConfig.CONNECT_TIME_OUT.toLong(),
                            TimeUnit.SECONDS
                        )
                        .writeTimeout(
                            ApiConfig.WRITE_TIME_OUT.toLong(),
                            TimeUnit.SECONDS
                        )
                        .addInterceptor(HeadInterceptor())
                        .addInterceptor(FlowResponseInterceptor())
                        .followRedirects(false)
                        .followSslRedirects(false)
                    if (ApiConfig.isDebug()) {
                        val loggingInterceptor = HttpLoggingInterceptor { message: String ->
                            LogUtil.d(
                                "river 网络日志： $message"
                            )
                        }
                        clientBuilder.addInterceptor(
                            loggingInterceptor.setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                        )
                    } else {
                        clientBuilder.proxy(Proxy.NO_PROXY)
                    }
                    flowClient = clientBuilder.build()
                }
            }
        }
        return Retrofit.Builder()
            .client(flowClient)
            .baseUrl(ApiConfig.getHost())
            .addConverterFactory(ConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun checkNetWorkConnected(context: Context) {
        if (!isNetworkAvailable) {
            throw OperationException(
                context.resources
                    .getString(R.string.network_no_net)
            )
        }
    }

    /**
     * 网络请求签名 requestIdKey = kela792f28requestIdvfdsfd2019
     *
     * @return md5 (os+imei+timestamp+random+requestIdKey)
     */
    fun getSign(triStr: String): String? {
        return (triStr + ApiConfig.API_KEY).encryptMD5()
    }

}