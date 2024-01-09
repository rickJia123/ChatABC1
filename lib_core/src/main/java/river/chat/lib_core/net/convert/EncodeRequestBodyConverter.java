package river.chat.lib_core.net.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by beiyongChao on 2023/4/23
 * Description:
 */
class EncodeRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private Gson gson;
        private TypeAdapter<T> adapter;

        public EncodeRequestBodyConverter(T p0, TypeAdapter<T> adapter) {
            this.adapter = adapter;
        }

        public EncodeRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T o) throws IOException {
//            return RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), Base64Utils.encode(adapter.toJson(o)));
            RequestBody requestBody=RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), adapter.toJson(o));
            return requestBody;
        }
    }