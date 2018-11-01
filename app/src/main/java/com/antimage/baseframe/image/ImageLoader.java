package com.antimage.baseframe.image;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.antimage.baseframe.R;
import com.antimage.baseframe.core.GlideApp;
import com.antimage.baseframe.utils.android.DeviceUtils;

import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by xuyuming on 2018/10/17.
 */

public class ImageLoader {

    /**
     * 普通加载
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        loadImage(context, url, imageView, R.mipmap.ic_launcher);
    }

    /**
     * 设置placeholder
     * @param context
     * @param url
     * @param imageView
     * @param placeholder
     */
    public static void loadImage(Context context, String url, ImageView imageView, @DrawableRes int placeholder) {
        GlideApp.with(context).load(url)
                .placeholder(placeholder)
                .into(imageView);
    }

    /**
     * 设置尺寸
     */
    public static void loadImage(Context context, String url, ImageView imageView, int width, int height) {
        GlideApp.with(context).load(url)
                .override(width, height)
                .into(imageView);
    }

    /**
     * 列表加载，无动画
     * @param context
     * @param url
     * @param imageView
     * @param placeholder
     */
    public static void loadImageWithoutAnim(Context context, String url, ImageView imageView, @DrawableRes int placeholder) {
        GlideApp.with(context).load(url)
                .dontAnimate()
                .dontTransform()
                .placeholder(placeholder)
                .into(imageView);
    }

    /**
     * 加载圆形icon
     */
    public static void loadRoundIcon(Context context, String url, ImageView imageView, RoundedCornersTransformation.CornerType cornerType) {
        loadRoundIcon(context, url, imageView, R.mipmap.ic_launcher, cornerType);
    }

    public static void loadRoundIcon(Context context, String url, ImageView imageView,
                              @DrawableRes int placeholder, RoundedCornersTransformation.CornerType cornerType) {
        float density = DeviceUtils.getScreenDensity();
        int size = Math.round(density * 60) + 1;
        size = size % 2 == 0 ? size : size + 1;
        GlideApp.with(context).load(url)
                .transform(new RoundedCornersTransformation(size / 2, 0, cornerType))
                .override(size)
                .placeholder(placeholder)
                .into(imageView);
    }

    /**
     * 加载方形icon
     */
    public static void loadSquareIcon(Context context, String url, ImageView imageView) {
        loadSquareIcon(context, url, imageView, R.mipmap.ic_launcher);
    }

    public static void loadSquareIcon(Context context, String url, ImageView imageView, @DrawableRes int placeholder) {
        float density = DeviceUtils.getScreenDensity();
        int size = Math.round(density * 60) + 1;
        size = size % 2 == 0 ? size : size + 1;
        GlideApp.with(context).load(url)
                .override(size)
                .placeholder(placeholder)
                .into(imageView);
    }

    /**
     * 灰色图片
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadGrayImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context).load(url)
                .transform(new GrayscaleTransformation())
                .into(imageView);
    }
}
