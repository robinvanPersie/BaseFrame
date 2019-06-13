package com.antimage.basemodule.core;


import android.content.Context;
import android.support.annotation.NonNull;

import com.antimage.basemodule.utils.android.StorageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

/**
 * 使用Generated API ，GlideApp.with(context) 代替 Glide.with(context),
 * 诸如 fitCenter() 和 placeholder() 等选项在 Builder 中直接可用，并不需要再传入单独的 RequestOptions 对象
 * 不必重写子类中的方法。
 *
 */
@GlideModule
public final class MAppGlideModule extends AppGlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        int memoryCacheSizeBytes = 1024 * 1024 * 20;
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));

        if (StorageUtils.isSDCardEnable()) {
            builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, "cache", memoryCacheSizeBytes * 5));
        }
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
    }

    /**
     * false 将不会扫描AndroidManifest.xml,
     * 所以com.github.bumptech.glide:okhttp3-integration:1.5.0@aar的自动合并xml应该是没用了，
     * 所以需要手动注册OKHttpUrlLoader
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
//public final class MAppGlideModule {}