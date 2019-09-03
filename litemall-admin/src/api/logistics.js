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
