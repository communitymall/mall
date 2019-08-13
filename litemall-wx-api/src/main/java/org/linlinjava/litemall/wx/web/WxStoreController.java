package org.linlinjava.litemall.wx.web;
import org.linlinjava.litemall.db.domain.LitemallMerchant;
import org.linlinjava.litemall.db.domain.LitemallUserStore;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/*对门店的管理*/
@RestController
@RequestMapping("wx/store")
@Validated
public class WxStoreController {

    @PostMapping("/update")
    public Object update(HttpServletRequest request,
                             @RequestBody LitemallMerchant merchant){

        return null;
    }

    @PostMapping("/addRelation")
    public Object addRelation(HttpServletRequest request,
                              @RequestBody LitemallUserStore userStore){
        return null;
    }
}
