import request from '@/utils/request'

export default {
  //更新医院上线和下线状态
  updateStatus(id,status){
    return request({
      url: `/admin/hosp/hospital/updataStatus/${id}/${status}`,
      method: 'put'
    })
  },
  //医院列表
  getPageList(current,limit,searchObj) {
    return request ({
      url: `/admin/hosp/hospital/${current}/${limit}`,
      method: 'get',
      params: searchObj  
    })
  },
  //查询dictCode查询下级数据字典（查询所有省份信息）
  findByDictCode(dictCode) {
    return request({
        url: `/admin/cmn/dict/findByDictCode/${dictCode}`,
        method: 'get'
      })
    },
  
  //根据id查询下级数据字典
  findByParentId(id) {
    return request({
        url: `/admin/cmn/dict/childList/${id}`,
        method: 'get'
      })
  },

  //查看医院id查询医院详情信息
  getHospById(id) {
    return request ({
      url: `/admin/hosp/hospital/detail/${id}`,
      method: 'get'
    })
  },
  //查看医院科室
  getDeptByHoscode(hoscode) {
    return request ({
        url: `/admin/hosp/department/getDeptList/${hoscode}`,
        method: 'get'
    })
  }
}