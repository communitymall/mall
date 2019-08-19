package org.linlinjava.litemall.admin.web;

/*物流管理*/

import com.github.pagehelper.util.StringUtil;
import com.mysql.jdbc.util.ResultSetUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.AdminLogisticsService;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallLogicsticsTransport;
import org.linlinjava.litemall.db.domain.LitemallLogisticsCompany;
import org.linlinjava.litemall.db.domain.LitemallLogisticsTrucks;
import org.linlinjava.litemall.db.service.LitemallLogisticsCompanyService;
import org.linlinjava.litemall.db.service.LitemallLogisticsTrucksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/logistics")
@Validated
public class AdminLogisticsController {

    private final Log logger = LogFactory.getLog(AdminLogisticsController.class);
    @Autowired
    private LitemallLogisticsCompanyService companyService;

    @Autowired
    private LitemallLogisticsTrucksService trucksService;

    @Autowired
    private AdminLogisticsService adminLogisticsService;
    /*公司管理更新、新增*/
    @RequiresPermissions("admin:logistics:add")
    @RequiresPermissionsDesc(menu = {"物流管理","物流公司管理"},button = "新增")
    @RequestMapping("/addCompany")
    @ResponseBody
    public Object addLogisticsCompany(HttpServletRequest request,
                                      @RequestParam(value = "name") String name,
                                      @RequestParam(value = "type") int type,
                                      @RequestParam(value = "address") String address,
                                      @RequestParam(value = "phone") String phone,
                                      @RequestParam(value = "contact") String contact,
                                      @RequestParam(value = "serviceTel",required = false) String serviceTel){
        LitemallLogisticsCompany company = new LitemallLogisticsCompany();
        Short b= (short) type;
        company.setName(name);
        company.setAddress(address);
        company.setPhone(phone);
        company.setContact(contact);
        company.setLogisticsType(b);
        company.setServiceTel(serviceTel);
        company.setCreateUser("测试");
        companyService.add(company);
        return ResponseUtil.ok();
    }

    @RequiresPermissions("admin:logistics:update")
    @RequiresPermissionsDesc(menu = {"物流管理","物流公司管理"},button = "修改")
    @RequestMapping("/updateCompany")
    @ResponseBody
    public Object updateLogisticsCompany(HttpServletRequest request,
                                         @RequestParam(value = "name") String name,
                                         @RequestParam(value = "type") int type,
                                         @RequestParam(value = "address") String address,
                                         @RequestParam(value = "phone") String phone,
                                         @RequestParam(value = "contact") String contact,
                                         @RequestParam(value = "serviceTel",required = false) String serviceTel,
                                         @RequestParam(value = "id") long id){
        LitemallLogisticsCompany company = new LitemallLogisticsCompany();
        company.setName(name);
        company.setLogisticsType((short)type);
        company.setAddress(address);
        company.setPhone(phone);
        company.setContact(contact);
        company.setServiceTel(serviceTel);
        company.setId((int)id);
        //companyService.updateById(company);
        companyService.updateById(company);
        return ResponseUtil.ok();
    }

    /*当id为空时，返回全部列表，否则返回指定的id详情*/
    @RequiresPermissions("admin:logistics:list")
    @RequiresPermissionsDesc(menu ={"物流管理","物流公司管理"},button = "详情")
    @RequestMapping("/listCompany")
    @ResponseBody
    public Object logisticsCompanyList(HttpServletRequest request,
                                       @RequestParam(value = "id", required = false)String id,
                                       @RequestParam(value = "name", required = false)String name,
                                       @RequestParam(value = "page" ,defaultValue = "1") int page,
                                       @RequestParam(value = "limit", defaultValue = "10") int limit){

        return ResponseUtil.okList(companyService.querySelective(id,name,page,limit));
    }

    /*删除物流公司时，需验证此物流公司是否使用，当前自有物流需验证是否挂有车辆信息*/
    @RequiresPermissions("admin:logistics:del")
    @RequiresPermissionsDesc(menu ={"物流管理","物流公司管理"},button = "删除")
    @RequestMapping("/delCompany")
    @ResponseBody
    public Object delLogisticsCompany(HttpServletRequest request,
                                       @RequestParam(value = "id", required = false) String id,
                                       @RequestParam(value = "name", required = false)String name){
        if(StringUtil.isEmpty(id)||StringUtil.isEmpty(name)){
            return ResponseUtil.fail(402,"没有物流公司编号与物流公司名称");
        }
        companyService.deleteOne(id,name);
        return ResponseUtil.ok();
    }


    /*车辆管理 更新 新增 逻辑删除*/
    @RequiresPermissions("admin:logistics:addtruck")
    @RequiresPermissionsDesc(menu ={"物流管理","车辆管理"},button = "新增")
    @RequestMapping("/addTruck")
    @ResponseBody
    public Object addTruck(HttpServletRequest request,
                           @RequestParam(value = "companyId") long comanpyId,
                           @RequestParam(value = "licensePlateNumber") String licensePlateNumber,
                           @RequestParam(value = "driver") String driver,
                           @RequestParam(value = "phone") String phone,
                           @RequestParam(value = "load") String load
                           ){
        LitemallLogisticsTrucks truck = new LitemallLogisticsTrucks();
        truck.setCompanyId((int)comanpyId);
        truck.setLicensePlateNumber(licensePlateNumber);
        truck.setDriver(driver);
        truck.setPhone(phone);
        truck.setLoad(load);
        truck.setCreateTime(LocalDateTime.now());
        trucksService.add(truck);
        return ResponseUtil.ok();
    }

