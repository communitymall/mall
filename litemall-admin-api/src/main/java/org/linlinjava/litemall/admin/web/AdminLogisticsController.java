package org.linlinjava.litemall.admin.web;

/*物流管理*/

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/logistics")
@Validated
public class AdminLogisticsController {

    /*公司管理更新、新增*/

    public Object addLogisticsCompany(){
        return null;
    }

    public Object updateLogisticsCompany(){
        return null;
    }

    /*车辆管理 更新 新增 逻辑删除*/

    public Object addTruck(){
        return null;
    }

    public Object updateTruck(){
        return null;
    }

    public Object delTruck(){
        return null;
    }

    public Object truckList(){
        return null;
    }

    
    /*物流订单管理 订单生成、订单状态更新、物流定单状态*/
}
