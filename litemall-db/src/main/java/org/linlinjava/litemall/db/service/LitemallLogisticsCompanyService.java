package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import org.linlinjava.litemall.db.dao.LitemallLogisticsCompanyMapper;
import org.linlinjava.litemall.db.domain.LitemallLogisticsCompany;
import org.linlinjava.litemall.db.domain.LitemallLogisticsCompanyExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/*
物流公司管理
 */
@Service
public class LitemallLogisticsCompanyService {
    LitemallLogisticsCompany.Column[] columns = new LitemallLogisticsCompany.Column[]{LitemallLogisticsCompany.Column.id, LitemallLogisticsCompany.Column.name, LitemallLogisticsCompany.Column.logisticsType, LitemallLogisticsCompany.Column.address, LitemallLogisticsCompany.Column.phone, LitemallLogisticsCompany.Column.contact, LitemallLogisticsCompany.Column.serviceTel, LitemallLogisticsCompany.Column.createTime,LitemallLogisticsCompany.Column.createUser};

    @Resource
    private LitemallLogisticsCompanyMapper CompanyMapper;

    /*
    物流公司的添加
     */
    public void  add(LitemallLogisticsCompany company){
        company.setCreateTime(LocalDateTime.now());
        CompanyMapper.insertSelective(company);
    }

    /*
    物流公司的修改
     */
    public int updateById(LitemallLogisticsCompany company){
        return CompanyMapper.updateByPrimaryKeySelective(company);
    }

    /*
    根据id查询物流公司的信息
     */
    public  LitemallLogisticsCompany selectById(LitemallLogisticsCompany company){
        int id=company.getId();
        return  CompanyMapper.selectByPrimaryKey(id);
    }

    public List<LitemallLogisticsCompany> querySelective(String id, String name, int page , int limt){
        LitemallLogisticsCompanyExample example = new LitemallLogisticsCompanyExample();
        LitemallLogisticsCompanyExample.Criteria criteria = example.createCriteria();
        if (!StringUtil.isEmpty(id)){
            int i = Integer.parseInt(id);
            criteria.andIdEqualTo(i);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        PageHelper.startPage(page,limt);

        System.out.println(CompanyMapper.selectByExample(example));

        return CompanyMapper.selectByExample(example);
    }

    /*
    物流公司的删除
     */
    public void deleteOne(String id,String name){
        if(!StringUtil.isEmpty(id)){
            int i = Integer.parseInt(id);
            CompanyMapper.deleteByPrimaryKey(i);
        }else if (!StringUtil.isEmpty(name)){
            LitemallLogisticsCompanyExample example = new LitemallLogisticsCompanyExample();
            LitemallLogisticsCompanyExample.Criteria criteria = example.createCriteria();
            criteria.andNameEqualTo(name);
            CompanyMapper.deleteByExample(example);
        }

    }
}
