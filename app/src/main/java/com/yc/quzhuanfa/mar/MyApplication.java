package com.yc.quzhuanfa.mar;

import android.content.Context;

import com.nanchen.crashmanager.CrashApplication;
import com.tencent.bugly.crashreport.CrashReport;
import com.yc.quzhuanfa.service.InitializeService;

public class MyApplication extends CrashApplication {

    @Override
    public void onCreate() {
        super.onCreate();
//        ViewTarget.setTagId(R.id.tag_glide);//项目glide 图片ID找不到  会报null

        InitializeService.start(this);
        CrashReport.initCrashReport(getApplicationContext(), "453952ae60", true);
    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

}
