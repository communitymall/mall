package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.dto.*;
import org.linlinjava.litemall.wx.service.EastnetService;
import org.linlinjava.litemall.wx.service.UserTokenManager;
import org.linlinjava.litemall.wx.util.SignUtil;
import org.linlinjava.litemall.wx.util.WechatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 获取关注公众号之后的微信用户信息的接口，如果在微信浏览器里访问
 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxaxxxxxxxxxxxxx&redirect_uri=https://www.yyzheng.cn/o2o/wechatlogin/logincheck&role_type=1&response_type=1&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
 * 则在这里将会获取到code，之后再通过code获取到access_token进而获取到用户信息
 * 原文链接：https://blog.csdn.net/weidong_y/article/details/81141868
 */
@RestController
@RequestMapping("/wx/chat")
@Validated
public class WxChatLoginController {
    private static Logger log = LoggerFactory.getLogger(WxChatLoginController.class);
    @Autowired
    private EastnetService eastnetService;

    @Autowired
    private LitemallUserService litemallUserService;

    @RequestMapping(value = "/logincheck", method = {RequestMethod.GET})
    public String doGet(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("成功l!");

        log.debug("weixin login get...");
        // 获取微信公众号传输过来的code，通过code可获取access_token,进而获取用户信息
        String code = request.getParameter("code");
        // 这个State可以用来传我们自定义的信息，方便程序调用，这里也可以不用
        // String roleType = request.getParameter("state");
        log.debug("weixin login code:" + code);
        WechatUser user = null;
        String openId = null;

        if (null != code) {
            UserAccessToken token;
            try {
                // 通过code获取access_token
                token = WechatUtil.getUserAccessToken(code);
                log.debug("weixin login token:" + token.toString());
                // 通过token获取accessToken
                String accessToken = token.getAccessToken();
                // 通过token获取openId
                openId = token.getOpenId();
                // 通过access_token和openId获取用户昵称等信息
                user = WechatUtil.getUserInfo(accessToken, openId);
                log.debug("weixin login user:" + user.toString());
                request.getSession().setAttribute("openId", openId);
            } catch (Exception e) {
                log.error("error in getUserAccessToken or getUserInfo or findByOpenId: " + e.toString());
                e.printStackTrace();
            }
        }

        // ========todo begin ===========
        // 前面咱门获取到openid后，可以通过它去数据库判断该微信账号是否在我们网站里有对应的 账号了
        // 没有的话这里可以自动创建上，直接实现微信与咱门网站的无缝对接
        List<LitemallUser> litemallUsers = litemallUserService.queryByOpenid(openId);
        if(litemallUsers.size()==0){//在数据库中没有该用户的信息（openid）
            //可以查看用户手机号有没有注册

        }

        AccessToken accessToken = WechatUtil.getAccessToken("wx4c1ec0cb3521699c", "5b750c0bedd20eb3ef7284079cb5689d");
        System.out.println("****************+++" + accessToken.getToken());
        if (null != accessToken) {
            // 调用接口创建菜单
            int result = WechatUtil.createMenu(getMenu(), accessToken.getToken());

            // 判断菜单创建结果
            if (0 == result)
                System.out.println("菜单创建成功！");
            else
                System.out.println("菜单创建失败，错误码：" + result);
        }

        // ========todo end =============

        if (user != null) {
            // 获取到微信验证的信息后返回到指定的路由（需要自己设定）
            return "frontend/index";                                  /*******修改******/
        } else {
            return null;
        }
    }


    /* 接收微信消息处理并做分发
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    public void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("ok");
        // TODO 消息的接收、处理、响应
        // 消息来源的可靠性
        String signature = request.getParameter("signature");// 微信加密签名
        String timestamp = request.getParameter("timestamp");// 时间戳
        String nonce = request.getParameter("nonce");       // 随机数
        //确认此次GET请求来自微信服务器，原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败
        if (!SignUtil.checkSignature(signature, timestamp, nonce)) {
            //消息不可靠，直接返回
            response.getWriter().write("消息不可靠！");
            return;
        }
        //用户每次向公众号发送消息、或者产生自定义菜单点击事件时，响应URL将得到推送
        try {
            System.out.println("ok");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/xml");
//            //调用parseXml方法解析请求消息
////            Map<String, String> map = MessageUtil.pareXml(request);
////            String MsgType = map.get("MsgType");
////            String xml = null;//处理输入消息，返回结果的xml
            String processRequest = eastnetService.processRequest(request,response);
            System.out.println("processRequest="+processRequest);
            response.getWriter().write(processRequest);
        } catch (Exception e) {
            response.getWriter().write("错误！");
        }
    }


    /**
     * 组装菜单数据
     *
     * @return
     */
    private static Menu getMenu() {
        CommonButton btn11 = new CommonButton();
        btn11.setName("蔬菜商城");
        btn11.setType("view");
        btn11.setKey("11");
        btn11.setUrl("http://fxjykx.natappfree.cc");

        CommonButton btn21 = new CommonButton();
        btn21.setName("餐饮加盟");
        btn21.setType("click");
        btn21.setKey("21");

        CommonButton btn22 = new CommonButton();
        btn22.setName("店铺转租");
        btn22.setType("click");
        btn22.setKey("22");

        CommonButton btn23 = new CommonButton();
        btn23.setName("成为供应商");
        btn23.setType("click");
        btn23.setKey("23");

        CommonButton btn24 = new CommonButton();
        btn24.setName("历史文章");
        btn24.setType("click");
        btn24.setKey("24");

        CommonButton btn31 = new CommonButton();
        btn31.setName("个人中心");
        btn31.setType("click");
        btn31.setKey("31");

        CommonButton btn32 = new CommonButton();
        btn32.setName("我的订单");
        btn32.setType("click");
        btn32.setKey("32");

        CommonButton btn33 = new CommonButton();
        btn33.setName("在线客服");
        btn33.setType("click");
        btn33.setKey("33");

        CommonButton btn34 = new CommonButton();
        btn34.setName("售后规则");
        btn34.setType("click");
        btn34.setKey("34");

        CommonButton btn35 = new CommonButton();
        btn35.setName("APP下载");
        btn35.setType("click");
        btn35.setKey("35");

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("蔬菜商城");
        mainBtn1.setSub_button(new CommonButton[]{btn11});

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("合作共赢");
        mainBtn2.setSub_button(new CommonButton[]{btn21, btn22, btn23, btn24});

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("服务支持");
        mainBtn3.setSub_button(new CommonButton[]{btn31, btn32, btn33, btn34, btn35});

        /**
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new Button[]{btn11, mainBtn2, mainBtn3});

        return menu;
    }

    @PostMapping("loginByOpenid")
    public Object loginByOpenid( @RequestBody String body){

        System.out.println("dawawawawawawawawawawawawawawawawawawawawawawawawawawawawawawaw");

        String openId = JacksonUtil.parseString(body, "openId");
        List<LitemallUser> LitemallUsers =  eastnetService.findUserByOpenId(openId);
        Map<Object, Object> result = new HashMap<Object, Object>();
        if(LitemallUsers.size()==0){
            return ResponseUtil.fail();
        }else {
            LitemallUser user = LitemallUsers.get(0);
            // userInfo
            UserInfo userInfo = new UserInfo();
            userInfo.setNickName(user.getNickname());
            userInfo.setAvatarUrl(user.getAvatar());
            userInfo.setId(user.getId());
            // token
            String token = UserTokenManager.generateToken(user.getId());
            result.put("token", token);
            result.put("userInfo", userInfo);
        }
        return ResponseUtil.ok(result);
    }
}
