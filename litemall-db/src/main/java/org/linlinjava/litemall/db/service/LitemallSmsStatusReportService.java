package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallSmsStatusReportMapper;
import org.linlinjava.litemall.db.domain.LitemallSmsStatusReport;
import org.linlinjava.litemall.db.domain.LitemallSmsStatusReportExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LitemallSmsStatusReportService {
    @Resource
    private LitemallSmsStatusReportMapper smsStatusReportMapper;

    public int insert(LitemallSmsStatusReport smsStatusReport){
        return smsStatusReportMapper.insert(smsStatusReport);
    }

    public Object update(LitemallSmsStatusReport smsStatusReport){
        return smsStatusReportMapper.updateByPrimaryKeySelective(smsStatusReport);
    }

    public List<LitemallSmsStatusReport> select(String msgId){
        LitemallSmsStatusReportExample example = new LitemallSmsStatusReportExample();
        LitemallSmsStatusReportExample.Criteria criteria = example.createCriteria();
        criteria.andMsgidEqualTo(msgId);
        return smsStatusReportMapper.selectByExample(example);
    }

}
