package org.linlinjava.litemall.wx.web;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.JacksonUtil;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallMerchant;
import org.linlinjava.litemall.db.service.LitemallMerchantService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*对门店的管理*/
@RestController
@RequestMapping("wx/store")
@Validated
public class WxStoreController {
    private final Log logger = LogFactory.getLog(WxStoreController.class);
    @Autowired
    private LitemallMerchantService litemallMerchantService;

    @PostMapping("/update")
    @ResponseBody
    public Object update(HttpServletRequest request,
                             @RequestBody String  body){
        Integer id = JacksonUtil.parseInteger(body, "id");
        String merchantName = JacksonUtil.parseString(body, "merchantName");
        String merchantCode = JacksonUtil.parseString(body, "merchantCode");
        String merchantAddress = JacksonUtil.parseString(body, "merchantAddress");
        String merchantPic = JacksonUtil.parseString(body, "merchantPic");
        String merchantPhone = JacksonUtil.parseString(body, "merchantPhone");
        String merchantLeader = JacksonUtil.parseString(body, "merchantLeader");
        litemallMerchantService.update(id, merchantName, merchantCode, merchantAddress, merchantPic, merchantPhone, merchantLeader);
        return ResponseUtil.ok();

    }

    @PostMapping("/create")
    @ResponseBody
    public Object create(HttpServletRequest request, @RequestBody String  body ){
        String merchantAddress = JacksonUtil.parseString(body, "merchantAddress");
        String merchantCode = JacksonUtil.parseString(body, "merchantCode");
        String merchantLeader = JacksonUtil.parseString(body, "merchantLeader");
        String merchantName = JacksonUtil.parseString(body, "merchantName");
        String merchantPhone = JacksonUtil.parseString(body, "merchantPhone");
        String merchantPic = JacksonUtil.parseString(body, "merchantPic");
        String userId = JacksonUtil.parseString(body, "userId");
        Object storeId = litemallMerchantService.add(userId,merchantAddress,merchantCode,merchantLeader,merchantName,merchantPhone,merchantPic);
        return ResponseUtil.createStoreOk(storeId);
    }

    @PostMapping("/addRelation")
    @ResponseBody
    public Object addRelation(HttpServletRequest request,
                              @RequestBody String body){
        String mobile = JacksonUtil.parseString(body, "mobile");
        String password = JacksonUtil.parseString(body, "password");
        String name = JacksonUtil.parseString(body, "name");
        String storeid = JacksonUtil.parseString(body, "storeid");
        String roleType = JacksonUtil.parseString(body, "roleType");
        litemallMerchantService.addRelation(mobile,password,name,storeid,roleType);
        return ResponseUtil.ok();
    }

    @PostMapping("/list")
    @ResponseBody
    public Object listUser(HttpServletRequest request,@LoginUser Integer userId){
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        List<LitemallMerchant> list = litemallMerchantService.list(String.valueOf(userId));
        return ResponseUtil.okList(list);
    }

    /*
    获得用户门店的详情
     */
    @PostMapping("/detail")
    @ResponseBody
    public Object storeDetail( @RequestBody String body){
        String storeId = JacksonUtil.parseString(body, "storeId");
        if (storeId == null) {
            return ResponseUtil.fail(409,"未知的错误");
        }
        LitemallMerchant detail = litemallMerchantService.detail(Integer.parseInt(storeId));
        return ResponseUtil.ok(detail);
    }

    /*
    用户查询门店中员工的信息
     */
    @PostMapping("/listUserStore")
    @ResponseBody
    public Object listUserStore(HttpServletRequest request, @RequestBody String body){
        String storeId = JacksonUtil.parseString(body, "storeId");
        return ResponseUtil.ok(litemallMerchantService.listUserStore(storeId));
    }

    /*
    修改门店中店员的信息
     */
    @PostMapping("/updateUserStore")
    @ResponseBody
    public Object updateUserStore(HttpServletRequest request, @RequestBody String body){
        String userId = JacksonUtil.parseString(body, "usId");
        String name = JacksonUtil.parseString(body, "name");
        Integer roleType = JacksonUtil.parseInteger(body, "roleType");
        if(StringUtil.isEmpty(userId)){
            return ResponseUtil.fail();
        }
        if(StringUtil.isEmpty(name)){
            return ResponseUtil.fail();
        }
        if(StringUtil.isEmpty(String.valueOf(roleType))){
            return ResponseUtil.fail();
        }
        litemallMerchantService.updateUserStore(userId,name,roleType);
        return ResponseUtil.ok();
    }

    /*
    查找门店创建人的信息
     */
    @PostMapping("/findMerchantLeader")
    @ResponseBody
    public Object findMerchantLeader(HttpServletRequest request, @RequestBody String body){
        String storeId = JacksonUtil.parseString(body, "storeId");
        if(StringUtil.isEmpty(storeId)){
            return ResponseUtil.fail();
        }
        return ResponseUtil.ok(litemallMerchantService.findMerchantLeader(storeId));
    }

    /*
    添加新的店员
     */
    @PostMapping("/addMerchantUser")
    @ResponseBody
    public Object addMerchantUser(HttpServletRequest request, @RequestBody String body){
        String storeId = JacksonUtil.parseString(body, "storeId");
        String name = JacksonUtil.parseString(body, "name");
        String mobile = JacksonUtil.parseString(body, "mobile");
        Integer roleType = JacksonUtil.parseInteger(body, "roleType");
        if(StringUtil.isEmpty(storeId)){
            return ResponseUtil.fail();
        }
        return ResponseUtil.ok(litemallMerchantService.addMerchantUser(storeId,name,mobile,roleType));
    }
}
