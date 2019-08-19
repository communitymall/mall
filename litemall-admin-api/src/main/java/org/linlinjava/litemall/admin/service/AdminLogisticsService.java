package org.linlinjava.litemall.admin.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.dao.LitemallLogicsticsTransportMapper;
import org.linlinjava.litemall.db.domain.LitemallLogicsticsTransport;
import org.linlinjava.litemall.db.domain.LitemallLogisticsCompany;
import org.linlinjava.litemall.db.domain.LitemallLogisticsTrucks;
import org.linlinjava.litemall.db.service.LitemallLogicsticsTransportService;
import org.linlinjava.litemall.db.service.LitemallLogisticsCompanyService;
import org.linlinjava.litemall.db.service.LitemallLogisticsTrucksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/*
物流公司的业务处理
 */
@Service
public class AdminLogisticsService {
    private final Log logger = LogFactory.getLog(AdminLogisticsService.class);

    @Autowired
    private LitemallLogisticsCompanyService companyService;

    @Autowired
    private LitemallLogisticsTrucksService trucksService;

    @Autowired
    private LitemallLogicsticsTransportService transportService;

    public Object list(String id, String name, int page, int limit) {
        List<LitemallLogisticsCompany> list = companyService.querySelective(id, name, page, limit);
        return ResponseUtil.okList(list);
    }

    /*
    物流订单的添加
     */
    public Object addOrder(String body) {
        //获得商品的订单号
        List<Integer> orderid = JacksonUtil.parseIntegerList(body, "orderid");
        //商品订单编号的处理？？？

        //获得物流公司的编号
        Integer companyId = JacksonUtil.parseInteger(body, "companyId");
        //获得第三方物流订单编号
        Integer thirdOrder = JacksonUtil.parseInteger(body, "ThirdOrder");
        //获得车辆牌照
        Integer licensePlateNumber = JacksonUtil.parseInteger(body, "licensePlateNumber");
        //获得运费
        Integer freight = JacksonUtil.parseInteger(body, "freight");
        //生成物流订单号
        int transitId = UUID.randomUUID().toString().hashCode();


        //设置司机
        String s = String.valueOf(licensePlateNumber);
        List<LitemallLogisticsTrucks> litemallLogisticsTrucks = trucksService.querySelectiveByLinNumber(s);
        //车牌号没有输入对
        if(litemallLogisticsTrucks.size()==0){
            return  ResponseUtil.fail();
        }
        LitemallLogisticsTrucks trucks = litemallLogisticsTrucks.get(0);
        String driver = trucks.getDriver();
        //其他的字段？？、


        return transportService.add(companyId,licensePlateNumber,thirdOrder,transitId,driver,freight);
    }

    /*
    物流配送订单状态更新
     */
    public Object updateLogisticsOrderStatus(String transitId,int status){
        return transportService.update(transitId,status);
    }

    /*
    物流订单的详情
     */
    public List<LitemallLogicsticsTransport> list(String orderId,String transitId,String companyId,String thirdOrder,String licensePlateNumber,int page,int limit){
        return transportService.list(orderId,transitId,companyId,thirdOrder,licensePlateNumber,page,limit);
    }
}
