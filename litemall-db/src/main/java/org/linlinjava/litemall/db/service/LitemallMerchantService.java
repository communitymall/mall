package org.linlinjava.litemall.db.service;

import com.github.pagehelper.util.StringUtil;
import org.linlinjava.litemall.db.dao.LitemallMerchantMapper;
import org.linlinjava.litemall.db.dao.LitemallSUMapper;
import org.linlinjava.litemall.db.dao.LitemallUserMapper;
import org.linlinjava.litemall.db.dao.LitemallUserStoreMapper;
import org.linlinjava.litemall.db.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/*
商户门店的操作
 */
@Service
public class LitemallMerchantService {
    @Resource
    private LitemallMerchantMapper litemallMerchantMapper;

    @Resource
    private LitemallUserStoreMapper litemallUserStoreMapper;

    @Resource
    private LitemallUserMapper litemallUserMapper;

    @Resource
    private LitemallSUMapper litemallSUMapper;

    /*
    添加商户的门店
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
    public Object add(String userId, String merchantAddress, String merchantCode, String merchantLeader, String merchantName, String merchantPhone, String merchantPic) {
        try {
            LitemallMerchant merchant = new LitemallMerchant();
            LitemallUserStore userStore = new LitemallUserStore();
            //创建门店的记录
            merchant.setCreateTime(LocalDateTime.now());
            merchant.setCreateDate(LocalDate.now());
            if (!StringUtil.isEmpty(merchantAddress)) {
                merchant.setMerchantAddress(merchantAddress);
            }
            if (!StringUtil.isEmpty(merchantCode)) {
                merchant.setMerchantCode(merchantCode);
            }
            if (!StringUtil.isEmpty(merchantLeader)) {
                merchant.setMerchantLeader(merchantLeader);
            }
            if (!StringUtil.isEmpty(merchantName)) {
                merchant.setMerchantName(merchantName);
            }
            if (!StringUtil.isEmpty(merchantPhone)) {
                merchant.setMerchantPhone(merchantPhone);
            }
            if (!StringUtil.isEmpty(merchantPic)) {
                merchant.setMerchantPic(merchantPic);
            }
            litemallMerchantMapper.insertSelective(merchant);//添加门店的信息
            Integer storeId = merchant.getId();

            userStore.setCreateTime(LocalDateTime.now());
            userStore.setStoreId(Long.parseLong(storeId.toString()));
            userStore.setUserId(Long.parseLong(String.valueOf(userId)));
            userStore.setRoleType((short) 0);
            litemallUserStoreMapper.insertSelective(userStore);


            return storeId;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /*
    更新商户的门店
     */
    public Object update(int id, String merchantName, String merchantCode, String merchantAddress, String merchantPic, String merchantPhone, String merchantLeader) {
        LitemallMerchant merchant = new LitemallMerchant();
        LitemallMerchantExample example = new LitemallMerchantExample();
        LitemallMerchantExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        if (!StringUtil.isEmpty(merchantName)) {
            merchant.setMerchantName(merchantName);
        }
        if (!StringUtil.isEmpty(merchantCode)) {
            merchant.setMerchantCode(merchantCode);
        }
        if (!StringUtil.isEmpty(merchantAddress)) {
            merchant.setMerchantAddress(merchantAddress);
        }
        if (!StringUtil.isEmpty(merchantPic)) {
            merchant.setMerchantPic(merchantPic);
        }
        if (!StringUtil.isEmpty(merchantPhone)) {
            merchant.setMerchantPhone(merchantPhone);
        }
        if (!StringUtil.isEmpty(merchantLeader)) {
            merchant.setMerchantLeader(merchantLeader);
        }
        //设置更新时间
        merchant.setEditTime(LocalDateTime.now());
        return litemallMerchantMapper.updateByExampleSelective(merchant, example);
    }

    /*
    添加店员的信息
     */
    public Object addRelation(String mobile, String password, String name, String storeId, String roleType) {
        //给user表添加数据 返回userid
        LitemallUser user = new LitemallUser();
        user.setMobile(mobile);
        user.setName(name);
        user.setPassword(password);
        //设置账号可用
        byte status = 0;
        user.setStatus(status);
        user.setAddTime(LocalDateTime.now());
        litemallUserMapper.insertSelective(user);
        Integer userId = user.getId();

        LitemallUserStore userStore = new LitemallUserStore();
        userStore.setUserId(userId.longValue());
        userStore.setStoreId(Long.parseLong(storeId));
        userStore.setRoleType(Short.parseShort(roleType));
        userStore.setCreateTime(LocalDateTime.now());
        litemallUserStoreMapper.insertSelective(userStore);
        return null;
    }

    /*
    查询所有商户的门店
     */
    public List<LitemallMerchant> list(String userId) {
        return litemallMerchantMapper.selectByOwnWay(Integer.parseInt(userId));
    }

    /*
    查询用户门店的具体信息
     */
    public LitemallMerchant detail(Integer id) {

        return litemallMerchantMapper.selectByPrimaryKey(id);
    }

    /*
   查询商户的门店中所有的店员
    */
    public List<LitemallStoreUser> listUserStore(String storeId) {
        return litemallSUMapper.selectByOWay(Integer.parseInt(storeId));
    }

    /*
  修改商户的门店中店员的信息
   */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
    public Object updateUserStore(String userId, String name, Integer roleType) {
        try {
            System.out.println(userId);
            System.out.println(name);
            System.out.println(roleType);
            LitemallUser user = new LitemallUser();
            user.setName(name);
            user.setId(Integer.parseInt(userId));
            LitemallUserExample userExample = new LitemallUserExample();
            LitemallUserExample.Criteria criteria1 = userExample.createCriteria();
            criteria1.andIdEqualTo(Integer.parseInt(userId));
            litemallUserMapper.updateByExampleSelective(user, userExample);//修改user表中的信息

            LitemallUserStore userStore = new LitemallUserStore();
            userStore.setRoleType(Short.parseShort(String.valueOf(roleType)));
            LitemallUserStoreExample example = new LitemallUserStoreExample();
            LitemallUserStoreExample.Criteria criteria = example.createCriteria();
            criteria.andUserIdEqualTo(Long.parseLong(userId));
            litemallUserStoreMapper.updateByExampleSelective(userStore, example);//修改userStore表中的信息
            return null;
        } catch (Exception e) {
            throw e;
        }
    }

    /*
     /*
   查找门店创建人的信息
    */
    public LitemallMerchant findMerchantLeader(String storeId) {
        try {
            return litemallMerchantMapper.selectByPrimaryKey(Integer.parseInt(storeId));
        } catch (Exception e) {
            throw e;
        }
    }

    /*
    添加新的店员
     */
    public Object addMerchantUser(String storeId, String name, String mobile, Integer roleType) {
        try {
            LitemallUser user = new LitemallUser();
            user.setMobile(mobile);
            user.setName(name);
            user.setAddTime(LocalDateTime.now());
            litemallUserMapper.insertSelective(user);
            Integer userId = user.getId();

            LitemallUserStore userStore = new LitemallUserStore();
            userStore.setRoleType(Short.parseShort(String.valueOf(roleType)));
            userStore.setStoreId(Long.parseLong(storeId));
            userStore.setUserId(Long.parseLong(String.valueOf(userId)));
            userStore.setCreateTime(LocalDateTime.now());
            litemallUserStoreMapper.insertSelective(userStore);
            return  null;
        } catch (Exception e) {
            throw e;
        }
    }
}
