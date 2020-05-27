package org.linlinjava.litemall.wx.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.github.pagehelper.util.StringUtil;
import com.izton.sms.entity.SmsRetMsg;
import com.newxtc.fw.client.FwClientApi;
import com.newxtc.fw.client.impl.FwClientImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.notify.NotifyService;
import org.linlinjava.litemall.core.util.*;
import org.linlinjava.litemall.core.util.bcrypt.BCryptPasswordEncoder;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.service.CouponAssignService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.dto.UserInfo;
import org.linlinjava.litemall.wx.dto.WxLoginInfo;
import org.linlinjava.litemall.wx.service.CaptchaCodeManager;
import org.linlinjava.litemall.wx.service.SmsService;
import org.linlinjava.litemall.wx.service.UserTokenManager;
import org.linlinjava.litemall.wx.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.linlinjava.litemall.wx.util.WxResponseCode.*;

/**
 * 鉴权服务
 */
@RestController
@RequestMapping("/wx/auth")
@Validated
public class WxAuthController {
    private final Log logger = LogFactory.getLog(WxAuthController.class);

    @Autowired
    private LitemallUserService userService;

    @Autowired
    private WxMaService wxService;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private CouponAssignService couponAssignService;

    @Autowired
    private SmsService smsService;

    SmsUtil smsUtil = new SmsUtil();

    /**
     * 账号登录
     *
     * @param body    请求内容，{ username: xxx, password: xxx }
     * @param request 请求对象
     * @return 登录结果
     */
    @PostMapping("login")
    public Object login(@RequestBody String body, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("body="+body);
        FwClientApi fingerApi = new FwClientImpl();
        String mobile = fingerApi.getMobileByJson(request,body);

        String password = JacksonUtil.parseString(body, "password");
        String code = JacksonUtil.parseString(body, "code");
        if (mobile == null) {
            return ResponseUtil.badArgument();
        }
        //判断登录的手机号是否是注册过的
        List<LitemallUser> userList = userService.queryByMobile(mobile);
        LitemallUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.serious();
        } else if (userList.size() == 0) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "请输入正确的手机号！");
        } else {
            user = userList.get(0);
        }
        //判断账号是否可用
        if (user.getStatus() == 1) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号被禁用！");
        }
        if (user.getStatus() == 2) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号被注销！");
        }
        FwClientApi fwApi = new FwClientImpl();
        // 1 调用【短信防火墙】短信发送请求
        HashMap<String, Object> paramMap = fwApi.getLoginReq(request, mobile);
        String req = fwApi.execReq(paramMap);

        if (!(code == null || code.length() <= 0)) {//验证码登录
            HashMap<String, Object> pm = fwApi.getVerifyReq(request, mobile);
            fwApi.execReq(pm);
            String cachedCaptcha = CaptchaCodeManager.getCachedCaptcha(mobile);
            if (StringUtil.isEmpty(cachedCaptcha)) {
                fwApi.execFail(pm);
                return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "验证码过期！");
            }
            if (!(code.equals(cachedCaptcha))) {
                fwApi.execFail(pm);
                return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "验证码错误！");
            } else {
                fwApi.execSucc(pm);
                fwApi.execSucc(paramMap);
                CaptchaCodeManager.removeCachedCaptcha(mobile);
            }
        } else {//验证码是空值 ，用手机号与密码登录
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(password, user.getPassword())) {
                fwApi.execFail(paramMap);
                return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "手机号或密码不对!");
            }
            fwApi.execSucc(paramMap);
        }
        // 更新登录情况
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        //获得user的ID
        Integer id = user.getId();
        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(mobile);
        userInfo.setAvatarUrl(user.getAvatar());
        userInfo.setId(id);
        // token
        String token = UserTokenManager.generateToken(user.getId());
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }


    @PostMapping("loginWxMobile")
    public Object loginWxMobile(@RequestBody String body, HttpServletRequest request, HttpServletResponse response) {
        String mobile = JacksonUtil.parseString(body, "mobile");
        String password = JacksonUtil.parseString(body, "password");
        if (mobile == null) {
            return ResponseUtil.badArgument();
        }
        //判断登录的手机号是否是注册过的
        List<LitemallUser> userList = userService.queryByMobile(mobile);
        LitemallUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.serious();
        } else if (userList.size() == 0) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "请输入正确的手机号！");
        } else {
            user = userList.get(0);
        }
        //判断账号是否可用
        if (user.getStatus() == 1) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号被禁用！");
        }
        if (user.getStatus() == 2) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号被注销！");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "手机号或密码不对!");
        }

        // 更新登录情况
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        //获得user的ID
        Integer id = user.getId();
        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(mobile);
        userInfo.setAvatarUrl(user.getAvatar());
        userInfo.setId(id);
        // token
        String token = UserTokenManager.generateToken(user.getId());
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }

    /**
     * 微信登录
     *
     * @param wxLoginInfo 请求内容，{ code: xxx, userInfo: xxx }
     * @param request     请求对象
     * @return 登录结果
     */
    @PostMapping("login_by_weixin")
    public Object loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        UserInfo userInfo = wxLoginInfo.getUserInfo();
        if (code == null || userInfo == null) {
            return ResponseUtil.badArgument();
        }
        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sessionKey == null || openId == null) {
            return ResponseUtil.fail();
        }
        LitemallUser user = userService.queryByOid(openId);
        if (user == null) {
            user = new LitemallUser();
            user.setUsername(openId);
            user.setPassword(openId);
            user.setWeixinOpenid(openId);
            user.setAvatar(userInfo.getAvatarUrl());
            user.setNickname(userInfo.getNickName());
            user.setGender(userInfo.getGender());
            user.setUserLevel((byte) 0);
            user.setStatus((byte) 0);
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);

            userService.add(user);

            // 新用户发送注册优惠券
