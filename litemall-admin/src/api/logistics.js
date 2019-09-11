/*
物流公司的管理
 */

import request from '@/utils/request'

export function listLogistics(query) {
  return request({
    url: '/logistics/listCompany',
    method: 'get',
    params: query
  })
}

export function createLogistics(data) {
  return request({
    url: '/logistics/addCompany',
    method: 'post',
    data
  })
}

export function readLogistics(data) {
  return request({
    url: '/logistics/read',
    method: 'get',
    data
  })
}

export function updateLogistics(data) {
  return request({
    url: '/logistics/updateCompany',
    method: 'post',
    data
  })
}

export function deleteLogistics(data) {
  return request({
    url: '/logistics/delCompany',
    method: 'post',
    data
  })
}

/*
   物流公司车辆的管理
 */
export function listLogisticsTrucks(query) {
  return request({
    url: '/logistics/listTruck',
    method: 'get',
    params: query
  })
}

export function createLogisticsTrucks(data) {
  return request({
    url: '/logistics/addTruck',
    method: 'post',
    data
  })
}

export function updateLogisticsTrucks(data) {
  return request({
    url: '/logistics/updateTruck',
    method: 'post',
    data
  })
}

export function deleteLogisticsTrucks(data) {
  return request({
    url: '/logistics/delTruck',
    method: 'post',
    data
  })
}

/**
 * 物流订单的管理
 */
export function listLogisticsOrder(query) {
  return request({
    url: '/logistics/queryOrderList',
    method: 'get',
    params: query
  })
}

/*
物流配送详情
 */
export function FindLogisticsTransit(query) {
  return request({
    url: '/logistics/queryTransit',
    method: 'get',
    params: query
  })
}

/*
物流订单的创建
 */
export function createOrder(data) {
  return request({
    url: '/logistics/createOrder',
    method: 'post',
    data
  })
}
