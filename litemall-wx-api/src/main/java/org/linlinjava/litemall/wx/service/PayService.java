package org.linlinjava.litemall.wx.service;

import com.alipay.api.AlipayApiException;
import org.linlinjava.litemall.core.notify.config.Alipay;
import org.linlinjava.litemall.core.notify.config.AlipayBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayService {
    @Autowired
    private Alipay alipay;


    public String aliPay(AlipayBean alipayBean) throws AlipayApiException {
        return alipay.pay(alipayBean);
    }
}
