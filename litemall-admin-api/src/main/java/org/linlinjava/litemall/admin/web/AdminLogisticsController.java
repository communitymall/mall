package org.linlinjava.litemall.admin.web;

/*物流管理*/

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/logistics")
@Validated
public class AdminLogisticsController {

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
        return null;
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
        return null;
    }

    /*当id为空时，返回全部列表，否则返回指定的id详情*/
    @RequiresPermissions("admin:logistics:list")
    @RequiresPermissionsDesc(menu ={"物流管理","物流公司管理"},button = "详情")
    @RequestMapping("/listCompany")
    @ResponseBody
    public Object logisticsCompanyList(HttpServletRequest request,
                                       @RequestParam(value = "id", required = false) long id,
                                       @RequestParam(value = "name", required = false)String name,
                                       @RequestParam(value = "page" ,defaultValue = "1") int page,
                                       @RequestParam(value = "limit", defaultValue = "10") int limit){
        return null;
    }

    /*删除物流公司时，需验证此物流公司是否使用，当前自有物流需验证是否挂有车辆信息*/
    @RequiresPermissions("admin:logistics:del")
    @RequiresPermissionsDesc(menu ={"物流管理","物流公司管理"},button = "删除")
    @RequestMapping("/delCompany")
    @ResponseBody
    public Object delLogisticsCompany(HttpServletRequest request,
                                       @RequestParam(value = "id", required = false) long id,
                                       @RequestParam(value = "name", required = false)String name){
        return null;
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
        return null;
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
        return null;
    }

    /*逻辑删除*/
    @RequiresPermissions("admin:logistics:deltruck")
    @RequiresPermissionsDesc(menu ={"物流管理","车辆管理"},button = "删除")
    @RequestMapping("/delTruck")
    @ResponseBody
    public Object delTruck(HttpServletRequest request,
                           @RequestParam(value = "companyId") long comanpyId,
                           @RequestParam(value = "id") long id,
                           @RequestParam(value = "licensePlateNumber") String licensePlateNumber
                            ){
        return null;
    }

    @RequiresPermissions("admin:logistics:listtruck")
    @RequiresPermissionsDesc(menu ={"物流管理","车辆管理"},button = "详情")
    @RequestMapping("/listTruck")
    @ResponseBody
    public Object truckList(HttpServletRequest request,
                            @RequestParam(value = "companyId" ,required = false) long comanpyId,
                            @RequestParam(value = "id" ,required = false) long id,
                            @RequestParam(value = "licensePlateNumber", required = false) String licensePlateNumber,
                            @RequestParam(value = "driver",required = false) String driver,
                            @RequestParam(value = "phone", required = false) String phone,
                            @RequestParam(value = "page",defaultValue = "1") int page,
                            @RequestParam(value = "limit", defaultValue = "10") int limit){
        return null;
    }



    /*物流订单管理 订单生成、订单状态更新、物流定单状态*/
    @RequiresPermissions("admin:logistics:createOrder")
    @RequiresPermissionsDesc(menu = {"物流管理","物流配送"},button = "创建")
    @ResponseBody
    @RequestMapping("/createOrder")
    public Object createLogisticsOrder(HttpServletRequest request,
                                       @RequestBody String body){
        return null;
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
        return null;
    }

    @RequiresPermissions("admin:logistics:queryorderlist")
    @RequiresPermissionsDesc(menu = {"物流管理","物流进度"},button = "查询")
    @ResponseBody
    @RequestMapping("/queryOrderList")
    public Object logisticsOrderList(HttpServletRequest request,
                                     @RequestParam(value = "orderId", required = false) String order,
                                     @RequestParam(value = "transitId", required = false) String transitId,
                                     @RequestParam(value = "companyId", required = false) long companyId,
                                     @RequestParam(value = "thirdOrder", required = false) String ThirdOrder,
                                     @RequestParam(value = "licensePlateNumber", required = false) String licensePlateNumber,
                                     @RequestParam(value = "page",defaultValue = "1") int page,
                                     @RequestParam(value = "limit",defaultValue = "10") int limit){

        return null;
    }


    @RequiresPermissions("admin:logistics:queryTransit")
    @RequiresPermissionsDesc(menu = {"物流管理","物流进度"},button = "详情")
    @ResponseBody
    @RequestMapping("/queryTransit")
    public Object logisticsOrderDetail(HttpServletRequest request,
                                     @RequestParam(value = "transitId", required = false) String transitId){
        return null;
    }
}
