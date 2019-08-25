package org.linlinjava.litemall.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "litemall.dxt")
public class ZztProperties {
    private String smsUrl;

    private String userid;

    private String password;

    private String ext;

    public String getSmsUrl() {
        return smsUrl;
    }

    public void setSmsUrl(String smsUrl) {
        this.smsUrl = smsUrl;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
