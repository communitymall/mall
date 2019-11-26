package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.db.domain.LitemallSmsStatusReport;
import org.linlinjava.litemall.db.service.LitemallSmsStatusReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/wx/wxSms")
@Validated
public class WxSmsController {
    private final Log logger = LogFactory.getLog(WxAuthController.class);

   @Autowired
   private LitemallSmsStatusReportService smsStatusReportService;

    @PostMapping("statusReportPush")
    public Object statusReportPush(@RequestBody String body, HttpServletRequest request){
        String msgId = JacksonUtil.parseString(body, "msgId");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String reportTime = JacksonUtil.parseString(body, "reportTime");
        String code = JacksonUtil.parseString(body,"code");
        String extend = JacksonUtil.parseString(body,"extend");

        LitemallSmsStatusReport smsStatusReport = new LitemallSmsStatusReport();
        smsStatusReport.setMobile(mobile);
        smsStatusReport.setMsgid(msgId);
        smsStatusReport.setReportTime(reportTime);
        smsStatusReport.setCode(code);
        smsStatusReport.setExtend(extend);
        smsStatusReport.setUpdataTime(LocalDateTime.now());
        List<LitemallSmsStatusReport> list = smsStatusReportService.select(msgId);
        if(list.size()==1){
            Integer id = list.get(0).getId();
            smsStatusReport.setId(id);
            return smsStatusReportService.update(smsStatusReport);
        }else {
            return null;
        }
    }

}
