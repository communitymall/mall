package org.linlinjava.litemall.wx.web;


import org.linlinjava.litemall.wx.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/wx/chat")
@Validated
public class WxChatController {
    private static Logger log = LoggerFactory.getLogger(WxChatController.class);

    @RequestMapping(method = { RequestMethod.GET })
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        log.debug("wechat get...");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                log.debug("wechat get success....");
                out.print(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                out.close();
        }
    }


}
