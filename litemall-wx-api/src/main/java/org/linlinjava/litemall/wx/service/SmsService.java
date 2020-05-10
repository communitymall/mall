package org.linlinjava.litemall.wx.service;

import com.github.pagehelper.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.db.domain.LitemallSmsStatusReport;
import org.linlinjava.litemall.db.service.LitemallSmsStatusReportService;
import org.linlinjava.litemall.wx.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SmsService {
    private static final Log logger = LogFactory.getLog(SmsUtil.class);
    @Autowired
    private LitemallSmsStatusReportService smsStatusReportService;

    public void sendSucess(String body){
        if(StringUtil.isEmpty(body)) {
            logger.debug("短信发送没有返回参数！");
            return;
        }
        String msg = JacksonUtil.parseString(body, "msg");
        String msgId = JacksonUtil.parseString(body, "msgId");
        String tpId = JacksonUtil.parseString(body, "tpId");
        Integer code = JacksonUtil.parseInteger(body, "code");
        System.out.println("msg="+msg);
        List<String> invalidList = JacksonUtil.parseStringList(body, "invalidList");
        LitemallSmsStatusReport litemallSmsStatusReport = new LitemallSmsStatusReport();
        litemallSmsStatusReport.setSmsResponseCode(code);
        litemallSmsStatusReport.setMsg(msg);
        litemallSmsStatusReport.setMsgid(msgId);
        litemallSmsStatusReport.setTpId(tpId);
        litemallSmsStatusReport.setAddTime(LocalDateTime.now());
        smsStatusReportService.insert(litemallSmsStatusReport);
    }
}
