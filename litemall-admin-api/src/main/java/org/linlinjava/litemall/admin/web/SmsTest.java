package org.linlinjava.litemall.admin.web;

import com.izton.sms.SmsApi;
import com.izton.sms.entity.SmsRetMsg;
import com.sms.SmsProvider;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
@ComponentScan
@ServletComponentScan
public class SmsTest {
    //@Autowired(required = false)
   // private IztoListenerSmsSender smsSender;

    @RequiresPermissions("admin:test:test")
    @RequestMapping("/test")

    private void test(){
        String phone="15110693641";
        String msg="8888";
        //System.out.println(smsSender);
        SmsApi smsApi = SmsProvider.getSmsApi();
        //String msg = "8888";
        SmsRetMsg smsRetMsg = smsApi.sendSms(phone, msg);
        System.out.println("测试");
    }
}
