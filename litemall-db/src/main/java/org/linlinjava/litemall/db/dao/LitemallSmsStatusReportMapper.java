package org.linlinjava.litemall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.linlinjava.litemall.db.domain.LitemallSmsStatusReport;
import org.linlinjava.litemall.db.domain.LitemallSmsStatusReportExample;

public interface LitemallSmsStatusReportMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    long countByExample(LitemallSmsStatusReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    int deleteByExample(LitemallSmsStatusReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    int insert(LitemallSmsStatusReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    int insertSelective(LitemallSmsStatusReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    LitemallSmsStatusReport selectOneByExample(LitemallSmsStatusReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    LitemallSmsStatusReport selectOneByExampleSelective(@Param("example") LitemallSmsStatusReportExample example, @Param("selective") LitemallSmsStatusReport.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    List<LitemallSmsStatusReport> selectByExampleSelective(@Param("example") LitemallSmsStatusReportExample example, @Param("selective") LitemallSmsStatusReport.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    List<LitemallSmsStatusReport> selectByExample(LitemallSmsStatusReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    LitemallSmsStatusReport selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") LitemallSmsStatusReport.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    LitemallSmsStatusReport selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    int updateByExampleSelective(@Param("record") LitemallSmsStatusReport record, @Param("example") LitemallSmsStatusReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    int updateByExample(@Param("record") LitemallSmsStatusReport record, @Param("example") LitemallSmsStatusReportExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    int updateByPrimaryKeySelective(LitemallSmsStatusReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    int updateByPrimaryKey(LitemallSmsStatusReport record);
}