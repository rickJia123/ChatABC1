package river.chat.lib_core.net.retrofit;

import android.content.Context;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import river.chat.lib_core.R;
import river.chat.lib_core.net.common.ApiConfig;
import river.chat.lib_core.net.common.OperationException;
import river.chat.lib_core.net.request.TrusHttpClient;
import river.chat.lib_core.utils.log.LogUtil;
import river.chat.lib_core.utils.longan.EncryptKt;
import river.chat.lib_core.utils.longan.NetworkKt;

public class BaseApi {

    private static volatile OkHttpClient client;

    public static final long DEFAULT_CONNECT_TIMEOUT = 0;//默认超时时间为0，在OkHttpClientWrapper里面会重新设置超时时间

    /**
     * 老的调用方式已被弃用，请使用新的网络框架进行调用，参考HDApiService
     */

    public static Retrofit retrofit() {
        return retrofit(DEFAULT_CONNECT_TIMEOUT);
    }

    /**
     * 带设置超时时间的retrofit方法
     */
    public static Retrofit retrofit(
            long connectTimeout) {
        //对主动设置超时时间的清空client重新走client配置流程
        if (connectTimeout != DEFAULT_CONNECT_TIMEOUT) {
            client = null;
        }
        if (client == null) {
            synchronized (BaseApi.class) {
                if (client == null) {
                    OkHttpClient.Builder clientBuilder = new TrusHttpClient().getTrusClient()
                            .readTimeout(ApiConfig.READ_TIME_OUT, TimeUnit.SECONDS)
                            .connectTimeout(ApiConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                            .writeTimeout(ApiConfig.WRITE_TIME_OUT, TimeUnit.SECONDS)
                            .addInterceptor(new HeadInterceptor())
                            .followRedirects(false)
                            .followSslRedirects(false);
//                            //ipv6域名切换
//                            .addInterceptor(new Ipv6SwitchHostInterceptor(context))
//                            .addInterceptor(new UBTIpv6SwitchHostInterceptor())//UBT埋点ivp6切换拦截器

                    if (ApiConfig.isDebug()) {
//                        clientBuilder.addInterceptor(new OkHttpProfilerInterceptor());
                        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
                                message ->
                                        LogUtil.d("http = " + message)
                        );
                        clientBuilder.addInterceptor(loggingInterceptor.setLevel(
                                HttpLoggingInterceptor.Level.BODY));
                    } else {
                        clientBuilder.proxy(Proxy.NO_PROXY);
                    }
                }
            }
        }

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(ApiConfig.getHost())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static void checkNetWorkConnected(Context context) {
        if (!NetworkKt.isNetworkAvailable()) {
            throw new OperationException(context.getResources()
                                                 .getString(R.string.network_no_net));
        }
    }

    /**
     * 网络请求签名 requestIdKey = kela792f28requestIdvfdsfd2019
     *
     * @return md5 (os+imei+timestamp+random+requestIdKey)
     */
    public static String getSign(String triStr) {
        return EncryptKt.encryptMD5(triStr + ApiConfig.API_KEY);
    }

}
