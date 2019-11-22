package org.linlinjava.litemall.wx.util;

import com.izton.sms.SmsApi;
import com.izton.sms.entity.SmsRetMsg;
import com.sms.SmsProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.HttpUtil;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

public class SmsUtil {
    private static final Log logger = LogFactory.getLog(SmsUtil.class);
    public SmsRetMsg send(String phoneNumber,String code){
        SmsApi smsApi = SmsProvider.getSmsApi();
        //【新昕科技】您的验证码是：XXXXX
        String msg = "【新昕科技】您的验证码是："+code;
        SmsRetMsg smsRetMsg = smsApi.sendSms(phoneNumber, msg);
        return  smsRetMsg;
    }

    public void sendSms(String phone,String code){
        long timeStampSec = System.currentTimeMillis()/1000;
        String tKey = String.format("%010d", timeStampSec);
        System.out.println("tKey="+tKey);
        String password = "S54Qx5CQ";

        String s1 = DigestUtils.md5DigestAsHex(password.getBytes());//第一次加密
        s1=s1+tKey;
        String s2 = DigestUtils.md5DigestAsHex(s1.getBytes());//第二次加密
        Map<String,String> map = new HashMap<>();
        map.put("username","hxjh888hy");
        map.put("password",s2);
        //map.put("tpId","");
        //map.put("ext","");
        //map.put("time","");
        //map.put("extend","");
        map.put("tKey",tKey);
        //map.put("signature","");
        map.put("content","验证码:"+code);
        map.put("mobile",phone);
//        Map<Object,Object> records = new HashMap<>();
//        records.put("mobile",phone);
//        Map<Object,Object> tpContent = new HashMap<>();
//        tpContent.put("var1","");
//
//        records.put("tpContent",tpContent);
//
//        map.put("records",records);
        String url = "http://api.mix2.zthysms.com/v2/sendSms";

            String s = HttpUtil.smsSendPost(url, map);
            System.out.println("*************"+s);


    }
}
