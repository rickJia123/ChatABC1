package river.chat.lib_core.net.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import river.chat.lib_core.net.common.ApiConfig;
import river.chat.lib_core.utils.log.LogUtil;

/**
 * Created by beiyongChao on 2023/4/23
 * Description:
 */
class DecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private TypeAdapter<T> adapter;
    private Gson gson;

    public DecodeResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    public DecodeResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String responseBodyStr = responseBody.string();
//            responseBodyStr.substring(0,responseBodyStr.length());
        if (ApiConfig.isDebug()) {
            LogUtil.i("原始网 络数据：" + responseBodyStr);
        }
        return adapter.fromJson(responseBodyStr);
//            return adapter.fromJson(Base64Utils.decode(responseBody.string()));
    }
}