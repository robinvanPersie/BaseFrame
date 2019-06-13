package com.antimage.basemodule.di.module;

import com.antimage.basemodule.annotation.Exclude;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xuyuming on 2018/10/16.
 */

@Module
public class GsonModule {

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setExclusionStrategies(new AnnotationExclusionStrategy())
                .registerTypeAdapterFactory(new SafeTypeAdapterFactory())
                .registerTypeAdapter(boolean.class, (JsonDeserializer<Boolean>) (json, typeOfT, context) -> {
                    try{
                        return json.getAsInt() == 1;
                    }catch (NumberFormatException e){
                        return json.getAsBoolean();
                    }
                }).create();
    }

    /**
     * http://stackoverflow.com/questions/10596667/how-to-invoke-default-deserialize-with-gson
     * 防止当字段为空时，出现异常
     */
    private static class SafeTypeAdapterFactory implements TypeAdapterFactory {

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
            return new TypeAdapter<T>() {
                @Override
                public void write(JsonWriter out, T value) throws IOException {
                    try {
                        delegate.write(out, value);
                    } catch (IOException e) {
                        delegate.write(out, null);
                    }
                }

                @Override
                public T read(JsonReader in) throws IOException {
                    try {
                        return delegate.read(in);
                    } catch (IOException e) {
                        in.skipValue();
                        return null;
                    } catch (IllegalStateException e) {
                        in.skipValue();
                        return null;
                    } catch (JsonSyntaxException e) {
                        in.skipValue();
                        if (type.getType() instanceof Class) {
                            try {
                                return (T) ((Class)type.getType()).newInstance();
                            } catch (Exception e1) {

                            }
                        }
                        return null;
                    } catch (NumberFormatException e) {
                        in.skipValue();
                        return null;
                    }
                }
            };
        }
    }

    /**
     * 指定不解析字段
     */
    private static class AnnotationExclusionStrategy implements ExclusionStrategy {

        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(Exclude.class) != null;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }
}
