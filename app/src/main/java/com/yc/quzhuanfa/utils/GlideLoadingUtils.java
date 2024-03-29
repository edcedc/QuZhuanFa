package com.yc.quzhuanfa.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lcodecore.tkrefreshlayout.header.progresslayout.CircleImageView;
import com.yc.quzhuanfa.R;

import java.security.MessageDigest;

import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION;

/**
 * Created by edison on 2018/4/19.
 */

public class GlideLoadingUtils {

    public static void load(Context act, Object url, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.place_holder);
        Glide.with(act).load(url).apply(options).into(imageView);
    }

    public static void load(Context act, Object url, ImageView imageView, boolean isUser) {
        ld(act, url, imageView, isUser);
    }
    public static void load(Context act, Object url, CircleImageView imageView) {
        ld(act, url, imageView, false);
    }

    public static void loadRounded(Context act, Object url, ImageView imageView){
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(6);
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(act).load(url).apply(options).into(imageView);
    }

    private static void ld(Context act, Object url, ImageView imageView, boolean isUser){
        RequestOptions options = new RequestOptions();
        if (isUser){
            options.placeholder(R.mipmap.place_holder_user);
        }else {
            options.placeholder(R.mipmap.place_holder);
        }
        Glide.with(act).load(url).apply(options).into(imageView);
    }

    public static void loadVideoScreenshot(final Context context, String uri, final ImageView imageView, long frameTimeMicros, final ImageView ivImg3) {
        final RequestOptions requestOptions = RequestOptions.frameOf(frameTimeMicros);
        requestOptions.set(FRAME_OPTION, MediaMetadataRetriever.OPTION_CLOSEST);
        requestOptions.transform(new BitmapTransformation() {
            @Override
            protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                return toTransform;
            }
            @Override
            public void updateDiskCacheKey(MessageDigest messageDigest) {
                try {
                    messageDigest.update((context.getPackageName() + "RotateTransform").getBytes("utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Glide.with(context).load(uri).apply(requestOptions).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                ivImg3.setImageDrawable(resource);
                return false;
            }
        }).into(imageView);
    }


   /* *//**
     * 设置监听请求接口
     *//*
    public static void loadImageViewListener(Context mContext, String path, ImageView mImageView, RequestListener<Drawable> requstlistener) {
        if (mContext != null) {
            Glide.with(mContext).load(path).apply(requestOptions).listener(requstlistener);
        } else {
            LogUtils.e("Picture loading failed,context is null");
        }
    }*/

    /**
     *  计算单张图片 计算宽高
     */
    public static void loadMeasuring(Context act, Object url, ImageView imageView){
        int pWidth = ScreenUtils.getScreenWidth();
        Glide.with(act)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        int w = resource.getIntrinsicWidth();
                        int h = resource.getIntrinsicHeight();

                        int newW;
                        int newH;
                        if (h > w * 3) {//h:w = 5:3
                            newW = pWidth / 2;
                            newH = newW * 5 / 3;
                        } else if (h < w) {//h:w = 2:3
                            newW = pWidth * 2 / 3;
                            newH = newW * 2 / 3;
                        } else {//newH:h = newW :w
                            newW = pWidth / 2;
                            newH = h * newW / w;
                        }
//                    imageView.setLayoutParams(new ViewGroup.LayoutParams(newW, newH));
//                    imageView.layout(0, 0, newW, newH);
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        params.height = newH;
                        params.width = newW;
                        imageView.setLayoutParams(params);
                        return false;
                    }
                }).into(imageView);
    }

    /**

     * 获取第1秒截图

     */

    public static void loadFirstFrame(Context context, String url ,ImageView imageView) {

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(context)

                .setDefaultRequestOptions(

                        new RequestOptions()

                                .frame(10000000)//单位微秒

                                .centerCrop()

                                .error(R.drawable.no_banner)

                                .placeholder(R.drawable.no_banner) )

                .load(url)

                .into(imageView);

    }

}
