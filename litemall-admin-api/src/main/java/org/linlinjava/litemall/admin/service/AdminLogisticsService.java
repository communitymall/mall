package org.linlinjava.litemall.admin.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Autowired
    private LitemallLogicticsTransportDetailService transportDetailService;

    @Autowired
    private LitemallOrderService orderService;

    public Object list(String id, String name, int page, int limit) {
        List<LitemallLogisticsCompany> list = companyService.querySelective(id, name, page, limit);
        return ResponseUtil.okList(list);
    }

    /*
    物流订单的添加
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
    public Object addOrder(String body) {
        //获得物流公司的编号
        String companyId = JacksonUtil.parseString(body, "companyId");
        //获得车辆牌照
        String licensePlateNumber = JacksonUtil.parseString(body, "licensePlateNumber");
        //判断输入的物流公司与车辆牌照在车辆表中是否存在
        List<LitemallLogisticsTrucks> litemallLogisticsTrucks1 = trucksService.querySelectiveByCompanyIdAndLn(companyId, licensePlateNumber);
        if (litemallLogisticsTrucks1.size() == 0) {
            //物流公司与车辆牌照在车辆表中是不存在
            return ResponseUtil.fail(512, "物流公司ID或车牌号输入错误");
        }
        //生成物流订单号
        Integer transitId = UUID.randomUUID().toString().hashCode();
        String transitId1 = transitId.toString();

        //获得商品的订单号
        List<String> orders = JacksonUtil.parseStringList(body, "orders");

        //得到创建人的信息
        Subject currentUser = SecurityUtils.getSubject();
        LitemallAdmin admin = (LitemallAdmin) currentUser.getPrincipal();
        String createUser = admin.getUsername();

        //获得物流公司的名称
        LitemallLogisticsCompany company = new LitemallLogisticsCompany();
        company.setId(Integer.parseInt(companyId));
        LitemallLogisticsCompany company1 = companyService.selectById(company);
        //获得运费
        Integer freight = JacksonUtil.parseInteger(body, "freight");

        String ft = JacksonUtil.parseString(body, "freight");
        BigDecimal f = new BigDecimal(ft);

        //得到司机
        LitemallLogisticsTrucks trucks = litemallLogisticsTrucks1.get(0);
        String driver = trucks.getDriver();
        String thirdOrder = null;
        try {//异常捕获
            LitemallOrder order = new LitemallOrder();
            for (int i = 0; i < orders.size(); i++) {
                transportDetailService.add(transitId1, orders.get(i));
                //数据操作没有错误。修改订单的状态为（4已发货），设置订单的物流编号，设置订单的物流公司名称,设置订单的更新时间
                order.setOrderSn(orders.get(i));
                order.setOrderStatus((short) 4);
                order.setShipSn(transitId1);
                order.setShipChannel(company1.getName());
                order.setUpdateTime(LocalDateTime.now());
                order.setShipTime(LocalDateTime.now());
                order.setFreightPrice(f);
                orderService.updateByOrderSn(order);
            }
            transportService.add(companyId, licensePlateNumber, thirdOrder, transitId1, driver, freight, createUser);
        } catch (Exception ex) {
            throw ex;
        }
        return ResponseUtil.ok();
    }

    /*
    物流配送订单状态更新
     */
    public Object updateLogisticsOrderStatus(String transitId, int status) {
        return transportService.update(transitId, status);
    }

    /*
    物流订单的详情
     */
    public List<LitemallLogicsticsTransport> list(String orderId, String transitId, String companyId, String thirdOrder, String licensePlateNumber, int page, int limit) {
        return transportService.list(orderId, transitId, companyId, thirdOrder, licensePlateNumber, page, limit);
    }
}
