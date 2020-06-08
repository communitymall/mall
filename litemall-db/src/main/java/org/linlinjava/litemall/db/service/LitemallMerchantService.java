package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import org.linlinjava.litemall.db.dao.LitemallMerchantMapper;
import org.linlinjava.litemall.db.dao.LitemallSUMapper;
import org.linlinjava.litemall.db.dao.LitemallUserMapper;
import org.linlinjava.litemall.db.dao.LitemallUserStoreMapper;
import org.linlinjava.litemall.db.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    public Integer add(String userId, String merchantAddress, String merchantCode, String merchantLeader, String merchantName, String merchantPhone, String merchantPic) {
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
                merchant.setConsigneeName(merchantLeader);//创建门店时，设置默认收货人为负责人
            }
            if (!StringUtil.isEmpty(merchantName)) {
                LitemallMerchantExample example = new LitemallMerchantExample();
                LitemallMerchantExample.Criteria criteria = example.createCriteria();
                criteria.andMerchantNameEqualTo(merchantName);
                List<LitemallMerchant> litemallMerchants = litemallMerchantMapper.selectByExample(example);
                if(litemallMerchants.size()>=1){
                    return -1;
                }
                merchant.setMerchantName(merchantName);
            }
            if (!StringUtil.isEmpty(merchantPhone)) {
                merchant.setMerchantPhone(merchantPhone);
                merchant.setConsigneePhone(merchantPhone);//创建门店时，设置默认收货人联系方式为负责人联系方式
            }
            if (!StringUtil.isEmpty(merchantPic)) {
                merchant.setMerchantPic(merchantPic);
            }

            LitemallUserExample example  = new LitemallUserExample();
            LitemallUserExample.Criteria criteria = example.createCriteria();
            criteria.andMobileEqualTo(merchantPhone);
            List<LitemallUser> litemallUsers = litemallUserMapper.selectByExample(example);
            if(litemallUsers.size()==0){//数据库user中没有这个手机号
                LitemallUser user = new LitemallUser();
                user.setMobile(merchantPhone);
                user.setName(merchantLeader);
                user.setAddTime(LocalDateTime.now());
                Byte b=0;
                user.setStatus(b);
                litemallUserMapper.insertSelective(user);
                Integer id = user.getId();
                System.out.println("id="+id);

                merchant.setConsigneeId(id);//创建门店时，设置默认收货人为门店负责人的id
                userStore.setUserId(Long.parseLong(String.valueOf(id)));//设置门店负责人的id
            }else if(litemallUsers.size()==1){//
                LitemallUser user = litemallUsers.get(0);
                merchant.setConsigneeId(user.getId());
                userStore.setUserId(Long.parseLong(String.valueOf(user.getId())));//设置门店负责人的id
            }
            merchant.setConsigneePhone(merchantPhone);
            merchant.setConsigneeName(merchantLeader);
            merchant.setMerchantStatus(2); //门店创建的时候，设置门店的状态是审核

            litemallMerchantMapper.insertSelective(merchant);//添加门店的信息
            Integer storeId = merchant.getId();

            userStore.setCreateTime(LocalDateTime.now());
            userStore.setStoreId(Long.parseLong(storeId.toString()));

            userStore.setCreaterId(Integer.parseInt(userId));//设置创建人的id
            userStore.setRoleType((short) 3);
            litemallUserStoreMapper.insertSelective(userStore);
            return storeId;
        } catch (Exception ex) {
            throw ex;
        }
    }

    /*
    更新商户的门店
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
    public Integer update(Integer userId,int id, String merchantName, String merchantCode, String merchantAddress, String merchantPic, String merchantPhone, String merchantLeader) {
        LitemallMerchant merchant = new LitemallMerchant();
        LitemallMerchantExample example = new LitemallMerchantExample();
        LitemallMerchantExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);

        LitemallUser user = new LitemallUser();

        if (!StringUtil.isEmpty(merchantName)) {
            LitemallMerchantExample example1 = new LitemallMerchantExample();
            LitemallMerchantExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andIdEqualTo(id);
            List<LitemallMerchant> litemallMerchants = litemallMerchantMapper.selectByExample(example1);
            if(!(litemallMerchants.get(0).getMerchantName().equals(merchantName))){
                LitemallMerchantExample example2 = new LitemallMerchantExample();
                LitemallMerchantExample.Criteria criteria2 = example2.createCriteria();
                criteria2.andMerchantNameEqualTo(merchantName);
                List<LitemallMerchant> merchants = litemallMerchantMapper.selectByExample(example2);
                if(merchants.size()>=1){
                    return -1;
                }
            }
            merchant.setMerchantName(merchantName);
            merchant.setMerchantStatus(2);
        }
        if (!StringUtil.isEmpty(merchantCode)) {
            LitemallMerchant merchant1 = litemallMerchantMapper.selectByPrimaryKey(id);
            if(!(merchantCode.equals(merchant1.getMerchantCode()))){
                merchant.setMerchantCode(merchantCode);
                merchant.setMerchantStatus(2);//如果修改门店的营业执照编号，就要重新审核门店
            }
        }
        if (!StringUtil.isEmpty(merchantAddress)) {
            merchant.setMerchantAddress(merchantAddress);
        }
        if (!StringUtil.isEmpty(merchantPic)) {
            merchant.setMerchantPic(merchantPic);
        }
        if (!StringUtil.isEmpty(merchantPhone)) {
            merchant.setMerchantPhone(merchantPhone);
            LitemallMerchant merchant1 = litemallMerchantMapper.selectByPrimaryKey(id);
            if((merchant1.getMerchantPhone().equals(merchant1.getConsigneePhone()))){//说明收货人是负责人
                merchant.setConsigneePhone(merchantPhone);
            }
        }
        if (!StringUtil.isEmpty(merchantLeader)) {
            merchant.setMerchantLeader(merchantLeader);
            user.setName(merchantLeader);
        }
        //设置更新时间
        merchant.setEditTime(LocalDateTime.now());
        try{
            LitemallMerchant merchantLeader1 = findMerchantLeader(String.valueOf(id));
            Integer consigneeId = merchantLeader1.getConsigneeId();
            //user.setId(consigneeId);
            //user.setUpdateTime(LocalDateTime.now());
            //LitemallUserExample userExample = new LitemallUserExample();
            //LitemallUserExample.Criteria criteria1 = userExample.createCriteria();
            //criteria1.andIdEqualTo(consigneeId);
            //litemallUserMapper.updateByExampleSelective(user, userExample);//修改user表中的信息
            return litemallMerchantMapper.updateByExampleSelective(merchant, example);
        }catch (Exception e){
            throw  e;
        }
    }

    /*
    更新商户门店的图片
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
    public Object updateMerchantPic(Integer storeId, String merchantPic) {
        LitemallMerchant merchant = new LitemallMerchant();
        LitemallMerchantExample example = new LitemallMerchantExample();
        LitemallMerchantExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(storeId);
        if (!StringUtil.isEmpty(merchantPic)) {
            merchant.setMerchantPic(merchantPic);
        }
        //设置更新时间
        merchant.setEditTime(LocalDateTime.now());
        try{
            return litemallMerchantMapper.updateByExampleSelective(merchant, example);
        }catch (Exception e){
            throw  e;
        }
    }

    /*
更新商户营业执照的图片
 */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
    public Object updateMerchantCodePic(Integer storeId, String merchantCodePic) {
        LitemallMerchant merchant = new LitemallMerchant();
        LitemallMerchantExample example = new LitemallMerchantExample();
        LitemallMerchantExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(storeId);
        if (!StringUtil.isEmpty(merchantCodePic)) {
            merchant.setMerchantCodePic(merchantCodePic);
        }
        //设置更新时间
        merchant.setEditTime(LocalDateTime.now());
        merchant.setMerchantStatus(2);
        try{
            return litemallMerchantMapper.updateByExampleSelective(merchant, example);
        }catch (Exception e){
            throw  e;
        }
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
    查询登录用户所注册的商户的门店（商户的状态）
     */
     public List<LitemallMerchant> merchantStatusList(Integer userId,Integer merchantStatus) {
         return litemallMerchantMapper.selectByMerchantStatus(userId,merchantStatus);
     }


    public List<LitemallMerchant> querySelective(Integer id, String merchantName, List<Integer> merchantStatusArray, Integer page, Integer limit, String sort, String order) {
        LitemallMerchantExample example = new LitemallMerchantExample();
        LitemallMerchantExample.Criteria criteria = example.createCriteria();

        if (id != null) {
            criteria.andIdEqualTo(id);
        }
        if (!StringUtils.isEmpty(merchantName)) {
            criteria.andMerchantNameEqualTo(merchantName);
        }
        if (merchantStatusArray != null && merchantStatusArray.size() != 0) {
            criteria.andMerchantStatusIn(merchantStatusArray);
        }

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return litemallMerchantMapper.selectByExample(example);
    }

    /*
    查询用户门店的具体信息
     */
    public LitemallMerchant detail(Integer id) {
        return litemallMerchantMapper.selectByPrimaryKey(id);
    }

    /*
    门店的审核
     */
    public Integer audit(Integer id ,Integer merchantStatus){
        LitemallMerchant merchant = new LitemallMerchant();
        merchant.setId(id);
        merchant.setMerchantStatus(merchantStatus);
        merchant.setEditTime(LocalDateTime.now());
        return litemallMerchantMapper.updateByPrimaryKeySelective(merchant);
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
    public Object updateUserStore(String userId, String name, Integer roleType,String mobile) {
        try {
            LitemallUser user = new LitemallUser();
            user.setName(name);
            user.setId(Integer.parseInt(userId));
            user.setMobile(mobile);
            user.setUpdateTime(LocalDateTime.now());
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
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
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

    /*
   查询具体店员的信息
    */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = {Exception.class})
    public Object findOneMerchantUser(String userId) {
        try {
            LitemallUser user = litemallUserMapper.selectByPrimaryKey(Integer.parseInt(userId));
            return  user;
        } catch (Exception e) {
            throw e;
        }
    }

    /*
  设置门店默认收货人
   */
    public Object setConsignee(String userId,String storeId,Integer roleType) {
        try {
            LitemallMerchant merchant =new LitemallMerchant();
            LitemallUser user = litemallUserMapper.selectByPrimaryKey(Integer.parseInt(userId));
            if(roleType==3){//设置负责人为默认收货人
                LitemallMerchant merchant1 = litemallMerchantMapper.selectByPrimaryKey(Integer.parseInt(storeId));
                merchant.setConsigneeName(merchant1.getMerchantLeader());
                merchant.setConsigneePhone(merchant1.getMerchantPhone());
                merchant.setConsigneeId(Integer.parseInt(userId));
            }else {
                merchant.setConsigneeName(user.getName());
                merchant.setConsigneePhone(user.getMobile());
                merchant.setConsigneeId(Integer.parseInt(userId));
            }
            merchant.setEditTime(LocalDateTime.now());
            LitemallMerchantExample example = new LitemallMerchantExample();
            LitemallMerchantExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(Integer.parseInt(storeId));
            int i = litemallMerchantMapper.updateByExampleSelective(merchant, example);
            return  i;
        } catch (Exception e) {
            throw e;
        }
    }

    /*
    门店图片的上传
     */

}
