package org.linlinjava.litemall.core.notify;

import com.izton.sms.SmsApi;
import com.izton.sms.entity.SmsRetMsg;
import com.sms.SmsProvider;
import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

import java.util.logging.Logger;

public class MyListener implements ApplicationListener<ApplicationStartingEvent>{
    @Autowired
    private Environment env;


    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        SpringApplication application = applicationStartingEvent.getSpringApplication();
        application.setBannerMode(Banner.Mode.OFF);
        System.out.println("--------- execute MyApplicationStartingEventListener----------");


    }

}