    /*修改车牌号时需检测最近一个月定单是否有物流配送，否则不允许修改*/
    @RequiresPermissions("admin:logistics:updatetruck")
    @RequiresPermissionsDesc(menu ={"物流管理","车辆管理"},button = "更新")
    @RequestMapping("/updateTruck")
    @ResponseBody
    public Object updateTruck(HttpServletRequest request,
                              @RequestParam(value = "companyId") long comanpyId,
                              @RequestParam(value = "id") long id,
                              @RequestParam(value = "licensePlateNumber") String licensePlateNumber,
                              @RequestParam(value = "driver") String driver,
                              @RequestParam(value = "phone") String phone,
                              @RequestParam(value = "load") String load){
        LitemallLogisticsTrucks truck = new LitemallLogisticsTrucks();
        truck.setCompanyId((int)comanpyId);
        truck.setId((int)id);
        truck.setLicensePlateNumber(licensePlateNumber);
        truck.setDriver(driver);
        truck.setPhone(phone);
        truck.setLoad(load);
        int update = trucksService.update(truck);
        if(update==0){
            return ResponseUtil.fail(499,"没有物流派送，不能修改");
        }
        if(update==1){
            return ResponseUtil.fail(489,"物流订单没有超过一个月，不能修改");
        }
        return trucksService.update(truck);
    }

    /*逻辑删除*/
    @RequiresPermissions("admin:logistics:deltruck")
    @RequiresPermissionsDesc(menu ={"物流管理","车辆管理"},button = "删除")
    @RequestMapping("/delTruck")
    @ResponseBody
    public Object delTruck(HttpServletRequest request,
                           @RequestParam(value = "companyId") String comanpyId,
                           @RequestParam(value = "id") String id,
                           @RequestParam(value = "licensePlateNumber") String licensePlateNumber
                            ){
        return trucksService.deleteByPrimaryKey(id);
    }

    @RequiresPermissions("admin:logistics:listtruck")
    @RequiresPermissionsDesc(menu ={"物流管理","车辆管理"},button = "详情")
    @RequestMapping("/listTruck")
    @ResponseBody
    public Object truckList(HttpServletRequest request,
                            @RequestParam(value = "companyId" ,required = false) String companyId,
                            @RequestParam(value = "id" ,required = false) String id,
                            @RequestParam(value = "licensePlateNumber", required = false) String licensePlateNumber,
                            @RequestParam(value = "driver",required = false) String driver,
                            @RequestParam(value = "phone", required = false) String phone,
                            @RequestParam(value = "page",defaultValue = "1") int page,
                            @RequestParam(value = "limit", defaultValue = "10") int limit){

        return ResponseUtil.okList(trucksService.querySelective(companyId,id,licensePlateNumber,driver,phone,page,limit));
    }



    /*物流订单管理 订单生成、订单状态更新、物流定单状态*/
    @RequiresPermissions("admin:logistics:createOrder")
    @RequiresPermissionsDesc(menu = {"物流管理","物流配送"},button = "创建")
    @ResponseBody
    @RequestMapping("/createOrder")
    public Object createLogisticsOrder(HttpServletRequest request,
                                       @RequestBody String body){
        Object transitId = adminLogisticsService.addOrder(body);
//        Map<Object, Object> data = new HashMap<String, Object>();
//        data.put(transitId,"物流配送订单");
        List list = new ArrayList();
        list.add(transitId);
        return ResponseUtil.transportOk(list);
    }


    @ResponseBody
    @RequestMapping("/updateOrderStatus")
    public Object updateOrderStatus(HttpServletRequest request,
                                    @RequestParam(value = "orderId") String orderId,
                                    @RequestParam(value = "status") int status){
        return null;
    }

    @ResponseBody
    @RequestMapping("/updateLogisticsOrderStatus")
    public Object updateLogisticsOrderStatus(HttpServletRequest request,
                                             @RequestParam(value = "transitId") String transitId,
                                             @RequestParam(value = "status") int status){
        adminLogisticsService.updateLogisticsOrderStatus(transitId,status);
        return ResponseUtil.ok();
    }

    @RequiresPermissions("admin:logistics:queryorderlist")
    @RequiresPermissionsDesc(menu = {"物流管理","物流进度"},button = "查询")
    @ResponseBody
    @RequestMapping("/queryOrderList")
    public Object logisticsOrderList(HttpServletRequest request,
                                     @RequestParam(value = "orderId", required = false) String order,
                                     @RequestParam(value = "transitId", required = false) String transitId,
                                     @RequestParam(value = "companyId", required = false) String companyId,
                                     @RequestParam(value = "thirdOrder", required = false) String ThirdOrder,
                                     @RequestParam(value = "licensePlateNumber", required = false) String licensePlateNumber,
                                     @RequestParam(value = "page",defaultValue = "1") int page,
                                     @RequestParam(value = "limit",defaultValue = "10") int limit){
        List<LitemallLogicsticsTransport> list = adminLogisticsService.list(order,transitId,companyId,ThirdOrder,licensePlateNumber,page,limit);
        return ResponseUtil.okList(list);
    }


    @RequiresPermissions("admin:logistics:queryTransit")
    @RequiresPermissionsDesc(menu = {"物流管理","物流进度"},button = "详情")
    @ResponseBody
    @RequestMapping("/queryTransit")
    public Object logisticsOrderDetail(HttpServletRequest request,
                                     @RequestParam(value = "transitId", required = false) String transitId){
        //List<LitemallLogicsticsTransport> list = adminLogisticsService.list(transitId);
        return null;
    }
}
