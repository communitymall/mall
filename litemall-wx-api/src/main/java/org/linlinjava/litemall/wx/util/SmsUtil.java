package org.linlinjava.litemall.wx.util;

import com.izton.sms.SmsApi;
import com.izton.sms.entity.SmsRetMsg;
import com.sms.SmsProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SmsUtil {
    private static final Log logger = LogFactory.getLog(SmsUtil.class);
    public SmsRetMsg send(String phoneNumber,String code){
        SmsApi smsApi = SmsProvider.getSmsApi();
        //【新昕科技】您的验证码是：XXXXX
        String msg = "【新昕科技】您的验证码是："+code;
        //SmsRetMsg smsRetMsg = smsApi.sendSms(phoneNumber, msg);
        SmsRetMsg smsRetMsg =new SmsRetMsg();
        smsRetMsg.setRet(0);
        return  smsRetMsg;
    }
}
