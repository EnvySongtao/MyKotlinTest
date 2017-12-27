package com.gst.mykotlintest.framework.image;

import android.content.Context;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;

/**
 * author: GuoSongtao on 2017/11/16 15:16
 * email: 157010607@qq.com
 */

public class ImageHelper implements IImageLoader {
    static GlideUtil glideUtil;


    private static class Inner {
        static ImageHelper imageHelper = new ImageHelper();
    }

    private ImageHelper() {

    }

    static void init(Context ctx) {
        glideUtil = GlideUtil.Companion.getInstance();
    }

    public IImageLoader getInstance() {
        return Inner.imageHelper;
    }

    @Override
    public void displayImageFromPath(@NotNull Context ctx, @NotNull String uri, @NotNull ImageView iv) {
        glideUtil.displayImageFromPath(ctx, uri, iv);
    }

    @Override
    public void displayImageFromPathRefresh(@NotNull Context ctx, @NotNull String uri, @NotNull ImageView iv) {
        glideUtil.displayImageFromPathRefresh(ctx, uri, iv);
    }

    @Override
    public void displayImageFromPathByScale(@NotNull Context ctx, @NotNull String uri, @NotNull ImageView iv, double scale) {
        glideUtil.displayImageFromPathByScale(ctx, uri, iv, scale);
    }

    @Override
    public void displayImageFromDrawable(@NotNull Context context, int imageName, @NotNull ImageView imageView) {

        glideUtil.displayImageFromDrawable(context, imageName, imageView);
    }

    @Override
    public void displayGifFromDrawable(@NotNull Context context, int imageName, @NotNull ImageView imageView) {
        glideUtil.displayImageFromDrawable(context, imageName, imageView);
    }

    @Override
    public void displayImageFromUrl(@NotNull Context context, @NotNull String url, @NotNull ImageView imageView) {
        glideUtil.displayImageFromUrl(context, url, imageView);
    }

    @Override
    public void clearOldImage(@NotNull Context context) {
        glideUtil.clearOldImage(context);
    }
}
