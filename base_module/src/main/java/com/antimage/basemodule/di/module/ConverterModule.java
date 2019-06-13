package com.antimage.basemodule.di.module;

import com.antimage.basemodule.exception.ApiException;
import com.antimage.basemodule.model.Response;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import javax.annotation.Nullable;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by xuyuming on 2018/10/16.
 */

@Module
public class ConverterModule {

    @Singleton
    @Provides
    Converter.Factory provideConvertFactory(Gson gson) {
        return ResponseConverterFactory.create(gson);
    }

    static class ResponseConverterFactory extends Converter.Factory {

        public static ResponseConverterFactory create() {
            return create(new Gson());

        }

        public static ResponseConverterFactory create(Gson gson) {
            return new ResponseConverterFactory(gson);
        }

        private final Gson gson;

        private ResponseConverterFactory(Gson gson) {
            if (gson == null) throw new NullPointerException("gson == null");
            this.gson = gson;
        }

        @Nullable
        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new RequestBodyConverter<>(gson, adapter);
        }

        @Nullable
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            return new ResponseBodyConverter<>(gson, type);
        }
    }

    /**
     * 参考 retrofit2/converter/gson/GsonRequestBodyConverter.java
     */
    static class RequestBodyConverter<T> implements Converter<T, RequestBody> {

        private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private static final Charset UTF_8 = Charset.forName("UTF-8");

        private Gson gson;
        private TypeAdapter<T> adapter;

        RequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = gson.newJsonWriter(writer);
            adapter.write(jsonWriter, value);
            jsonWriter.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }

    static class ResponseBodyConverter<T> implements Converter<ResponseBody, T> {

        private final Gson gson;
        private final Type type;

        ResponseBodyConverter(Gson gson, Type type) {
            this.gson = gson;
            this.type = type;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            String json = value.string();
            T response = gson.fromJson(json, type);
            if (response instanceof Response) {
                Response r = (Response) response;
                if (r.isSuccess()) {
                    return response;
                } else {
                    throw new ApiException(r.getCode(), r.getMessage());
                }
            } else {
                return response;
            }
        }
    }
}
