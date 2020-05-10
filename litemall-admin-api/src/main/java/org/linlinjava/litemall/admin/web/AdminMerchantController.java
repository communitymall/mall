package org.linlinjava.litemall.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.linlinjava.litemall.admin.annotation.RequiresPermissionsDesc;
import org.linlinjava.litemall.admin.service.AdminMerchantService;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.validator.Order;
import org.linlinjava.litemall.core.validator.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/merchant")
@Validated
public class AdminMerchantController {
    private final Log logger = LogFactory.getLog(AdminMerchantController.class);

    @Autowired
    private AdminMerchantService merchantService;

    /**
     * 管理门店
     *
     * @param id
     * @param merchantName
     * @param merchantStatusArray
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:merchant:list")
    @RequiresPermissionsDesc(menu = {"商场管理", "门店管理"}, button = "查询")
    @GetMapping("/list")
    public Object list(Integer id, String merchantName,
                       @RequestParam(required = false) List<Integer> merchantStatusArray,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "id") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        return merchantService.list(id, merchantName, merchantStatusArray, page, limit, sort, order);
    }

    /**
     * 门店详情
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:merchant:read")
    @RequiresPermissionsDesc(menu = {"商场管理", "门店管理"}, button = "详情")
    @GetMapping("/detail")
    public Object detail(@NotNull Integer id) {
        return merchantService.detail(id);
    }

    /**
     * 门店的审核
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:merchant:audit")
    @RequiresPermissionsDesc(menu = {"商场管理", "门店审核"}, button = "审核")
    @PostMapping("/audit")
    public Object audit(@RequestBody String body) {
        Integer id = JacksonUtil.parseInteger(body, "id");
        Integer merchantStatus = JacksonUtil.parseInteger(body, "merchantStatus");
        return merchantService.audit(id,merchantStatus);
    }
}
