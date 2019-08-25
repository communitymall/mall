package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import org.linlinjava.litemall.db.dao.LitemallLogicsticsTransportMapper;
import org.linlinjava.litemall.db.dao.LitemallLogisticsTrucksMapper;
import org.linlinjava.litemall.db.domain.LitemallLogicsticsTransport;
import org.linlinjava.litemall.db.domain.LitemallLogicsticsTransportExample;
import org.linlinjava.litemall.db.domain.LitemallLogisticsTrucks;
import org.linlinjava.litemall.db.domain.LitemallLogisticsTrucksExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LitemallLogicsticsTransportService {
    @Resource
    LitemallLogicsticsTransportMapper transportMapper;

    @Resource
    LitemallLogisticsTrucksMapper trucksMapper;

    /*
    物流订单的添加
     */
    LitemallLogicsticsTransport transport = new LitemallLogicsticsTransport();

    public Object add(Integer companyId,Integer licensePlateNumber,Integer thirdOrder,int transitId ,String driver,Integer freight){
        String id = String.valueOf(transitId);
        String ln = String.valueOf(licensePlateNumber);
        //设置id与物流订单的id
        transport.setTransitId(id);
        transport.setId(transitId);
        transport.setLicensePlateNumber(ln);
        transport.setFreight(freight);
        transport.setDriver(driver);
        //设置创建的时间
        LocalDateTime now = LocalDateTime.now();
        transport.setCreateTime(now);
        //这是创建人
        transport.setCreateUser("测试");
        //订单的状态(初始状态为0)
        Short b=0;
        transport.setTransitStatus(b);

        transportMapper.insertSelective(transport);
        return id;
    }

    /*
    物流订单的修改
     */
    public Object update(String transitId,int status){
        LitemallLogicsticsTransportExample example = new LitemallLogicsticsTransportExample();
        LitemallLogicsticsTransportExample.Criteria criteria = example.createCriteria();
        example.or().andTransitIdEqualTo(transitId);
        transport.setTransitId(transitId);
        Short aShort = Short.valueOf(String.valueOf(status));
        transport.setTransitStatus(aShort);
        transportMapper.updateByExampleSelective(transport,example);
        return null;
    }

    /*
    物流派送的详情
     */
    public List<LitemallLogicsticsTransport> list(String orderId,String transitId,String companyId,String thirdOrder,String licensePlateNumber,int page,int limit){
        LitemallLogicsticsTransportExample example = new LitemallLogicsticsTransportExample();
        LitemallLogicsticsTransportExample.Criteria criteria = example.createCriteria();
        //如果没有输入具体的id 全部查询
        if(!StringUtil.isEmpty(transitId)){
            criteria.andTransitIdEqualTo(transitId);
        }
        PageHelper.startPage(page, limit);
        List<LitemallLogicsticsTransport> litemallLogicsticsTransports = transportMapper.selectByExampleSelective(example);
        return litemallLogicsticsTransports;
    }
}
