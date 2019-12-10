import request from '@/utils/request'
import Qs from 'qs'

export function listOrder(query) {
  return request({
    url: '/order/list',
    method: 'get',
    params: query,
    paramsSerializer: function(params) {
      return Qs.stringify(params, { arrayFormat: 'repeat' })
    }
  })
}

export function detailOrder(id) {
  return request({
    url: '/order/detail',
    method: 'get',
    params: { id }
  })
}

export function shipOrder(data) {
  return request({
    url: '/order/ship',
    method: 'post',
    data
  })
}

export function refundOrder(data) {
  return request({
    url: '/order/refund',
    method: 'post',
    data
  })
}

export function replyComment(data) {
  return request({
    url: '/order/reply',
    method: 'post',
    data
  })
}

/*
  订单的审核
 */
export function approvedOrder(data) {
  return request({
    url: '/order/approved',
    method: 'post',
    data
  })
}

/*
  订单的未审核
 */
export function unApprovedOrder(data) {
  return request({
    url: '/order/unApproved',
    method: 'post',
    data
  })
}

/*
订单的备货
*/
export function completeGoods(data) {
  return request({
    url: '/order/completeGoods',
    method: 'post',
    data
  })
}

/*
查询所有备货完成的订单
 */
export function checkDeliveryOrder(query) {
  return request({
    url: '/order/checkDeliveryOrder',
    method: 'get',
    params: query,
    paramsSerializer: function(params) {
      return Qs.stringify(params, { arrayFormat: 'repeat' })
    }
  })
}

