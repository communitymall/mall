import request from '@/utils/request'
import Qs from 'qs'
export function listMerchant(query) {
  return request({
    url: '/merchant/list',
    method: 'get',
    params: query,
    paramsSerializer: function(params) {
      return Qs.stringify(params, { arrayFormat: 'repeat' })
    }
  })
}
export function detailMerchant(id) {
  return request({
    url: '/merchant/detail',
    method: 'get',
    params: { id }
  })
}
/*
  门店的审核
 */
export function auditMerchant(data) {
  return request({
    url: '/merchant/audit',
    method: 'post',
    data
  })
}

