package org.linlinjava.litemall.core.notify;

import com.izton.sms.SmsApi;
import com.izton.sms.entity.SmsRetMsg;
import com.sms.SmsProvider;

/*
自己的短信验证码
 */
public class SmsSend {
    public void send(String phone,String code){
        SmsApi smsApi = SmsProvider.getSmsApi();
        //【新昕科技】您的验证码是：XXXXX
        String msg = "【新昕科技】您的验证码是："+code;
        SmsRetMsg smsRetMsg = smsApi.sendSms(phone, msg);
    }

}
