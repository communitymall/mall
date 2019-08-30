package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.wx.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 订单接口
 *
 * @author Louis
 * @date Dec 12, 2018
 */
@RestController()
@RequestMapping("/order")
public class PayController {

    @Autowired
    private PayService payService;


}