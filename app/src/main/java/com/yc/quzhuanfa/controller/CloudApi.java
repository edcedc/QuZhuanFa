package com.yc.quzhuanfa.controller;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
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

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：yc on 2018/6/20.
 * 邮箱：501807647@qq.com
 * 版本：v1.0
 */

public class CloudApi {

    public static final String url =
            "xiaoyueliang.cn:8080";
//            "192.168.8.204:8080";


    public static final String SERVLET_URL = "http://" + url + "/forward/";

    public static final String SERVLET_VIDEO_URL = "http://47.75.179.86:8090/";

    public static final String SERVLET_IMG_URL = SERVLET_URL + "attach/showPic?attachId=";

    public static final String TEST_URL = ""; //测试

    private static final String TAG = "CloudApi";

    private CloudApi() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static final String SHARE_URL = "http://ff.wx108.net/shortApi.php?key=jtkM1QXTzoFR0k3VhJmZ5VHdmNzQv1Geth0Zxh3SqdVc0N2VHpUT3FERNdXQE10dBRUT&format=wxbox&longurl=";
    public static final String ARTICLE_URL = "http://xiaoyueliang.cn/forwardingh5/#/H5?type=2&userId=";//查看H5文章链接
    public static final String VIDEO_URL = "http://xiaoyueliang.cn/forwardingh5/#/video?type=2&userId=";//查看H5视频链接
    public static final String AGREEMENT_URL = "http://xiaoyueliang.cn/forwardingh5/#/agreement?agreementType=";//各种协议
    public static final String REGISTER_URL = SHARE_URL + "http://xiaoyueliang.cn/forwardingh5/#/?userId=";//注册

    /**
     * 文章分类
     */
    public static final String articleGetArticleClass = "article/getArticleClass";

    /**
     * 获取视频分类列表
     */
    public static final String videoGetVideoClassifyList = "video/getVideoClassifyList";
    /**
     * 银行卡列表
     */
    public static final String userGetBankList = "bank/getBankList";
    /**
     * 获取我的评价
     */
    public static final String articleGetDiscussList = "article/getDiscussList";
    /**
     * 收入明细
     */
    public static final String userGetBalanceList = "userBalance/getBalanceList";
    /**
     * 银行卡提现记录
     */
    public static final String userGetUserCashList = "userBalance/getUserCashList";
    /**
     * 获取随机十个视频
     */
    public static final String videoGetRandomVideo = "video/getRandomVideo";
     /**
     * 获取历史记录
     */
    public static final String articleGetHistoryList = "article/getHistoryList";
    /**
     * 获取热门标签列表
     */
    public static final String hotitleGetHotitleList = "hotitle/getHotitleList";

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
     * 随机推荐文章
     */
    public static String articleGetRandomArticle = "article/getRandomArticle";

    /**
     * 获取10条提现成功的记录
     */
    public static String userBalanceGetUserCashRecordList = "userBalance/getUserCashRecordList";
    /**
     * 获取徒弟列表
     */
    public static String userGetUserList = "userBalance/getApprentices";
    /**
     * 获取用户进来要显示的弹窗
     */
    public static String userGetNewOldUser = "user/getNewOldUser";



