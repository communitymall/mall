package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import org.linlinjava.litemall.db.dao.LitemallLogicsticsTransportMapper;
import org.linlinjava.litemall.db.dao.LitemallLogisticsTrucksMapper;
import org.linlinjava.litemall.db.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LitemallLogisticsTrucksService {
    @Resource
    private LitemallLogisticsTrucksMapper trucksMapper;

    @Resource
    LitemallLogicsticsTransportMapper transportMapper;
    /*
    添加车辆的信息
     */
    public void  add(LitemallLogisticsTrucks litemallLogisticsTrucks){
        trucksMapper.insertSelective(litemallLogisticsTrucks);
    }
    /*
    修改车辆的信息
    修改车牌号时需检测最近一个月定单是否有物流配送，否则不允许修改
     */
    public int update(LitemallLogisticsTrucks trucks){
        //获得车牌号
        String licensePlateNumber = trucks.getLicensePlateNumber();
        //最近一个月定单是否有物流配送
        LitemallLogicsticsTransportExample example = new LitemallLogicsticsTransportExample();
        LitemallLogicsticsTransportExample.Criteria criteria = example.createCriteria();
        criteria.andLicensePlateNumberEqualTo(licensePlateNumber);
        String sort = "create_time";
        String order = "desc";
        example.setOrderByClause(sort + " " + order);
        List<LitemallLogicsticsTransport> litemallLogicsticsTransports = transportMapper.selectByExample(example);
        if(litemallLogicsticsTransports.size()==0){
            //没有订单信息  不允许需改
            System.out.println("------没有订单不能修改----------------------------");
            return 0;
        }
        //获得最近创建的订单信息
        LitemallLogicsticsTransport litemallLogicsticsTransport1 = litemallLogicsticsTransports.get(0);
        //获得创建的时间
        LocalDateTime createTime = litemallLogicsticsTransport1.getCreateTime();
        //获得现在的时间
        LocalDateTime now = LocalDateTime.now();
        //比较时间差
        long between = ChronoUnit.DAYS.between(now, createTime);
        //最近30天没有派送订单，不能修改
        if(between>30){
            System.out.println(between);
            System.out.println("------------------------------------------------");
            return 1;
        }

        LitemallLogicsticsTransport litemallLogicsticsTransport = new LitemallLogicsticsTransport();


       return trucksMapper.updateByPrimaryKey(trucks);
    }

    public List<LitemallLogisticsTrucks> querySelective(String companyId,String id, String licensePlateNumber,String driver,String phone,int page,int limit){
        LitemallLogisticsTrucksExample example = new LitemallLogisticsTrucksExample();
        LitemallLogisticsTrucksExample.Criteria criteria = example.createCriteria();
        //进行判断
        if(StringUtil.isEmpty(companyId)){

        }
        if(StringUtil.isEmpty(id)){

        }
        PageHelper.startPage(page, limit);
        
        return trucksMapper.selectByExample(example);
    }

    public Object deleteByPrimaryKey(String id){
        int i = Integer.parseInt(id);
        //trucksMapper.updateByExampleSelective();

        return trucksMapper.deleteByPrimaryKey(i);
    }

    /*
    根据车牌号查询
     */

    public  List<LitemallLogisticsTrucks> querySelectiveByLinNumber(String licenseNumber){
        LitemallLogisticsTrucksExample example = new LitemallLogisticsTrucksExample();
        LitemallLogisticsTrucksExample.Criteria criteria = example.createCriteria();
        criteria.andLicensePlateNumberEqualTo(licenseNumber);
        List<LitemallLogisticsTrucks> litemallLogisticsTrucks = trucksMapper.selectByExample(example);
        return litemallLogisticsTrucks;
    }
}
