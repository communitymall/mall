package org.linlinjava.litemall.db.service;

import com.github.pagehelper.util.StringUtil;
import org.linlinjava.litemall.db.dao.LitemallLogicticsTransportDetailMapper;
import org.linlinjava.litemall.db.domain.LitemallLogicticsTransportDetail;
import org.linlinjava.litemall.db.domain.LitemallLogicticsTransportDetailExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LitemallLogicticsTransportDetailService {
    @Resource
    private LitemallLogicticsTransportDetailMapper detailMapper;

    LitemallLogicticsTransportDetail detail = new LitemallLogicticsTransportDetail();

    public int add(String orderId,int status){
        int transitId = UUID.randomUUID().hashCode();
        detail.setOrderSn(orderId);
        detail.setTransitId(String.valueOf(transitId));
        detail.setShipStatus(String.valueOf(status));
        String ctime = String.valueOf(LocalDateTime.now());
        detail.setCreateTime(ctime);
        //判断orderid在数据是否存在
        LitemallLogicticsTransportDetailExample example = new LitemallLogicticsTransportDetailExample();
        LitemallLogicticsTransportDetailExample.Criteria criteria = example.createCriteria();
        criteria.andOrderSnEqualTo(orderId);
        List<LitemallLogicticsTransportDetail> details = detailMapper.selectByExampleSelective(example);
        if(details.size()>0){
            return -1;
        }
        return detailMapper.insertSelective(detail);
    }

    /*
    获取物流配送详情
     */
    public List list(String transitId){
        LitemallLogicticsTransportDetailExample example = new LitemallLogicticsTransportDetailExample();
        LitemallLogicticsTransportDetailExample.Criteria criteria = example.createCriteria();
        if(!StringUtil.isEmpty(transitId)){
            criteria.andTransitIdEqualTo(transitId);
        }

        return detailMapper.selectByExample(example);
    }
}
