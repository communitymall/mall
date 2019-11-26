package org.linlinjava.litemall.db.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class LitemallSmsStatusReport {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_sms_status_report.id
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_sms_status_report.msgId
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    private String msgid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_sms_status_report.mobile
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    private String mobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_sms_status_report.report_time
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    private String reportTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_sms_status_report.code
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    private String code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_sms_status_report.extend
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    private String extend;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_sms_status_report.add_time
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    private LocalDateTime addTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_sms_status_report.updata_time
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    private LocalDateTime updataTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_sms_status_report.msg
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    private String msg;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_sms_status_report.tp_id
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    private String tpId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_sms_status_report.sms_response_code
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    private Integer smsResponseCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_sms_status_report.invalid
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    private String invalid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_sms_status_report.id
     *
     * @return the value of litemall_sms_status_report.id
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_sms_status_report.id
     *
     * @param id the value for litemall_sms_status_report.id
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_sms_status_report.msgId
     *
     * @return the value of litemall_sms_status_report.msgId
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public String getMsgid() {
        return msgid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_sms_status_report.msgId
     *
     * @param msgid the value for litemall_sms_status_report.msgId
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_sms_status_report.mobile
     *
     * @return the value of litemall_sms_status_report.mobile
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_sms_status_report.mobile
     *
     * @param mobile the value for litemall_sms_status_report.mobile
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_sms_status_report.report_time
     *
     * @return the value of litemall_sms_status_report.report_time
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public String getReportTime() {
        return reportTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_sms_status_report.report_time
     *
     * @param reportTime the value for litemall_sms_status_report.report_time
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_sms_status_report.code
     *
     * @return the value of litemall_sms_status_report.code
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_sms_status_report.code
     *
     * @param code the value for litemall_sms_status_report.code
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_sms_status_report.extend
     *
     * @return the value of litemall_sms_status_report.extend
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public String getExtend() {
        return extend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_sms_status_report.extend
     *
     * @param extend the value for litemall_sms_status_report.extend
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public void setExtend(String extend) {
        this.extend = extend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_sms_status_report.add_time
     *
     * @return the value of litemall_sms_status_report.add_time
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public LocalDateTime getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_sms_status_report.add_time
     *
     * @param addTime the value for litemall_sms_status_report.add_time
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_sms_status_report.updata_time
     *
     * @return the value of litemall_sms_status_report.updata_time
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public LocalDateTime getUpdataTime() {
        return updataTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_sms_status_report.updata_time
     *
     * @param updataTime the value for litemall_sms_status_report.updata_time
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public void setUpdataTime(LocalDateTime updataTime) {
        this.updataTime = updataTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_sms_status_report.msg
     *
     * @return the value of litemall_sms_status_report.msg
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public String getMsg() {
        return msg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_sms_status_report.msg
     *
     * @param msg the value for litemall_sms_status_report.msg
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_sms_status_report.tp_id
     *
     * @return the value of litemall_sms_status_report.tp_id
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public String getTpId() {
        return tpId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_sms_status_report.tp_id
     *
     * @param tpId the value for litemall_sms_status_report.tp_id
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_sms_status_report.sms_response_code
     *
     * @return the value of litemall_sms_status_report.sms_response_code
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public Integer getSmsResponseCode() {
        return smsResponseCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_sms_status_report.sms_response_code
     *
     * @param smsResponseCode the value for litemall_sms_status_report.sms_response_code
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public void setSmsResponseCode(Integer smsResponseCode) {
        this.smsResponseCode = smsResponseCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_sms_status_report.invalid
     *
     * @return the value of litemall_sms_status_report.invalid
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public String getInvalid() {
        return invalid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_sms_status_report.invalid
     *
     * @param invalid the value for litemall_sms_status_report.invalid
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public void setInvalid(String invalid) {
        this.invalid = invalid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", msgid=").append(msgid);
        sb.append(", mobile=").append(mobile);
        sb.append(", reportTime=").append(reportTime);
        sb.append(", code=").append(code);
        sb.append(", extend=").append(extend);
        sb.append(", addTime=").append(addTime);
        sb.append(", updataTime=").append(updataTime);
        sb.append(", msg=").append(msg);
        sb.append(", tpId=").append(tpId);
        sb.append(", smsResponseCode=").append(smsResponseCode);
        sb.append(", invalid=").append(invalid);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        LitemallSmsStatusReport other = (LitemallSmsStatusReport) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMsgid() == null ? other.getMsgid() == null : this.getMsgid().equals(other.getMsgid()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getReportTime() == null ? other.getReportTime() == null : this.getReportTime().equals(other.getReportTime()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getExtend() == null ? other.getExtend() == null : this.getExtend().equals(other.getExtend()))
            && (this.getAddTime() == null ? other.getAddTime() == null : this.getAddTime().equals(other.getAddTime()))
            && (this.getUpdataTime() == null ? other.getUpdataTime() == null : this.getUpdataTime().equals(other.getUpdataTime()))
            && (this.getMsg() == null ? other.getMsg() == null : this.getMsg().equals(other.getMsg()))
            && (this.getTpId() == null ? other.getTpId() == null : this.getTpId().equals(other.getTpId()))
            && (this.getSmsResponseCode() == null ? other.getSmsResponseCode() == null : this.getSmsResponseCode().equals(other.getSmsResponseCode()))
            && (this.getInvalid() == null ? other.getInvalid() == null : this.getInvalid().equals(other.getInvalid()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMsgid() == null) ? 0 : getMsgid().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getReportTime() == null) ? 0 : getReportTime().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getExtend() == null) ? 0 : getExtend().hashCode());
        result = prime * result + ((getAddTime() == null) ? 0 : getAddTime().hashCode());
        result = prime * result + ((getUpdataTime() == null) ? 0 : getUpdataTime().hashCode());
        result = prime * result + ((getMsg() == null) ? 0 : getMsg().hashCode());
        result = prime * result + ((getTpId() == null) ? 0 : getTpId().hashCode());
        result = prime * result + ((getSmsResponseCode() == null) ? 0 : getSmsResponseCode().hashCode());
        result = prime * result + ((getInvalid() == null) ? 0 : getInvalid().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table litemall_sms_status_report
     *
     * @mbg.generated Mon Nov 25 13:39:12 CST 2019
     */
    public enum Column {
        id("id", "id", "INTEGER", false),
        msgid("msgId", "msgid", "VARCHAR", false),
        mobile("mobile", "mobile", "VARCHAR", false),
        reportTime("report_time", "reportTime", "VARCHAR", false),
        code("code", "code", "VARCHAR", false),
        extend("extend", "extend", "VARCHAR", false),
        addTime("add_time", "addTime", "TIMESTAMP", false),
        updataTime("updata_time", "updataTime", "TIMESTAMP", false),
        msg("msg", "msg", "VARCHAR", false),
        tpId("tp_id", "tpId", "VARCHAR", false),
        smsResponseCode("sms_response_code", "smsResponseCode", "INTEGER", false),
        invalid("invalid", "invalid", "VARCHAR", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_sms_status_report
         *
         * @mbg.generated Mon Nov 25 13:39:12 CST 2019
         */
        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}