    /**
     * 获取我的视频收藏
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> videoGetVideoCollectList() {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + "video/getVideoCollectList")
                .params("page", 1)
                .params("size", 100)
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取我的收藏（文章视频一起）
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> articleGetArticleCollectList(int pageNumber) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + "article/getCollectList")
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

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
                .adapt(new ObservableResponse<>())
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
                .adapt(new ObservableResponse<>())
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
                .adapt(new ObservableResponse<>())
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
                .adapt(new ObservableBody<>())
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
                .adapt(new ObservableBody<>())
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
                .adapt(new ObservableBody<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 分享走第三方
     */
    public static Observable<JSONObject> share(String url) {
        return OkGo.<JSONObject>get(url)
                .converter(new JsonConvert<JSONObject>() {
                })
                .adapt(new ObservableBody<>())
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
                .adapt(new ObservableBody<>())
                .subscribeOn(Schedulers.io());
    }
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
                .adapt(new ObservableResponse<>())
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
                .adapt(new ObservableResponse<>())
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
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑顶级评论
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> articleSaveArticleDiscuss(String articleId, String content) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "article/saveArticleDiscuss")
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("articleId", articleId)
                .params("content", content)
                .converter(new JsonConvert<BaseResponseBean<DataBean>>() {
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑用户信息
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> userSaveUserInfo(int sex, String userName, String file) {
        PostRequest<BaseResponseBean<DataBean>> post = OkGo.post(SERVLET_URL + "user/saveUserInfo");
        if (sex != -1){
            post.params("sex", sex);
        }
        if (!StringUtils.isEmpty(file)){
            post.params("file", new File(file));
        }
        return post
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userName", userName)
                .converter(new JsonConvert<BaseResponseBean<DataBean>>() {
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑朋友圈顶级评论
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> friendSaveFriendsDiscuss(String articleId, String content, String puserId) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "friend/saveFriendsDiscuss")
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("friendId", articleId)
                .params("puserId", puserId)
                .params("content", content)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑顶级评论
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> videoSaveVideoDiscuss(String videoId, String content) {
        return OkGo.<BaseResponseBean<DataBean>>post(SERVLET_URL + "video/saveVideoDiscuss")
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("videoId", videoId)
                .params("content", content)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }



    /**
     * 保存编辑朋友圈
     */
    public static Observable<Response<BaseResponseBean<DataBean>>> friendSaveFriendsCircle(String text, int type, List<LocalMedia> localMediaList, String address) {
        PostRequest<BaseResponseBean<DataBean>> post = OkGo.post(SERVLET_URL + "friend/saveFriendsCircle");
        if (localMediaList != null && localMediaList.size() != 0){
            if (type == 0){
                post.params("videoFile", new File(localMediaList.get(0).getPath()));
                post.params("videoImg", new File(localMediaList.get(0).getCutPath()));
            }else {
                for (LocalMedia media : localMediaList){
                    post.params("imgFile", new File(media.getCompressPath()));
                }
            }
        }
        return post
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("context", text)
                .params("address", address)
                .converter(new NewsCallback<BaseResponseBean<DataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<DataBean>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
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
                .adapt(new ObservableResponse<>())
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
                .adapt(new ObservableResponse<>())
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
                .adapt(new ObservableResponse<>())
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
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取文章列表
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> articleGetArticleList(int pageNumber, String id, String like) {
        GetRequest<BaseResponseBean<BaseListBean<DataBean>>> request = OkGo.get(SERVLET_URL + "article/getArticleList");
        if (!StringUtils.isEmpty(like)){
            request.params("like", like);
        }
        return request
                .params("classId", id)
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取朋友圈列表
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> friendGetFriendsCircleList(int pageNumber) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + "friend/getFriendsCircleList")
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取朋友圈评论列表
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> friendGetFriendsDiscussList(int pageNumber, String friendId) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + "friend/getFriendsDiscussList")
                .params("friendId", friendId)
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取视频列表
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> videoGetVideoList(int pageNumber, String id, String like) {
        GetRequest<BaseResponseBean<BaseListBean<DataBean>>> request = OkGo.get(SERVLET_URL + "video/getVideoList");
        if (!StringUtils.isEmpty(like)){
            request.params("like", like);
        }
        return request
                .params("classifyId", id)
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
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
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 视频收藏
     */
    public static Observable<Response<BaseResponseBean<Object>>> videoVideoCollect(String ids, int status) {
        return OkGo.<BaseResponseBean<Object>>post(SERVLET_URL + "video/videoCollect")
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("ids", ids)
                .params("status", status)
                .converter(new NewsCallback<BaseResponseBean<Object>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<Object>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 清除历史记录
     */
    public static Observable<Response<BaseResponseBean<Object>>> articleDelHistory() {
        return OkGo.<BaseResponseBean<Object>>post(SERVLET_URL + "article/delHistory")
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .converter(new NewsCallback<BaseResponseBean<Object>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<Object>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 文章收藏
     */
    public static Observable<Response<BaseResponseBean<Object>>> articleArticleCollect(String ids, int status) {
        return OkGo.<BaseResponseBean<Object>>post(SERVLET_URL + "article/articleCollect")
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("ids", ids)
                .params("status", status)
                .converter(new NewsCallback<BaseResponseBean<Object>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<Object>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 保存编辑历史记录
     */
    public static Observable<Response<BaseResponseBean<Object>>> articleSaveHistory(int type, String viacId) {
        return OkGo.<BaseResponseBean<Object>>post(SERVLET_URL + "article/saveHistory")
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("type", type)
                .params("viacId", viacId)
                .converter(new NewsCallback<BaseResponseBean<Object>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<Object>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
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
                .adapt(new ObservableBody<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取文章评论列表
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> articleGetArticleDiscussesList(int pageNumber, String articleId) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + "article/getArticleDiscussesList")
                .params("articleId", articleId)
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }

    /**
     * 获取视频评论列表
     */
    public static Observable<Response<BaseResponseBean<BaseListBean<DataBean>>>> videoGetVideoDiscussList(int pageNumber, String videoId) {
        return OkGo.<BaseResponseBean<BaseListBean<DataBean>>>get(SERVLET_URL + "video/getVideoDiscussList")
                .params("videoId", videoId)
                .params("page", pageNumber)
                .params("size", Constants.pageSize)
                .params("token", ShareSessionIdCache.getInstance(Utils.getApp()).getSessionId())
                .params("userId", ShareSessionIdCache.getInstance(Utils.getApp()).getUserId())
                .converter(new NewsCallback<BaseResponseBean<BaseListBean<DataBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponseBean<BaseListBean<DataBean>>> response) {

                    }
                })
                .adapt(new ObservableResponse<>())
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
                .adapt(new ObservableResponse<>())
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
                .adapt(new ObservableResponse<>())
                .subscribeOn(Schedulers.io());
    }
}