package com.yc.quzhuanfa.utils;

import android.app.Activity;
import android.graphics.Bitmap;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.yc.quzhuanfa.R;
import com.yc.quzhuanfa.controller.CloudApi;

import org.json.JSONObject;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2018/5/11.
 * 分享工具
 */

public class ShareTool {

    private static class LazyHolder {
        private static final ShareTool INSTANCE = new ShareTool();
    }

    private ShareTool() {
    }

    private static Activity act;

    private Bitmap imgBitmap;

    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }

    public static final ShareTool getInstance(Activity act_) {
        act = act_;
        return LazyHolder.INSTANCE;
    }

//    private ShareTool() {
//        throw new UnsupportedOperationException("u can't instantiate me...");
//    }

    /****************************分享***********************************/
    public ShareAction shareAction(final String title, final String content, final String image, final String url) {
        LogUtils.e(url);
        return new ShareAction(act).setDisplayList(
                SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE
        )
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        UMWeb web = new UMWeb(url);
                        web.setTitle(title);
                        web.setDescription(content);
                        web.setThumb(new UMImage(act, image));
                        new ShareAction(act)
                                .withMedia(web)
                                .setPlatform(share_media)
                                .setCallback(listener(act))
                                .share();
                    }

                    @Override
                    protected void finalize() throws Throwable {
                        super.finalize();
                    }
                });

    }

    /**
     * 指定分享
     */
    public void shareAppointAction(final SHARE_MEDIA share_media, final String url) {
        LogUtils.e(url);
        UMWeb web = new UMWeb(url);
        web.setTitle(act.getString(R.string.app_name));
        web.setDescription("邀请注册");
        web.setThumb(new UMImage(act, R.mipmap.icon_logo));
        new ShareAction(act).withMedia(web)
                .setPlatform(share_media)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        LogUtils.e("onStart");
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        LogUtils.e("onResult");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {

                    }
                }).share();
    }

    public ShareAction shareActionImage(final String url) {
        return new ShareAction(act).setDisplayList(
                SHARE_MEDIA.WEIXIN,
                SHARE_MEDIA.WEIXIN_CIRCLE
        )
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        UMImage imagelocal = null;
                        UMWeb weblocal = null;
                        imagelocal = new UMImage(act, imgBitmap);//本地文件
                        imagelocal.setThumb(new UMImage(act, R.mipmap.login_logo));
                        imagelocal.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
                        new ShareAction((Activity) act)
                                .withMedia(imagelocal)
                                .setPlatform(share_media)
                                .setCallback(listener(act))
                                .share();
                    }

                    @Override
                    protected void finalize() throws Throwable {
                        super.finalize();
                    }
                });
    }

    public void release() {
        UMShareAPI.get(act).release();
    }

    private CustomShareListener listener(Activity act) {
        return new CustomShareListener(act);
    }

    private UMListener listener;
    public interface UMListener{
        void onResult(SHARE_MEDIA platform);
    }
    public void setUMListener(UMListener listener){
        this.listener = listener;
    }

    private class CustomShareListener implements UMShareListener {

        private WeakReference<Activity> mActivity;

        private CustomShareListener(Activity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onStart(SHARE_MEDIA platform) {
            switch (platform){
                case WEIXIN:
                    if (!IsInstallWeChatOrAliPay.isWeixinAvilible(act)){
                        ToastUtils.showShort("未安装微信客户端");
                        return;
                    }
                    break;
                case QQ:
                    if (!IsInstallWeChatOrAliPay.isWeixinAvilible(act)){
                        ToastUtils.showShort("未QQ微博客户端");
                        return;
                    }
                    break;
            }
            ToastUtils.showShort(platform + " 正在启动");
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showShort(platform + " 分享成功啦");
            if (listener != null){
                listener.onResult(platform);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            LogUtils.e(t.getMessage());
//            ToastUtils.showShort(platform + " 分享失败啦");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShort(platform + " 分享取消了");
        }
    }


    /****************************授权***********************************/
    public void authorization(UMAuthListener listener) {
        boolean b = UMShareAPI.get(act).isAuthorize(act, SHARE_MEDIA.WEIXIN);
        /*if (b) {
            LogUtils.e("删除授权");
            UMShareAPI.get(act).deleteOauth(act, SHARE_MEDIA.WEIXIN, listener);
        } else {
            LogUtils.e("授权");
            UMShareAPI.get(act).doOauthVerify(act, SHARE_MEDIA.WEIXIN, listener);
        }*/
        UMShareAPI.get(act).doOauthVerify(act, SHARE_MEDIA.WEIXIN, listener);
    }

    /****************************登陆***********************************/
    public void ShareLogin(Activity act) {

    }


}
