package river.chat.lib_core.net.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by beiyongChao on 2023/2/20
 * Description:
 */
public class HeadInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

                long timestamp = System.currentTimeMillis();

                Request request = chain.request()
                        .newBuilder()
//                        .header("Connection", "close")
                        .header("timestamp", timestamp + "")
                        .build();
                return chain.proceed(request);
        }
}