package com.yc.quzhuanfa;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.umeng.socialize.UMShareAPI;
import com.yc.quzhuanfa.base.BaseActivity;
import com.yc.quzhuanfa.view.MainFrg;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * /**
 *  *                             _ooOoo_
 *  *                            o8888888o
 *  *                            88" . "88
 *  *                            (| -_- |)
 *  *                            O\  =  /O
 *  *                         ____/`---'\____
 *  *                       .'  \\|     |//  `.
 *  *                      /  \\|||  :  |||//  \
 *  *                     /  _||||| -:- |||||-  \
 *  *                     |   | \\\  -  /// |   |
 *  *                     | \_|  ''\---/''  |   |
 *  *                     \  .-\__  `-`  ___/-. /
 *  *                   ___`. .'  /--.--\  `. . __
 *  *                ."" '<  `.___\_<|>_/___.'  >'"".
 *  *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *  *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *  *          ======`-.____`-.___\_____/___.-`____.-'======
 *  *                             `=---='
 *  *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *  *                     佛祖保佑        永无BUG
 *  *            佛曰:
 *  *                   写字楼里写字间，写字间里程序员；
 *  *                   程序人员写程序，又拿程序换酒钱。
 *  *                   酒醒只在网上坐，酒醉还来网下眠；
 *  *                   酒醉酒醒日复日，网上网下年复年。
 *  *                   但愿老死电脑间，不愿鞠躬老板前；
 *  *                   奔驰宝马贵者趣，公交自行程序员。
 *  *                   别人笑我忒疯癫，我笑自己命太贱；
 *  *                   不见满街漂亮妹，哪个归得程序员？
 * */
public class MainActivity extends BaseActivity {


    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    protected void initView() {


        if (findFragment(MainFrg.class) == null) {
            loadRootFragment(R.id.fl_container, MainFrg.newInstance());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

}
