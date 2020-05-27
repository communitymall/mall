package org.linlinjava.litemall.wx.service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import org.linlinjava.litemall.core.util.IpUtil;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallOrder;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.linlinjava.litemall.db.util.OrderHandleOption;
import org.linlinjava.litemall.db.util.OrderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.linlinjava.litemall.wx.util.WxResponseCode.ORDER_INVALID_OPERATION;

@Service
public class WxH5Pay {
    private static final Logger logger = LoggerFactory.getLogger("WxH5Pay");
    @Autowired
    private LitemallOrderService orderService;

    @Transactional
    public Object DounifiedOrder(Integer userId, String body,HttpServletRequest request) throws Exception {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = JacksonUtil.parseInteger(body, "orderId");

        if (orderId == null) {
            return ResponseUtil.badArgument();
        }

        LitemallOrder order = orderService.findById(orderId);
        if (order == null) {
            return ResponseUtil.badArgumentValue();
        }
        if (!order.getUserId().equals(userId)) {
            return ResponseUtil.badArgumentValue();
        }

        // 检测订单是否能支付
        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isPay()) {
            return ResponseUtil.fail(ORDER_INVALID_OPERATION, "订单不能支付");
        }
        // 元转成分
        int fee = 0;
        BigDecimal actualPrice = order.getActualPrice();
        fee = actualPrice.multiply(new BigDecimal(100)).intValue();
        String s1 = String.valueOf(fee);

        //返回参数
        Map<String, String> returnMap = new HashMap<>();

        String mweb_url = null;
        //微信配置
        WXPayConfig config = new WXPayConfig() {
            @Override
            public String getAppID() {
                return "wx68f04348d9abb716";
            }

            @Override
            public String getMchID() {
                return "1593550941";
            }

            @Override
            public String getKey() {
                return "JheB2GFdauhDnYe4pbP5MZBLUoZA2e5D";
            }

            @Override
            public InputStream getCertStream() {
                return null;
            }

            @Override
            public int getHttpConnectTimeoutMs() {
                return 0;
            }

            @Override
            public int getHttpReadTimeoutMs() {
                return 0;
            }
        };

        WXPay wxpay = new WXPay(config);
        //请求参数封装
        Map<String, String> data = new HashMap<>();
        data.put("appid", config.getAppID());
        data.put("mch_id", config.getMchID());
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        data.put("body", "H5订单支付"+order.getOrderSn());
        data.put("out_trade_no", order.getOrderSn());//订单号
        data.put("total_fee", s1);//支付金额
        data.put("spbill_create_ip", IpUtil.getIpAddr(request)); //自己的服务器IP地址
        data.put("notify_url", "http://39.97.235.28:8082/wx/order/pay-notify");//异步通知地址（请注意必须是外网）
        data.put("trade_type", "MWEB");//交易类型
        data.put("attach", "");//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
        String s = WXPayUtil.generateSignature(data, config.getKey());  //签名
        data.put("sign", s);//签名
        try {
            logger.info("sign{}",data.get("sign"));
            //使用官方API请求预付订单
            Map<String, String> response = wxpay.unifiedOrder(data);
            System.out.println("response=="+response);
            String returnCode = response.get("return_code");//获取返回码
            logger.info("返回码{}",returnCode); //获取返回码
            //若返回码为SUCCESS，则会返回一个result_code,再对该result_code进行判断
            if (returnCode.equals("SUCCESS")) {
                returnMap.put("ok", "200");
                //拼接返回跳转地址
                //String url= UrlEnCode.urlEncode(wxCfg.getRedirect_url());
//                logger.info("url{}",url);
//                returnMap.put("url", response.get("mweb_url")+"&redirect_url="+url);
                returnMap.put("mweb_url",response.get("mweb_url"));
            } else {
                returnMap.put("ok", "201");
                returnMap.put("url",null);
                return returnMap;
            }
        } catch (Exception e) {
            System.out.println(e);
            //系统等其他错误的时候
        }
        return returnMap;
    }


}
