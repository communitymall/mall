package org.linlinjava.litemall.wx.util;

import com.izton.sms.SmsApi;
import com.izton.sms.entity.SmsRetMsg;
import com.tzhl.sms.impl.TzhlSmsImpl;
import org.springframework.stereotype.Controller;

@Controller
public class SmsUtil {
    public final static String autoLoginCode = "【新疆买菜网】提醒您的验证码为:$1,该验证码2分钟内有效，请勿泄露他人 ";
    public final static String autoRegCode = "【新疆买菜网】";

    public SmsRetMsg send(String phoneNumber, String code) {
        //     SmsApi smsApi = SmsProvider.getSmsApi();
        //     SmsRetMsg smsRetMsg = smsApi.sendSmsTp(phoneNumber,"【新疆买菜网】", code);
        //    SmsRetMsg smsRetMsg = smsApi.sendSms(phoneNumber, code);
        //   SmsRetMsg smsRetMsg = getSmsRetMsg(code);


        SmsApi smsImpl = new TzhlSmsImpl();
        String autoLog = autoLoginCode.replace("$1", code).replace("$2", "1s0vfXx");
        SmsRetMsg smsRetMsg = smsImpl.sendSms(phoneNumber, autoLog);
//        String autoReg = autoRegCode.replace("$", "1s0vfXx");
//        SmsRetMsg smsRetMsg = smsImpl.sendSms(phoneNumber, autoReg);
        return smsRetMsg;
    }

    private SmsRetMsg getSmsRetMsg(String code) {
        SmsRetMsg smsRetMsg = new SmsRetMsg();
        if (code.startsWith("1") || code.startsWith("3") || code.startsWith("5") || code.startsWith("7") || code.startsWith("9") || code.startsWith("2") || code.startsWith("4") || code.startsWith("6") || code.startsWith("8")) {
            smsRetMsg.setRet(0);
            smsRetMsg.setMsg(code);
            System.out.println(smsRetMsg);
        } else {
            smsRetMsg.setRet(-1);
            smsRetMsg.setMsg("test");
        }
        return smsRetMsg;
    }


//    public void sendSms(String phone, String code) {
//        long timeStampSec = System.currentTimeMillis() / 1000;
//        String tKey = String.format("%010d", timeStampSec);
//        System.out.println("tKey=" + tKey);
//        String password = "S54Qx5CQ";
//        String s1 = DigestUtils.md5DigestAsHex(password.getBytes());//第一次加密
//        s1 = s1 + tKey;
//        String s2 = DigestUtils.md5DigestAsHex(s1.getBytes());//第二次加密
//        Map<Object, Object> map = new HashMap<>();
//        map.put("username", "hxjh888hy");
//        map.put("password", s2);
//        map.put("tpId", "3835");
//        map.put("time", "");
//        map.put("tKey", tKey);
//        map.put("signature", "【易购菜鸟】");
//        Map<String, String> tpContent = new HashMap<>();
//        tpContent.put("valid_code", code);
//        Map<Object,Object> records = new HashMap<>();
//        records.put("mobile",phone);
//        records.put("tpContent",tpContent);
//        map.put("records", records);
//        String url = "http://api.mix2.zthysms.com/v2/sendSmsTp";
//        String s = HttpUtil.smsSendPost(url, map);
//    }
}
