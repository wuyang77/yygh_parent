import request from '@/utils/request'

const api_name = `/api/ucenter/wx`

export default {
  /**
   * 获取微信登录的参数
   * @returns {AxiosPromise}
   */
  getLoginParam() {
    return request({
      url: `${api_name}/getLoginParam`,
      method: `get`
    })
  },
  /**
   * 下单 生成二维码
   * @param orderId
   * @returns {AxiosPromise}
   */
  createNative(orderId) {
    return request({
        url: `/api/order/weixin/createNative/${orderId}`,
        method: 'get'
    })
  },
  /**
   * 查询支付的状态
   * @param orderId
   * @returns {AxiosPromise}
   */
  queryPayStatus(orderId) {
    return request({
        url: `/api/order/weixin/queryPayStatus/${orderId}`,
        method: 'get'
    })
  },
  /**
   * 取消订单
   * @param orderId
   * @returns {AxiosPromise}
   */
  cancelOrder(orderId) {
    return request({
        url: `/api/order/orderInfo/auth/cancelOrder/${orderId}`,
        method: 'get'
    })
  }
}
