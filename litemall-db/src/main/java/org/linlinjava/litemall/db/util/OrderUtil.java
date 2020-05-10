package org.linlinjava.litemall.db.util;

import org.linlinjava.litemall.db.domain.LitemallOrder;
import java.util.ArrayList;
import java.util.List;

/*
 * 订单流程：下单成功－》支付订单－》发货－》收货
 * 订单状态：
 * 101 订单生成，未支付；102，下单未支付用户取消；103，下单未支付超期系统自动取消
 * 201 支付完成，商家未发货；202，订单生产，已付款未发货，用户申请退款；203，管理员执行退款操作，确认退款成功；
 * 301 商家发货，用户未确认；
 * 401 用户确认收货，订单结束； 402 用户没有确认收货，但是快递反馈已收获后，超过一定时间，系统自动确认收货，订单结束。
 *
 *
 * 0 订单没有审核 (用户)可以取消订单 系统管理员需要审核的订单（货到付款类型的订单）
 * 1 未备货 订单中的商品需要去采购（客户现在可以取消订单）
 * 2 已备货  订单的商品采购完毕（客户不可用取消订单）
 * 3 订单未派送  是专门给客户展现的订单状态？？？
 * 4 订单已经发货 ：不可以消订单
 * 5 订单已经收货 ：不可以消订单
 * 6 订单手动收货 ： 不可以消订单
 * 7 订单待支付  在线付款的订单的状态
 * 8 订单取消
 * 9 订单超时未支付取消订单
 * 10 订单交易完成   不可用取消订单
 * 11 订单审核未通过
 *
 * 当101用户未付款时，此时用户可以进行的操作是取消或者付款
 * 当201支付完成而商家未发货时，此时用户可以退款
 * 当301商家已发货时，此时用户可以有确认收货
 * 当401用户确认收货以后，此时用户可以进行的操作是退货、删除、去评价或者再次购买
 * 当402系统自动确认收货以后，此时用户可以删除、去评价、或者再次购买
 */
public class OrderUtil {

    public static final Short STATUS_NO_REVIEW = 0;
    public static final Short STATUS_APPROVED = 1;
    public static final Short STATUS_STOCK_UP = 2;
    public static final Short STATUS_NO_SEND = 3;
    public static final Short STATUS_DELIVERY = 4;
    public static final Short STATUS_RECEIVING = 5;
    public static final Short STATUS_MANUAL_RECEIVING = 6;
    public static final Short STATUS_PENDING_PAYMENT = 7;
    public static final Short STATUS_PAYMENT_COMPLETED = 8;
    public static final Short STATUS_CANCELLATION = 9;
    public static final Short STATUS_OVERTINE_CANCELLATION = 10;
    public static final Short STATUS_FAILED_AUDIT = 11;




    public static String orderStatusText(LitemallOrder order) {
        int status = order.getOrderStatus().intValue();

        System.out.println(status);

        if (status == 0) {
            return "订单未审核";
        }

        if (status == 1) {
            return "订单未备货";
        }

        if (status == 2) {
            return "订单已备货";
        }

        if (status == 3) {
            return "订单未派送";
        }

        if (status == 4) {
            return "订单已经发货";
        }

        if (status == 5) {
            return "订单已经收货";
        }

        if (status == 6) {
            return "订单手动收货";
        }

        if (status == 7) {
            return "订单待支付";
        }

        if (status == 8) {
            return "订单取消";
        }

        if (status == 9) {
            return "订单超时未支付取消订单";
        }

        if (status == 10) {
            return "订单交易完成";
        }

        if(status == 11){
            return "订单审核未通过";
        }
        throw new IllegalStateException("orderStatus不支持");
    }


    public static OrderHandleOption build(LitemallOrder order) {
        int status = order.getOrderStatus().intValue();
        OrderHandleOption handleOption = new OrderHandleOption();
        if (status == 0) {
            //订单没有审核 ： 可以取消订单  ，订单不可支付
            handleOption.setCancel(true);
        } else if (status == 1 ) {
            // 订单未备货 : 可以取消订单
            handleOption.setCancel(true);
        } else if (status == 2 ) {
            // 订单的商品已备货

        }else if(status == 3){
            // 订单未派送
        }
        else if (status == 4) {
            // 订单已经发货    不可以取消订单，（在线支付）不可以退款,可以确认收货
            handleOption.setConfirm(true);
        } else if (status == 5) {
            // 订单系统自动确认收货
        }else if (status == 6) {
            // 订单用户确认收货
        }  else if (status == 7) {
            // 订单待支付， 订单可以取消 ，订单可以支付
            handleOption.setCancel(true);
            handleOption.setPay(true);
        } else if (status == 8) {
            // 订单取消，没有操作
        } else if (status == 9 ) {
            // 订单超时未支付取消订单，没有操作
        } else if (status == 10 ) {
            // 订单交易完成，不可取消订单，不可退款，可以删除订单，可以评论，可以再次购买
            handleOption.setDelete(true);
            handleOption.setComment(true);
            handleOption.setRebuy(true);
        } else if (status == 11 ) {
            // 订单审核未通过

        } else {
            throw new IllegalStateException("status不支持");
        }
        return handleOption;
    }



    public static List<Short> orderStatus(Integer showType) {
        // 全部订单(修改为-1)
        if (showType == -1) {
            return null;
        }

        List<Short> status = new ArrayList<Short>(2);

        if (showType.equals(0)) {
            // 未审核的订单
            status.add((short) 0);
        } else if (showType.equals(1)) {
            // 订单审核通过
            status.add((short) 1);
        } else if (showType.equals(2)) {
            //  订单待发货
            status.add((short) 1);
            status.add((short) 2);
            status.add((short) 3);
        } else if (showType.equals(3)) {
            // 订单待收货
            status.add((short) 4);

//            系统超时自动取消，此时应该不支持评价
//            status.add((short)402);
        }else if (showType.equals(4)) {
            //  未审核与审核没有通过的订单信息
            status.add((short) 11);
            status.add((short )0);
        }else if (showType.equals(5)) {
            // 订单已经收货
            status.add((short) 5);
        }else if (showType.equals(6)) {
            //订单手动收货
            status.add((short) 6);
        }else if (showType.equals(7)) {
            // 订单待支付
            status.add((short) 7);
        }else if (showType.equals(8)) {
            // 订单支付完成
            status.add((short) 8);
        }else if (showType.equals(9)) {
            // 订单取消
            status.add((short) 9);
        }else if (showType.equals(10)) {
            // 订单超时未支付取消订单
            status.add((short) 10);
        }else if (showType.equals(11)) {
            // 订单交易完成
            status.add((short) 11);
        }else {
            return null;
        }

        return status;
    }


    public static boolean isNoReviewStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_NO_REVIEW == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isApprovedStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_APPROVED == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isStockUpStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_STOCK_UP == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isNoSendStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_NO_SEND == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isDeliveryStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_DELIVERY == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isReceivingStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_RECEIVING == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isManualReceivingStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_MANUAL_RECEIVING == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isPendingPaymentStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_PENDING_PAYMENT == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isPaymentCompletedStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_FAILED_AUDIT == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isCancellationStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_CANCELLATION == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isOvertimeCancellationStatus(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_OVERTINE_CANCELLATION == litemallOrder.getOrderStatus().shortValue();
    }

    public static boolean isStatusFailed_Audit(LitemallOrder litemallOrder) {
        return OrderUtil.STATUS_OVERTINE_CANCELLATION == litemallOrder.getOrderStatus().shortValue();
    }

}