//            couponAssignService.assignForRegister(user.getId());
        } else {
            user.setLastLoginTime(LocalDateTime.now());
            user.setLastLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);
            if (userService.updateById(user) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        // token
        String token = UserTokenManager.generateToken(user.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }

    /**
     * 微信的所有请求验证码
     * <p>
     * TODO
     * 这里需要一定机制防止短信验证码被滥用
     *
     * @param body 手机号码 { mobile }
     * @return
     */
    @PostMapping("wxRegCaptcha")
    public Object wxRegCaptcha(@RequestBody String body, HttpServletResponse response, HttpServletRequest request) {
        String mobile = JacksonUtil.parseString(body, "mobile");
        if (StringUtils.isEmpty(mobile)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isMobileExact(mobile)) {
            return ResponseUtil.badArgumentValue();
        }
        List<LitemallUser> litemallUsers = userService.queryByMobile(mobile);
        if (litemallUsers.size() > 0) {
            return ResponseUtil.fail(405, "该手机不能注册了！");
        }
        String code = CharUtil.getRandomNum(6);// 生成6位随机数
        SmsUtil smsUtil = new SmsUtil();
        SmsRetMsg smsRetMsg = smsUtil.send(mobile, code);
        if (smsRetMsg.getRet() == 0) {
            boolean successful = CaptchaCodeManager.addToCache(mobile, code);
            if (!successful) {
                return ResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY, "验证码未超时5分钟，不能发送");
            }
        }
        if (smsRetMsg.getRet() == 0) {//发送成功
            String resp = smsRetMsg.getResp();
            smsService.sendSucess(resp);
        }
        return ResponseUtil.ok();
    }

    /**
     * 请求注册验证码
     * <p>
     * TODO
     * 这里需要一定机制防止短信验证码被滥用
     *
     * @param body 手机号码 { mobile }
     * @return
     */
    @PostMapping("regCaptcha")
    public Object registerCaptcha(@RequestBody String body, HttpServletResponse response, HttpServletRequest request) {
        FwClientApi fwApi = new FwClientImpl();
        String phoneNumber = fwApi.getMobileByJson(request, body);


        if (phoneNumber == null || phoneNumber.length() <= 0) {
            return ResponseUtil.fail(701, "手机号不能为空！");
        }
        //判断手机号是否合法
        boolean mobileExact = RegexUtil.isMobileExact(phoneNumber);
        if (!mobileExact) {
            return ResponseUtil.fail(701, "手机号格式不合法！");
        }
        List<LitemallUser> users = userService.queryByMobile(phoneNumber);
        if (users.size() > 0) {
            LitemallUser user = users.get(0);
            String mobile = user.getMobile();
            String pwd = user.getPassword();
            if(StringUtil.isEmpty(mobile)){}
            return ResponseUtil.fail(701, "该手机号不可用！");
        }

        // 1 调用【短信防火墙】验证请求
        HashMap<String, Object> paramMap = fwApi.getSendReq(request,phoneNumber);
        //请求防火墙
        String jsonReq = fwApi.execReq(paramMap);
        //报文处理
        String ret = fwApi.getRetVaule(jsonReq,"riskResult");
        if ("REJECT".equals(ret)) {
            // 2 调用【短信防火墙】失败结果
            fwApi.execFail(paramMap);
            return ResponseUtil.fail(701, "发送验证码过于频繁！");
        }
        String code = CharUtil.getRandomNum(6);// 生成6位随机数
        SmsUtil smsUtil = new SmsUtil();
        SmsRetMsg smsRetMsg = smsUtil.send(phoneNumber, code);
        if (smsRetMsg.getRet() == 0) {
            boolean successful = CaptchaCodeManager.addToCache(phoneNumber, code);
            if (!successful) {
                fwApi.execFail(paramMap);
                return ResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY, "验证码未超时2分钟，不能发送");
            }
        }
        if (smsRetMsg.getRet() == 0) {//发送成功
            String resp = smsRetMsg.getResp();
            fwApi.execSucc(paramMap);
            smsService.sendSucess(resp);
        } else {
            fwApi.execFail(paramMap);
        }
        return ResponseUtil.ok();
    }

    /**
     * 账号注册
     *
     * @param //body  请求内容
     *                {
     *                username: xxx,
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * token: xxx,
     * tokenExpire: xxx,
     * userInfo: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */

    /*帐号注册分H5及小程序注册，采用不同的接口进行注册*/
    @PostMapping("/registerH5")
    public Object register_h5(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
        FwClientApi fwApi = new FwClientImpl();
        String mobile = fwApi.getMobileByJson(request, body);

        //String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");
        String password = JacksonUtil.parseString(body, "password");


        // 1 调用【短信防火墙】验证请求
        HashMap<String, Object> paramMap = fwApi.getVerifyReq(request, mobile);
        //请求防火墙
        String jsonReq = fwApi.execReq(paramMap);
        //报文处理
        String smsSendRet = fwApi.getRetVaule(jsonReq,"riskResult");
        if ("REJECT".equals(smsSendRet)) {
            // 2 调用【短信防火墙】失败结果
            fwApi.execFail(paramMap);
        }
        List<LitemallUser> litemallUsers = userService.queryByMobile(mobile);
        if (!(litemallUsers.isEmpty())) {
            fwApi.execFail(paramMap);
            return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "手机号已经被注册了！");
        }
        String cachedCaptcha = CaptchaCodeManager.getCachedCaptcha(mobile);
        if (StringUtil.isEmpty(cachedCaptcha)) {
            fwApi.execFail(paramMap);
            return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "验证码过期！");
        }
        if (!(code.equals(cachedCaptcha))) {
           //调用【短信防火墙】失败结果
            fwApi.execFail(paramMap);
           return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "验证码错误！");
        }
        else {
            //调用【短信防火墙】成功结果
            fwApi.execSucc(paramMap);
            CaptchaCodeManager.removeCachedCaptcha(mobile);
        }
        LitemallUser user = new LitemallUser();
        user.setMobile(mobile);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //给密码加密
        String encode = encoder.encode(password);
        user.setPassword(encode);
        //设置账号可用
        byte b = 0;
        user.setStatus(b);
        userService.add(user);
        return ResponseUtil.ok();
    }

    @PostMapping("/registerWx")
    public Object register_wx(HttpServletRequest request,
                              @RequestParam(value = "mobile") String mobile,
                              @RequestParam(value = "wxCode") String wxCode) {
        List<LitemallUser> litemallUsers = userService.queryByMobile(mobile);
        if (!(litemallUsers.isEmpty())) {
            return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "手机号已经被注册了！");
        }
        LitemallUser user = new LitemallUser();
        user.setMobile(mobile);
        //设置账号可用
        byte b = 0;
        user.setStatus(b);
        userService.add(user);
        return ResponseUtil.ok();
    }


    @PostMapping("register")
    public Object register(@RequestBody String body, HttpServletRequest request, HttpServletResponse response) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");
        String wxCode = JacksonUtil.parseString(body, "wxCode");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(wxCode) || StringUtils.isEmpty(code)) {
            return ResponseUtil.badArgument();
        }

        List<LitemallUser> userList = userService.queryByUsername(username);
        if (userList.size() > 0) {
            return ResponseUtil.fail(AUTH_NAME_REGISTERED, "用户名已注册");
        }

        userList = userService.queryByMobile(mobile);
        if (userList.size() > 0) {
            return ResponseUtil.fail(AUTH_MOBILE_REGISTERED, "手机号已注册!");
        }
        if (!RegexUtil.isMobileExact(mobile)) {
            return ResponseUtil.fail(AUTH_INVALID_MOBILE, "手机号格式不正确");
        }
        //判断验证码是否正确
        String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code)) {
            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");
        }
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(wxCode);
            openId = result.getOpenid();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.fail(AUTH_OPENID_UNACCESS, "openid 获取失败");
        }
        userList = userService.queryByOpenid(openId);
        if (userList.size() > 1) {
            return ResponseUtil.serious();
        }
        if (userList.size() == 1) {
            LitemallUser checkUser = userList.get(0);
            String checkUsername = checkUser.getUsername();
            String checkPassword = checkUser.getPassword();
            if (!checkUsername.equals(openId) || !checkPassword.equals(openId)) {
                return ResponseUtil.fail(AUTH_OPENID_BINDED, "openid已绑定账号");
            }
        }
        LitemallUser user = null;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user = new LitemallUser();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setMobile(mobile);
        user.setWeixinOpenid(openId);
        user.setAvatar("https://yanxuan.nosdn.127.net/80841d741d7fa3073e0ae27bf487339f.jpg?imageView&quality=90&thumbnail=64x64");
        user.setNickname(username);
        user.setGender((byte) 0);
        user.setUserLevel((byte) 0);
        user.setStatus((byte) 0);
        user.setLastLoginTime(LocalDateTime.now());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        userService.add(user);

        // 给新用户发送注册优惠券
        couponAssignService.assignForRegister(user.getId());

        // userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());

        // token
        String token = UserTokenManager.generateToken(user.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }


    /**
     * 登录 重置密码的 请求验证码
     * <p>
     * TODO
     * 这里需要一定机制防止短信验证码被滥用
     *
     * @param body 手机号码 { mobile: xxx, type: xxx }
     * @return
     */
    @PostMapping("captcha")
    public Object captcha(@LoginUser Integer userId, @RequestBody String body,
                          HttpServletResponse response, HttpServletRequest request) {
        Map<String, String> stringStringMap = JacksonUtil.toMap(body);
        FwClientApi fwApi = new FwClientImpl();
        String mobile = fwApi.getMobileByJson(request, body);

        if (StringUtils.isEmpty(mobile)) {
            return ResponseUtil.badArgument();
        }
        if (!RegexUtil.isMobileExact(mobile)) {
            return ResponseUtil.badArgumentValue();
        }
        //判断登录的手机号是否是注册过的
        List<LitemallUser> userList = userService.queryByMobile(mobile);
        if (userList.size() > 1) {
            return ResponseUtil.serious();
        } else if (userList.size() == 0) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "手机号没有注册！");
        }

        //1 调用【短信防火墙】短信发送请求
        HashMap<String, Object> paramMap = fwApi.getSendReq(request, mobile);
        String jsonReq = fwApi.execReq(paramMap);
        String smsSendRet = fwApi.getRetVaule(jsonReq,"riskResult");
        if ("REJECT".equals(smsSendRet)) {
            // 2 调用【短信防火墙】失败结果
            fwApi.execFail(paramMap);
            return ResponseUtil.fail(409, "发送验证码过于频繁！");
        } else {
            String code = CharUtil.getRandomNum(6);
            boolean successful = CaptchaCodeManager.addToCache(mobile, code);
            if (!successful) {
                return ResponseUtil.fail(AUTH_CAPTCHA_FREQUENCY, "验证码未超时2分钟，不能发送");
            }
            SmsRetMsg smsRetMsg = smsUtil.send(mobile, code);
            if (smsRetMsg.getRet() == 0) {
                System.out.println("code====="+code);
            }
            if (smsRetMsg != null && smsRetMsg.getRet() == 0) {
                // 2 调用【短信防火墙】成功结果
                fwApi.execSucc(paramMap);
            } else {
                // 2 调用【短信防火墙】失败结果
                fwApi.execFail(paramMap);
            }
            smsService.sendSucess(smsRetMsg.getResp());
        }
        return ResponseUtil.ok();
    }


    /**
     * 用手机重置密码
     *
     * @param body    请求内容
     *                {
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     * @param request 请求对象
     * @return 登录结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("reset")
    public Object reset(@RequestBody String body, HttpServletRequest request, HttpServletResponse response) {
        FwClientApi fwApi = new FwClientImpl();
        String mobile = fwApi.getMobileByJson(request, body);

        String password = JacksonUtil.parseString(body, "password");
        String passwordRepeat = JacksonUtil.parseString(body, "passwordRepeat");
        //String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");

        // 1 调用【短信防火墙】验证请求
        HashMap<String, Object> paramMap = fwApi.getVerifyReq(request, mobile);
        //请求防火墙
        String jsonReq = fwApi.execReq(paramMap);
        //报文处理
        String ret = fwApi.getRetVaule(jsonReq,"riskResult");
        if ("REJECT".equals(ret)) {
            // 2 调用【短信防火墙】失败结果
            fwApi.execFail(paramMap);
        }

        //判断两次密码是否一致
        if (!(password.equals(passwordRepeat))) {
            return ResponseUtil.fail(401, "两次输入的密码不一致！");
        }
        if (mobile == null || code == null) {
            return ResponseUtil.fail(401, "手机号或验证码为空！");
        }

        //判断验证码输入的是否正确
        String cachedCaptcha = CaptchaCodeManager.getCachedCaptcha(mobile);
        if (!(code.equals(cachedCaptcha))) {
            //调用【短信防火墙】失败结果
            fwApi.execFail(paramMap);
            return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "验证码错误！");
        } else {
            //调用【短信防火墙】成功结果
            fwApi.execSucc(paramMap);
        }
        if (StringUtil.isEmpty(cachedCaptcha)) {
            return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "验证码过期或没有发送验证码！");
        }
        List<LitemallUser> userList = userService.queryByMobile(mobile);
        LitemallUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.serious();
        } else if (userList.size() == 0) {
            fwApi.execFail(paramMap);
            return ResponseUtil.fail(AUTH_MOBILE_UNREGISTERED, "手机号未注册");
        } else {
            user = userList.get(0);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user.setPassword(encodedPassword);

        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        CaptchaCodeManager.removeCachedCaptcha(mobile);
        return ResponseUtil.ok();
    }

    /**
     * 账号手机号码重置
     *
     * @param body    请求内容
     *                {
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("resetPhone")
    public Object resetPhone(@LoginUser Integer userId, @RequestBody String body, HttpServletRequest request) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");

        if (mobile == null || code == null || password == null) {
            return ResponseUtil.badArgument();
        }
        String cachedCaptcha = CaptchaCodeManager.getCachedCaptcha(mobile);
        if (StringUtil.isEmpty(cachedCaptcha)) {
            return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "验证码过期或没有发送验证码！");
        }
        if (!(code.equals(cachedCaptcha))) {
            return ResponseUtil.fail(AUTH_CAPTCHA_UNSUPPORT, "验证码错误！");
        }
        //判断验证码是否正确
        String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code))
            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");

        List<LitemallUser> userList = userService.queryByMobile(mobile);
        LitemallUser user = null;
        if (userList.size() > 1) {
            return ResponseUtil.fail(AUTH_MOBILE_REGISTERED, "手机号已注册");
        }
        user = userService.findById(userId);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号密码不对");
        }

        user.setMobile(mobile);
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }

    /**
     * 账号信息更新  修改昵称的
     *
     * @param body    请求内容
     *                {
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("profile")
    public Object profile(@LoginUser Integer userId, @RequestBody String body, HttpServletRequest request) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        String avatar = JacksonUtil.parseString(body, "avatar");
        Byte gender = JacksonUtil.parseByte(body, "gender");
        String nickName = JacksonUtil.parseString(body, "nickName");

        //进行密码修改接受的参数
        String oldPassword = JacksonUtil.parseString(body, "oldPassword");
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");

        LitemallUser user = userService.findById(userId);
        if (!StringUtils.isEmpty(avatar)) {
            user.setAvatar(avatar);
        }
        if (gender != null) {
            user.setGender(gender);
        }
        if (!StringUtils.isEmpty(nickName)) {
            user.setNickname(nickName);
        }
        //判断旧密码是否正确
        if (!StringUtil.isEmpty(mobile)) {
            user.setMobile(mobile);
            List<LitemallUser> litemallUsers = userService.queryByMobile(mobile);
            if (litemallUsers.size() > 1) {//有两个相同的手机号同时在数据库
                ResponseUtil.fail(407, "系统内部错误");
            }
            LitemallUser litemallUser = litemallUsers.get(0);
            String password1 = litemallUser.getPassword();
            if (!(password1.equals(oldPassword))) {
                ResponseUtil.fail(407, "当前密码输入错误");
            }
            if (!StringUtils.isEmpty(password)) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                //给密码加密
                String encode = encoder.encode(password);
                user.setPassword(encode);
            }
            int i = userService.updateById(user);
            return ResponseUtil.ok();
        } else {
            ResponseUtil.fail(407, "传入参数错误");
        }
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    /**
     * 微信手机号码绑定
     *
     * @param userId
     * @param body
     * @return
     */
    @PostMapping("bindPhone")
    public Object bindPhone(@LoginUser Integer userId, @RequestBody String body) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        LitemallUser user = userService.findById(userId);
        String encryptedData = JacksonUtil.parseString(body, "encryptedData");
        String iv = JacksonUtil.parseString(body, "iv");
        WxMaPhoneNumberInfo phoneNumberInfo = this.wxService.getUserService().getPhoneNoInfo(user.getSessionKey(), encryptedData, iv);
        String phone = phoneNumberInfo.getPhoneNumber();
        user.setMobile(phone);
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @PostMapping("logout")
    public Object logout(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return ResponseUtil.ok();
    }

    /*
    获得用户的信息

     */
    @GetMapping("info")
    public Object info(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        LitemallUser user = userService.findById(userId);
        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("nickName", user.getNickname());
        data.put("avatar", user.getAvatar());
        data.put("gender", user.getGender());
        data.put("mobile", user.getMobile());
        data.put("userId", user.getId());
        return ResponseUtil.ok(data);
    }
}
