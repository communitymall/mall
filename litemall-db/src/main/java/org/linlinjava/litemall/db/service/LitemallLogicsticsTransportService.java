package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import org.linlinjava.litemall.db.dao.LitemallLogicsticsTransportMapper;
import org.linlinjava.litemall.db.dao.LitemallLogisticsTrucksMapper;
import org.linlinjava.litemall.db.domain.LitemallLogicsticsTransport;
import org.linlinjava.litemall.db.domain.LitemallLogicsticsTransportExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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

    public Object add(String companyId,String licensePlateNumber,String thirdOrder,String transitId ,String driver,Integer freight,String createUser){

        int i = Integer.parseInt(companyId);
        //设置id与物流订单的id
        transport.setCompanyId(i);
        transport.setTransitId(transitId);
        transport.setLicensePlateNumber(licensePlateNumber);
        transport.setFreight(freight);
        transport.setDriver(driver);
        //设置创建的时间
        LocalDateTime now = LocalDateTime.now();
        transport.setCreateTime(now);
        //这是创建人
        transport.setCreateUser(createUser);
        //订单的状态(4已发货)
        Short b=4;
        transport.setTransitStatus(b);
        return transportMapper.insertSelective(transport);
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
        if(!StringUtil.isEmpty(orderId)){
            criteria.andTransitIdEqualTo(transitId);
        }
        PageHelper.startPage(page, limit);
        List<LitemallLogicsticsTransport> litemallLogicsticsTransports = transportMapper.selectByExampleSelective(example);

        System.out.println(litemallLogicsticsTransports);
        return litemallLogicsticsTransports;
    }
}
