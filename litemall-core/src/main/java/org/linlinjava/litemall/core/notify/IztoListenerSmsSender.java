package org.linlinjava.litemall.core.notify;

import com.izton.sms.SmsApi;
import com.izton.sms.entity.SmsRetMsg;
import com.sms.SmsProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class IztoListenerSmsSender {
    private final Log logger = LogFactory.getLog(IztoListenerSmsSender.class);
    public Object send(String phone, String content){
        SmsApi smsApi = SmsProvider.getSmsApi();
        String msg = "8888";
        SmsRetMsg smsRetMsg = smsApi.sendSms(phone, msg);
        return null;
    }
}
