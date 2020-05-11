package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.AdminOrderService;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/order")
@Validated
public class AdminOrderController {
    private final Log logger = LogFactory.getLog(AdminOrderController.class);

    @Autowired
    private AdminOrderService adminOrderService;

    /**
     * 查询订单
     *
     * @param userId
     * @param orderSn
     * @param orderStatusArray
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:order:list")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(String mobile, String orderSn,String name,
                       @RequestParam(required = false) List<Short> orderStatusArray,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return adminOrderService.list(mobile,name, orderSn, orderStatusArray, page, limit, sort, order);
    }

    /**
     * 订单详情
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:order:read")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "详情")
    @GetMapping("/detail")
    public Object detail(@NotNull Integer id) {
        return adminOrderService.detail(id);
    }

    /**
     * 订单退款
     *
     * @param body 订单信息，{ orderId：xxx }
     * @return 订单退款操作结果
     */
    @RequiresPermissions("admin:order:refund")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "订单退款")
    @PostMapping("/refund")
    public Object refund(@RequestBody String body) {
        return adminOrderService.refund(body);
    }

    /**
     * 发货
     *
     * @param body 订单信息，{ orderId：xxx, shipSn: xxx, shipChannel: xxx }
     * @return 订单操作结果
     */
    @RequiresPermissions("admin:order:ship")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "订单发货")
    @PostMapping("/ship")
    public Object ship(@RequestBody String body) {
        return adminOrderService.ship(body);
    }


    /**
     * 回复订单商品
     *
     * @param body 订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @RequiresPermissions("admin:order:reply")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "订单商品回复")
    @PostMapping("/reply")
    public Object reply(@RequestBody String body) {
        return adminOrderService.reply(body);
    }


    /**
     * 货到付款订单的审核
     *
     * @param body 订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @RequiresPermissions("admin:order:approved")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "订单审核")
    @PostMapping("/approved")
    public Object approved(@RequestBody String body){return adminOrderService.approved(body);}

    /**
     * 货到付款订单的未通过审核
     *
     * @param body 订单信息，{ orderId：xxx }
     * @return 订单操作结果
     */
    @RequiresPermissions("admin:order:unApproved")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "订单审核")
    @PostMapping("/unApproved")
    public Object unApproved(@RequestBody String body){return adminOrderService.unApproved(body);}


    /*
    订单备货完成
     */
    @RequiresPermissions("admin:order:completeGoods")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "备货完成")
    @PostMapping("/completeGoods")
    public Object completeGoods(@RequestBody String body){return adminOrderService.completeGoods(body);}

     /*
    查询所有备货完成，可以发货的订单
     */
    @RequiresPermissions("admin:order:checkDeliveryOrder")
    @RequiresPermissionsDesc(menu = {"商场管理", "订单管理"}, button = "发货")
    @GetMapping("/checkDeliveryOrder")
    public Object checkDeliveryOrder(Integer userId, String orderSn,
                       @RequestParam(required = false) List<Short> orderStatusArray,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return adminOrderService.checkDeliveryOrder(userId, orderSn, orderStatusArray, page, limit, sort, order);
    }
}
