package river.chat.lib_core.net.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


/**
 * Created by beiyongChao on 2023/4/23
 * Description:
 */
public class ConverterFactory extends Converter.Factory {
        public static ConverterFactory create() {
            return create(new Gson());
        }

        private static ConverterFactory create(Gson gson) {
            return new ConverterFactory(gson);
        }

        private final Gson gson;

        private ConverterFactory(Gson gson) {
            if (gson == null) {
                throw new NullPointerException("gson == null");
            }
            this.gson = gson;
        }

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new DecodeResponseBodyConverter<>(adapter);
        }

        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new EncodeRequestBodyConverter(gson, adapter);
        }


    }