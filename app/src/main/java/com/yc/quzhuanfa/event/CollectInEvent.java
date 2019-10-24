package com.yc.quzhuanfa.event;

/**
 * Created by Android Studio.
 * User: ${edison}
 * Date: 2019/10/21
 * Time: 17:39
 */
public class CollectInEvent {

    public int type;//0文章 1视频

    public int isTrue;

    public int position;

    public CollectInEvent(int type, int isTrue, int position) {
        this.type = type;
        this.isTrue = isTrue;
        this.position = position;
    }
}
