package org.linlinjava.litemall.wx.util;

import com.izton.sms.SmsApi;
import com.izton.sms.entity.SmsRetMsg;
import com.sms.SmsProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SmsUtil {
    private static final Log logger = LogFactory.getLog(SmsUtil.class);

    public SmsRetMsg send(String phoneNumber, String code) {
        SmsApi smsApi = SmsProvider.getSmsApi();
        SmsRetMsg smsRetMsg = smsApi.sendSmsTp(phoneNumber,"【易购菜鸟】", code);
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
