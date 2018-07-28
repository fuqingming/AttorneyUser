package com.xinfu.attorneyuser.https;

import com.xinfu.attorneyuser.settings.Constant;
import com.xinfu.attorneyuser.settings.GlobalVariables;
import com.xinfu.attorneyuser.utils.MD5Encrypt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HH
 * Date: 2017/11/21
 */

public class ApiStores
{

    static final String urlVersion = "/api/verification";

    /** 发送短信验证码 */
    public static <T> void smsSend(String mobile, int codeType, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=get_code";

        Map<String,Object> map = new HashMap<>();
        map.put("phone",mobile);
        map.put("type",codeType);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 忘记密码 */
    public static <T> void forgetPass(String mobile, String pwd, String code, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=forgetPass";

        Map<String,Object> map = new HashMap<>();
        map.put("phone",mobile);
        map.put("pass",pwd);
        map.put("code",code);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 邮箱找回密码 */
    public static <T> void emailSetPass(String mobile, String pwd, String code, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=emailSetPass";

        Map<String,Object> map = new HashMap<>();
        map.put("email",mobile);
        map.put("pass",pwd);
        map.put("code",code);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 注册 */
    public static <T> void register(String mobile, String pwd, String code, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=lawyer_register";

        Map<String,Object> map = new HashMap<>();
        map.put("phone",mobile);
        map.put("pass",pwd);
        map.put("code",code);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 绑定手机 */
    public static <T> void oauthBindPhone(String id, String phone,String pass, String code, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=oauth_bindphone";

        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("phone",phone);
        map.put("pass",pass);
        map.put("code",code);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 邮箱验证码 */
    public static <T> void emailBack(String mobile, int codeType, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=emailBack";

        Map<String,Object> map = new HashMap<>();
        map.put("email",mobile);
        map.put("type",codeType);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 获取律师列表（找） */
    public static <T> void getLawlist(int page, String strGrade, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=get_lawlist";

        Map<String,Object> map = new HashMap<>();
        map.put("sourcetype", "5");
        map.put("user_id", GlobalVariables.getUserId());
        map.put("grade", strGrade);
        map.put("token",GlobalVariables.getToken());
        map.put("page",page);
        map.put("num", Constant.PAGE_SIZE);

        List<String> keyList = new ArrayList<>();
        keyList.add("user_id");
        keyList.add("sourcetype");
        keyList.add("grade");
        keyList.add("token");
        keyList.add("page");
        keyList.add("num");
        String sign = MD5Encrypt.getInstance().getsignMD5(map,keyList);
        map.put("sign",sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 获取在线律师（问） */
    public static <T> void askGetLawList(int strGrade, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=ask_get_lawlist";

        Map<String,Object> map = new HashMap<>();
        map.put("sourcetype", "5");
        map.put("user_id", GlobalVariables.getUserId());
        map.put("grade", strGrade);
        map.put("token",GlobalVariables.getToken());

        List<String> keyList = new ArrayList<>();
        keyList.add("user_id");
        keyList.add("sourcetype");
        keyList.add("grade");
        keyList.add("token");
        String sign = MD5Encrypt.getInstance().getsignMD5(map,keyList);
        map.put("sign",sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 取消通知律师 */
    public static <T> void cancelOrder(String strWzid, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=cancel_order";

        Map<String,Object> map = new HashMap<>();
        map.put("sourcetype", "5");
        map.put("wzid", strWzid);
        map.put("token",GlobalVariables.getToken());

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("wzid");
        keyList.add("token");
        String sign = MD5Encrypt.getInstance().getsignMD5(map,keyList);
        map.put("sign",sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 律师个人主页（找）咨询 */
    public static <T> void findConsult(int strGrade,String strLawyerId, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=find_consult";

        Map<String,Object> map = new HashMap<>();
        map.put("sourcetype", "5");
        map.put("uid", GlobalVariables.getUserId());
        map.put("lid", strLawyerId);
        map.put("grade", strGrade);
        map.put("token",GlobalVariables.getToken());

        List<String> keyList = new ArrayList<>();
        keyList.add("uid");
        keyList.add("lid");
        keyList.add("sourcetype");
        keyList.add("grade");
        keyList.add("token");
        String sign = MD5Encrypt.getInstance().getsignMD5(map,keyList);
        map.put("sign",sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 律师个人主页（找）咨询 */
    public static <T> void focus(String strLawyerId, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=focus";

        Map<String,Object> map = new HashMap<>();
        map.put("sourcetype", "5");
        map.put("uid", GlobalVariables.getUserId());
        map.put("lid", strLawyerId);
        map.put("token",GlobalVariables.getToken());

        List<String> keyList = new ArrayList<>();
        keyList.add("uid");
        keyList.add("lid");
        keyList.add("sourcetype");
        keyList.add("token");
        String sign = MD5Encrypt.getInstance().getsignMD5(map,keyList);
        map.put("sign",sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 留言 */
    public static <T> void message(String strLawyerId,String strContent, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=message";

        Map<String,Object> map = new HashMap<>();
        map.put("sourcetype", "5");
        map.put("uid", GlobalVariables.getUserId());
        map.put("lid", strLawyerId);
        map.put("content", strContent);
        map.put("token",GlobalVariables.getToken());

        List<String> keyList = new ArrayList<>();
        keyList.add("uid");
        keyList.add("lid");
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("content");
        String sign = MD5Encrypt.getInstance().getsignMD5(map,keyList);
        map.put("sign",sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 用户律币接口 */
    public static <T> void userBalance( HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=user_balance";

        Map<String,Object> map = new HashMap<>();
        map.put("sourcetype", "5");
        map.put("uid", GlobalVariables.getUserId());
        map.put("token",GlobalVariables.getToken());

        List<String> keyList = new ArrayList<>();
        keyList.add("uid");
        keyList.add("sourcetype");
        keyList.add("token");
        String sign = MD5Encrypt.getInstance().getsignMD5(map,keyList);
        map.put("sign",sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /**
     * 资金托管
     * @param strSlug "asset"=>'资产托管',"knowledge"=>'知识产权',"find"=>'调查取证',"scholar"=>'专家证人',"insurance"=>'保险服务',"market"=>'上市服务'
     * @param strCategory   子分类
     * @param name          真实姓名
     * @param mobile        手机号
     * @param remark        备注
     * @param company       公司
     */
    public static <T> void userAddService(String strSlug,
                                          int strCategory,
                                          String name,
                                          String mobile,
                                          String remark,
                                          String company,
                                          HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=user_add_service";

        Map<String,Object> map = new HashMap<>();
        map.put("sourcetype", "5");
        map.put("lid", GlobalVariables.getUserId());
        map.put("token",GlobalVariables.getToken());
        map.put("slug", strSlug);
        map.put("category", strCategory);
        map.put("name", name);
        map.put("mobile", mobile);
        map.put("remark", remark);
        map.put("company", company);

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("lid");
        keyList.add("token");
        keyList.add("slug");
        keyList.add("category");
        keyList.add("name");
        keyList.add("mobile");
        keyList.add("remark");
        keyList.add("company");
        String sign = MD5Encrypt.getInstance().getsignMD5(map,keyList);
        map.put("sign",sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 审文书/写文书
     * slug look    审文书
     * slug write   写文书
     * */
    public static <T> void userInstrument(int m_iCategory, String slug, ArrayList<String> arrImages, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=user_instrument";

        String  a = arrImages.toString();
        Map<String,Object> map = new HashMap<>();
        map.put("sourcetype", "5");
        map.put("lid", GlobalVariables.getUserId());
        map.put("token",GlobalVariables.getToken());
        map.put("category", m_iCategory);
        map.put("slug", slug);
        map.put("mainImages", arrImages);

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("lid");
        keyList.add("token");
        keyList.add("category");
        keyList.add("slug");
        keyList.add("mainImages");
        String sign = MD5Encrypt.getInstance().getsignMD5(map,keyList);
        map.put("sign",sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 开始咨询（问） */
    public static <T> void askStartConsult(String wzid, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=ask_start_consult";

        Map<String,Object> map = new HashMap<>();
        map.put("sourcetype","5");
        map.put("wzid", wzid);
        map.put("hx_user", GlobalVariables.getHXName());
        map.put("token",GlobalVariables.getToken());
        List<String> keyList = new ArrayList<>();
        keyList.add("wzid");
        keyList.add("sourcetype");
        keyList.add("hx_user");
        keyList.add("token");
        String sign = MD5Encrypt.getInstance().getsignMD5(map,keyList);
        map.put("sign",sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 个人中心-律币充值 */
    public static <T> void recharge(int amount, boolean isWX,HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=user_recharge";

        Map<String,Object> map = new HashMap<>();
        map.put("price", amount);
        map.put("lid", GlobalVariables.getUserId());
        map.put("token",GlobalVariables.getToken());
        map.put("paySlug",isWX ? "wxpay":"alipay");

//        List<String> keyList = new ArrayList<>();
//        keyList.add("price");
//        keyList.add("lid");
//        keyList.add("token");
//        String sign = MD5Encrypt.getInstance().getsignMD5(map,keyList);
//        map.put("sign",sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 登录 */
    public static <T> void oauthLogin(   String type,
                                         String openId,
                                         String image,
                                         String nickname,
                                         String accessToken,
                                         String sex,
                                         HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=oauth_login";

        Map<String,Object> map = new HashMap<>();
        map.put("type",type);
        map.put("openId",openId);
        map.put("image",image);
        map.put("nickname",nickname);
        map.put("accessToken",accessToken);
        map.put("sex",sex);
        map.put("address","中国");

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 登录 */
    public static <T> void login(String mobile, String pwd, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=login";

        Map<String,Object> map = new HashMap<>();
        map.put("phone",mobile);
        map.put("pass",pwd);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 获取律师数量（问） */
    public static <T> void getOnlineNum(int type, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=get_online_num";

        Map<String,Object> map = new HashMap<>();
        map.put("type",type);
        map.put("token", GlobalVariables.getToken());   //防止空指针

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 用户律币接口） */
    public static <T> void userBalance(int type, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=user_balance";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");
        map.put("uid", GlobalVariables.getUserId());

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("uid");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /**  律师个人主页（找） **/
    public static <T> void homePage( String strLawId,HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=homepage";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");
        map.put("user_id", GlobalVariables.getUserId());
        map.put("law_id", strLawId);

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("user_id");
        keyList.add("law_id");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /**  个人中心-获取用户信息 **/
    public static <T> void userInfo(HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=user_info";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");
        map.put("lid", GlobalVariables.getUserId());

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("lid");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /**  我的卡券 **/
    public static <T> void vipCardInfo(HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=vip_card_info";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");
        map.put("lid", GlobalVariables.getUserId());

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("lid");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /**  用户资料-个人信息 **/
    public static <T> void userPpdateInfo(String avatar,String sex,String nickname,String email,HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=user_update_info";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");
        map.put("lid", GlobalVariables.getUserId());
        map.put("avatar", avatar);
        map.put("sex", sex);
        map.put("nickname", nickname);
        map.put("email", email);

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("lid");
        keyList.add("avatar");
        keyList.add("sex");
        keyList.add("nickname");
        keyList.add("email");

        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /**  个人中心-积分兑换律币（10积分兑换一律币） **/
    public static <T> void userScoreReplace(int score,HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=user_score_replace";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");
        map.put("lid", GlobalVariables.getUserId());
        map.put("score", score);

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("lid");
        keyList.add("score");

        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 获取领域分类的接口 */
    public static <T> void getProfessionList( HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=get_profession_list";

        Map<String,Object> map = new HashMap<>();
        map.put("sourcetype","5");
        map.put("token",GlobalVariables.getToken());
        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        String sign = MD5Encrypt.getInstance().getsignMD5(map,keyList);
        map.put("sign",sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 获取领域分类的接口 */
    public static <T> void userBatchCat( HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=user_batch_cat";

        Map<String,Object> map = new HashMap<>();
        map.put("sourcetype","5");
        map.put("token",GlobalVariables.getToken());
        map.put("lid",GlobalVariables.getToken());
        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("lid");
        String sign = MD5Encrypt.getInstance().getsignMD5(map,keyList);
        map.put("sign",sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 获取领域分类的接口 */
    public static <T> void logout( HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=logout";

        Map<String,Object> map = new HashMap<>();
        map.put("sourcetype","5");
        map.put("token",GlobalVariables.getToken());
        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        String sign = MD5Encrypt.getInstance().getsignMD5(map,keyList);
        map.put("sign",sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 轮播广告接口 */
    public static <T> void getSlideAd( HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=slide_ad";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 获取律师列表（找） */
    public static <T> void getLawList(String address, String profession, int pagenum, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=get_lawlist";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");
        map.put("address", address);
        map.put("profession", profession);
        map.put("num", Constant.PAGE_SIZE);
        map.put("pagenum", pagenum);


        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("address");
        keyList.add("profession");
        keyList.add("num");
        keyList.add("pagenum");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /**
     * 便民查询返回tag及资讯列表
     * @param slug  （"company"=>'企业信息',"regulation"=>'法律法规',"instrument"=>'裁判文书',"news"=>法律咨询）
     */
    public static <T> void helpListTag(String slug, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=help_list_tag";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");
        map.put("lid", GlobalVariables.getUserId());
        map.put("slug", slug);

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("lid");
        keyList.add("slug");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /**  VIP卡片列表 */
    public static <T> void vipCardList(HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=vip_card_list";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 购买VIP卡片列表 */
    public static <T> void vipCardBuy(String cardid, boolean isWX, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=vip_card_buy";

        Map<String,Object> map = new HashMap<>();
        map.put("cardid", cardid);
        map.put("lid", GlobalVariables.getUserId());
        map.put("token",GlobalVariables.getToken());
        map.put("paySlug",isWX ? "wxpay":"alipay");

        HttpClient.rxPost(url,map,httpCallback);
    }

    /**  验证线下卡券 */
    public static <T> void vipCardValidate(String strNum, String strPwd, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=vip_card_validate";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");
        map.put("slug", strNum);
        map.put("password",strPwd);

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("slug");
        keyList.add("password");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /**  合同文书-文书列表 */
    public static <T> void userBatchLst(String strCatId, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=user_batch_list";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");
        map.put("lid", GlobalVariables.getUserId());
        map.put("catid", strCatId);

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("lid");
        keyList.add("catid");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /**
     * 合同文书-文书详情页
     * @param uuid  文书id
     */
    public static <T> void userBatchDetail(String uuid, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=user_batch_detail";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");
        map.put("lid", GlobalVariables.getUserId());
        map.put("uuid", uuid);

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("lid");
        keyList.add("uuid");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 合同文书-文书购买 */
    public static <T> void userBatchBuy(String uuid, boolean isWX, HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=user_batch_buy";

        Map<String,Object> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("lid", GlobalVariables.getUserId());
        map.put("token",GlobalVariables.getToken());
        map.put("paySlug",isWX ? "wxpay":"alipay");

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 律界说法 */
    public static <T> void fragmentList( HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=fragment_list";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");
        map.put("lid", GlobalVariables.getUserId());

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("lid");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /**  便民查询返回服务热线 **/
    public static <T> void helpListHotline( HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=help_list_hotline";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");
        map.put("lid", GlobalVariables.getUserId());

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("lid");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 获取推荐律师 */
    public static <T> void getRecommended(HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=get_recommended";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /** 获取位置分类的接口 */
    public static <T> void getCity(HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=get_city";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /**
     * 个人中心-我的订单
     */
    public static <T> void userOrderList(HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=user_orderlist";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");
        map.put("lid", GlobalVariables.getUserId());

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        keyList.add("lid");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

    /**
     * 收藏
     */
    public static <T> void userFavorList( HttpCallback<T> httpCallback)
    {
        String url =  urlVersion+"?action=user_favor_list";

        Map<String,Object> map = new HashMap<>();
        map.put("token", GlobalVariables.getToken());
        map.put("sourcetype", "5");

        List<String> keyList = new ArrayList<>();
        keyList.add("sourcetype");
        keyList.add("token");
        String sign = MD5Encrypt.getInstance().getsignMD5(map, keyList);
        map.put("sign", sign);

        HttpClient.rxPost(url,map,httpCallback);
    }

}
