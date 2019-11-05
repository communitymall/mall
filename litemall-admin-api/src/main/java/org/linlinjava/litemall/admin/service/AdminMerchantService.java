package org.linlinjava.litemall.admin.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallMerchant;
import org.linlinjava.litemall.db.service.LitemallCommentService;
import org.linlinjava.litemall.db.service.LitemallMerchantService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AdminMerchantService {
    private final Log logger = LogFactory.getLog(AdminMerchantService.class);

    @Autowired
    private LitemallUserService userService;
    @Autowired
    private LitemallCommentService commentService;
    @Autowired
    private LogHelper logHelper;
    @Autowired
    private LitemallMerchantService merchantService;

    public Object list(Integer id, String merchantName, List<Integer> merchantStatusArray,
                       Integer page, Integer limit, String sort, String order) {

        List<LitemallMerchant> litemallMerchants = merchantService.querySelective(id, merchantName, merchantStatusArray, page, limit,
                sort, order);
        return ResponseUtil.okList(litemallMerchants);
    }

    public Object detail(Integer id) {
        LitemallMerchant merchant = merchantService.detail(id);
        return ResponseUtil.ok(merchant);
    }

    public Object audit(Integer id,Integer merchantStatus) {
        Integer audit = merchantService.audit(id, merchantStatus);
        if(audit==1){
            return ResponseUtil.ok();
        }else {
            return ResponseUtil.fail();
        }
    }

}
