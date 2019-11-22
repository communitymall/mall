package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallSmsStatusReportMapper;
import org.linlinjava.litemall.db.domain.LitemallSmsStatusReport;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LitemallSmsStatusReportService {
    @Resource
    private LitemallSmsStatusReportMapper smsStatusReportMapper;

    public int insert(LitemallSmsStatusReport smsStatusReport){
        return smsStatusReportMapper.insert(smsStatusReport);
    }

}
