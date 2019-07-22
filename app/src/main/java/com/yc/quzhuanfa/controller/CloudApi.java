package com.yc.quzhuanfa.controller;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okrx2.adapter.ObservableBody;
import com.lzy.okrx2.adapter.ObservableResponse;
import com.yc.quzhuanfa.bean.BaseListBean;
import com.yc.quzhuanfa.bean.BaseResponseBean;
import com.yc.quzhuanfa.bean.DataBean;
import com.yc.quzhuanfa.callback.JsonConvert;
import com.yc.quzhuanfa.callback.NewsCallback;
import com.yc.quzhuanfa.utils.Constants;
import com.yc.quzhuanfa.utils.cache.ShareSessionIdCache;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：yc on 2018/6/20.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class CloudApi {

    private static final String url =
            "xiaoyueliang.cn:8080";
//            "10.0.1.229:8080";


    public static final String SERVLET_URL = "http://" + url + "/forward/";

    public static final String SERVLET_IMG_URL = SERVLET_URL + "attach/showPic?attachId=";

    public static final String TEST_URL = ""; //测试

    private static final String TAG = "CloudApi";

    private CloudApi() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static final String ARTICLE_URL = "http://xiaoyueliang.cn/forwardingh5/#/H5?type=2&userId=";//查看H5文章链接
    public static final String AGREEMENT_URL = "http://xiaoyueliang.cn/forwardingh5/#/agreement?agreementType=";//各种协议
    public static final String REGISTER_URL = "http://xiaoyueliang.cn/forwardingh5/#/?userId=";//注册

    /**
     * 文章分类
     */
    public static final String articleGetArticleClass = "article/getArticleClass";
    /**
     * 银行卡列表
     */
    public static final String userGetBankList = "bank/getBankList";
    /**
     * 收入明细
     */
    public static final String userGetBalanceList = "userBalance/getBalanceList";
    /**
     * 银行卡提现记录
     */
    public static final String userGetUserCashList = "userBalance/getUserCashList";

    /**
     * 收入明细
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> userGetBalanceList(int pageNumber, String userId) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + userGetBalanceList)
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("userId", userId)
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<BaseListBean<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }
    /**
     * 徒弟收入明细
     */
    public static Observable<Response<BaseResponseBean<List<DataBean>>>> userApprenticeBalanceList(String userId) {
        return OkGo.<BaseResponseBean<List<DataBean>>>get(SERVLET_URL + "userBalance/getApprenticesDetail")
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("levelUserId", userId)
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<List<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 通用获取验证码  1注册   2找回  4修改  8绑定
     */
    public static Observable<Response<BaseResponseBean>> getCode(String phone, int type) {
        return OkGo.<BaseResponseBean>get(SERVLET_URL + "user/getCode")
                .params("phone", phone)
                .params("type", type)
                .converter(new JsonConvert<BaseResponseBean>() {
                })
                .adapt(new ObservableResponse<BaseResponseBean>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 注册
     */
    public static Observable<JSONObject> register(String mobile, String code, String invitation, String password) {
        return OkGo.<JSONObject>post(SERVLET_URL + "user/register")
                .params("phone", mobile)
                .params("code", code)
                .params("password", EncryptUtils.encryptMD5ToString(password))
//                .params("userCode", invitation)
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<JSONObject>())
                .subscribeOn(Schedulers.io());
    }

    /**
     *  获取微信登陆返回值
     */
    public static Observable<JSONObject> wxLogin(String openid, String access_token){
        return OkGo.<JSONObject>get("https://api.weixin.qq.com/sns/userinfo")
                .params("access_token", access_token)
                .params("openid", openid)
                .converter(new JsonConvert<JSONObject>() {})
                .adapt(new ObservableBody<JSONObject>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 登录
     */
    public static Observable<JSONObject> login(String mobile, String code) {
        return OkGo.<JSONObject>post(SERVLET_URL + "user/pLogin")
                .params("phone", mobile)
                .params("password", EncryptUtils.encryptMD5ToString(code))
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<JSONObject>())
                .subscribeOn(Schedulers.io());
    }
    /**
     * 微信登录
     */
    public static Observable<JSONObject> wxlogin(String openid, String nickname, String headimgurl) {
        return OkGo.<JSONObject>post(SERVLET_URL + "user/wxLogin")
                .params("openid", openid)
                .params("userName", nickname)
                .params("head", headimgurl)
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<JSONObject>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 找回手机密码
     */
    public static String userRetrievePwd = "user/retrievePwd";
    /**
     * 绑定手机
     */
    public static String userBingPhone = "user/bingPhone";

    /**
     * 签到成功展示的列表
     */
    public static String userSignList = "sign/signList";
    /**
     * 获取徒弟列表
     */
    public static String userGetUserList = "userBalance/getApprentices";
    /**
     * 获取用户进来要显示的弹窗
     */
    public static String userGetNewOldUser = "user/getNewOldUser";

    /**
     * 找回密码
     */
    public static Observable<Response<BaseResponseBean>> retrievePwd(String phone, String code, String pwd, String url) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + url)
                .params("phoneNum", phone)
                .params("code", code)
                .params("password", EncryptUtils.encryptMD5ToString(pwd))
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new JsonConvert<BaseResponseBean>() {
                })
                .adapt(new ObservableResponse<BaseResponseBean>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 签到
     */
    public static Observable<Response<BaseResponseBean>> userSaveSign() {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "sign/saveSign")
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new JsonConvert<BaseResponseBean>() {
                })
                .adapt(new ObservableResponse<BaseResponseBean>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 记录文章转发
     */
    public static Observable<Response<BaseResponseBean>> saveForward(String articleId) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "article/saveForward")
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("articleId", articleId)
                .converter(new JsonConvert<BaseResponseBean>() {
                })
                .adapt(new ObservableResponse<BaseResponseBean>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 提现
     */
    public static Observable<Response<BaseResponseBean>> userSaveUserCash(DataBean bankBean, String balance) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "userBalance/saveUserCash")
                .params("bankId", bankBean.getBankId())
                .params("balance", balance)
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new JsonConvert<BaseResponseBean>() {
                })
                .adapt(new ObservableResponse<BaseResponseBean>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取用户首次记录
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> getUserOne() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "userBalance/getUserOne")
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new JsonConvert<BaseResponseBean<DataBean>>() {
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取用户进来要显示的弹窗
     */
    public static Observable<Response<BaseResponseBean<List<DataBean>>>> getNewOldUser() {
        return OkGo.<BaseResponseBean<List<DataBean>>>get(SERVLET_URL + "user/getNewOldUser")
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<List<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 各种协议
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> commonQueryAPPAgreement(int type) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "agreement/getAgrList")
                .params("agreementType", type)
                .converter(new JsonConvert<BaseResponseBean<DataBean>>() {
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取徒弟今昨日收益
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> getApprenticeToy(String userId) {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "userBalance/getUpTodayYesterday")
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("pupiUserId", userId)
                .converter(new JsonConvert<BaseResponseBean<DataBean>>() {
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 更新银行卡
     */
    public static Observable<Response<BaseResponseBean>> saveBank(String bankId, String bankName,
                                                                  String name, String bankDeposit,
                                                                  String phoneNum, String bankNum, String userCard) {
        return OkGo.<BaseResponseBean>post(SERVLET_URL + "bank/saveBank")
                .params("bankId", bankId)
                .params("bankName", bankName)
                .params("name", name)
                .params("bankDeposit", bankName)
                .params("phoneNum", phoneNum)
                .params("bankNum", bankNum)
                .params("userCard", userCard)
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new JsonConvert<BaseResponseBean>() {
                })
                .adapt(new ObservableResponse<BaseResponseBean>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取文章列表
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> articleGetArticleList(int pageNumber, String id) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + "article/getArticleList")
                .params("classId", id)
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<BaseListBean<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取今日/昨日/总收益
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> getTodayYesterdayALl() {
        return OkGo.<BaseResponseBean<DataBean>>get(SERVLET_URL + "userBalance/getTodayYesterdayALl")
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new JsonConvert<BaseResponseBean<DataBean>>() {
                })
                .adapt(new ObservableResponse<BaseResponseBean<DataBean>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取用户详情
     */
    public static Observable<JSONObject> userInfo() {
        return OkGo.<JSONObject>get(SERVLET_URL + "user/userInfo")
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<JSONObject>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 通用list数据
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> list(int pageNumber, String url) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + url)
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<BaseListBean<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 通用list 2
     */
    public static Observable<Response<BaseResponseBean<List<DataBean>>>> list2(String url) {
        return OkGo.<BaseResponseBean<List<DataBean>>>get(SERVLET_URL + url)
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .converter(new NewsCallback<BaseResponseBean<List<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<List<DataBean>>> response) {
                    }
                })
                .adapt(new ObservableResponse<BaseResponseBean<List<DataBean>>>())
                .subscribeOn(Schedulers.io());
    }
}