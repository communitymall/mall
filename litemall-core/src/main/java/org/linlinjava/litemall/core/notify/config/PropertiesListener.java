package org.linlinjava.litemall.core.notify.config;


import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 配置文件监听器，用来加载自定义配置文件
 * @author Louis
 * @date Dec 12, 2018
 */
@Component
public class PropertiesListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        AlipayProperties.loadProperties();
    }
}