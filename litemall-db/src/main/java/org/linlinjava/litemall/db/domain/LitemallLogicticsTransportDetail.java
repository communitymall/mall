package org.linlinjava.litemall.db.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class LitemallLogicticsTransportDetail {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_logictics_transport_detail.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_logictics_transport_detail.transit_id
     *
     * @mbg.generated
     */
    private String transitId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_logictics_transport_detail.order_sn
     *
     * @mbg.generated
     */
    private String orderSn;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_logictics_transport_detail.ship_status
     *
     * @mbg.generated
     */
    private Integer shipStatus;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column litemall_logictics_transport_detail.create_time
     *
     * @mbg.generated
     */
    private LocalDateTime createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_logictics_transport_detail.id
     *
     * @return the value of litemall_logictics_transport_detail.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_logictics_transport_detail.id
     *
     * @param id the value for litemall_logictics_transport_detail.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_logictics_transport_detail.transit_id
     *
     * @return the value of litemall_logictics_transport_detail.transit_id
     *
     * @mbg.generated
     */
    public String getTransitId() {
        return transitId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_logictics_transport_detail.transit_id
     *
     * @param transitId the value for litemall_logictics_transport_detail.transit_id
     *
     * @mbg.generated
     */
    public void setTransitId(String transitId) {
        this.transitId = transitId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_logictics_transport_detail.order_sn
     *
     * @return the value of litemall_logictics_transport_detail.order_sn
     *
     * @mbg.generated
     */
    public String getOrderSn() {
        return orderSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_logictics_transport_detail.order_sn
     *
     * @param orderSn the value for litemall_logictics_transport_detail.order_sn
     *
     * @mbg.generated
     */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_logictics_transport_detail.ship_status
     *
     * @return the value of litemall_logictics_transport_detail.ship_status
     *
     * @mbg.generated
     */
    public Integer getShipStatus() {
        return shipStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_logictics_transport_detail.ship_status
     *
     * @param shipStatus the value for litemall_logictics_transport_detail.ship_status
     *
     * @mbg.generated
     */
    public void setShipStatus(Integer shipStatus) {
        this.shipStatus = shipStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column litemall_logictics_transport_detail.create_time
     *
     * @return the value of litemall_logictics_transport_detail.create_time
     *
     * @mbg.generated
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column litemall_logictics_transport_detail.create_time
     *
     * @param createTime the value for litemall_logictics_transport_detail.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_logictics_transport_detail
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", transitId=").append(transitId);
        sb.append(", orderSn=").append(orderSn);
        sb.append(", shipStatus=").append(shipStatus);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_logictics_transport_detail
     *
     * @mbg.generated
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
        LitemallLogicticsTransportDetail other = (LitemallLogicticsTransportDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTransitId() == null ? other.getTransitId() == null : this.getTransitId().equals(other.getTransitId()))
            && (this.getOrderSn() == null ? other.getOrderSn() == null : this.getOrderSn().equals(other.getOrderSn()))
            && (this.getShipStatus() == null ? other.getShipStatus() == null : this.getShipStatus().equals(other.getShipStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table litemall_logictics_transport_detail
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTransitId() == null) ? 0 : getTransitId().hashCode());
        result = prime * result + ((getOrderSn() == null) ? 0 : getOrderSn().hashCode());
        result = prime * result + ((getShipStatus() == null) ? 0 : getShipStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table litemall_logictics_transport_detail
     *
     * @mbg.generated
     */
    public enum Column {
        id("id", "id", "BIGINT", false),
        transitId("transit_id", "transitId", "VARCHAR", false),
        orderSn("order_sn", "orderSn", "VARCHAR", false),
        shipStatus("ship_status", "shipStatus", "INTEGER", false),
        createTime("create_time", "createTime", "TIMESTAMP", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
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
         * This method corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
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
         * This method corresponds to the database table litemall_logictics_transport_detail
         *
         * @mbg.generated
         */
        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}