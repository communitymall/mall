package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wx/wxSms")
@Validated
public class WxSmsController {
    private final Log logger = LogFactory.getLog(WxAuthController.class);

    @PostMapping("statusReportPush")
    public Object statusReportPush(@RequestBody String body, HttpServletRequest request){
        String msgId = JacksonUtil.parseString(body, "msgId");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String reportTime = JacksonUtil.parseString(body, "reportTime");
        String code = JacksonUtil.parseString(body,"code");
        String extend = JacksonUtil.parseString(body,"extend");
        logger.debug("msgId =" + msgId);
        logger.debug("mobile =" + mobile);
        logger.debug("reportTime =" + reportTime);
        logger.debug("code =" + code);
        logger.debug("extend =" + extend);
        return null;
    }
}
