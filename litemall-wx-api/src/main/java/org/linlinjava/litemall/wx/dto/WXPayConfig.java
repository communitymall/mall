package org.linlinjava.litemall.wx.dto;

import java.io.InputStream;



public class WXPayConfig {

    private String AppId ="";
    private String MchID ="";
    private String Key ="";


    public String getAppID() {
        return AppId;
    }


    public String getMchID() {
        return MchID;
    }


    public String getKey() {
        return Key;
    }


    public InputStream getCertStream() {
        return null;
    }


    public int getHttpConnectTimeoutMs() {
        return 0;
    }


    public int getHttpReadTimeoutMs() {
        return 0;
    }
}
