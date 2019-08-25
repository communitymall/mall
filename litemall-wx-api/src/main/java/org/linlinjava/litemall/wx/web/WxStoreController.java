package org.linlinjava.litemall.wx.web;
import org.linlinjava.litemall.db.domain.LitemallMerchant;
import org.linlinjava.litemall.db.domain.LitemallUserStore;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/*对门店的管理*/
@RestController
@RequestMapping("wx/store")
@Validated
public class WxStoreController {

    @PostMapping("/update")
    @ResponseBody
    public Object update(HttpServletRequest request,
                             @RequestBody LitemallMerchant merchant){

        return null;
    }

    @PostMapping("/create")
    @ResponseBody
    public Object create(HttpServletRequest request,
                         @RequestParam(value = "userId") String userId
                         ){
        return null;
    }

    @PostMapping("/addRelation")
    @ResponseBody
    public Object addRelation(HttpServletRequest request,
                              @RequestBody LitemallUserStore userStore){
        return null;
    }

    @PostMapping("/list")
    @ResponseBody
    public Object listUser(HttpServletRequest request,
                           @RequestParam(value = "storeid") String storeId){
        return null;
    }
}
