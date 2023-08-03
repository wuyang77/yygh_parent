import request from '@/utils/request'

const api_name = '/admin/hosp/hospital-set'//基础路径

export default{
    //查询医院列表信息
    //如果携带的是普通参数:param:对象
    //如果携带是json数据:data:searchObj
    getHospitalPage(current,limit,searchObj) {
        return request({
          url: `${api_name}/${current}/${limit}`,
          method: 'post',
          data:searchObj
        })
    },
    //根据医院id删除医院信息
    removeById(id) {
        return request({
            url: `${api_name}/remove/${id}`,
            method: 'delete',
        })
    },
    //添加医院设置
    saveHospSet(hospitalSet) {
        return request({
            url: `${api_name}/saveHospSet`,
            method: 'post',
            data: hospitalSet
        })
    },
     //根据医院id查询医院详情信息
    getById(id) {
        return request({
            url: `${api_name}/detail/${id}`,
            method: 'get'
        })
    },
     //根据id修改医院设置信息
    updateHospitalById(id) {
        return request({
            url: `${api_name}/updateHospSet`,
            method: 'post',
            params:{id:id}
        })
    },
    updateById(hospitalset) {
        return request({
            url: `${api_name}/updateHospSet`,
            method: 'put',
            data: hospitalset
        })
    },

     //做批量删除
    batchDeleteByIds(ids) {
        return request({
            url: `${api_name}/batchRemove`,
            method: 'delete',
            data: ids
        })
    },
    //锁定和取消（修改医院设置的状态）//注意不可以重名
    lockHospital(id,status) {
        return request({
            url: `${api_name}/lockHospitalSet/${id}/${status}`,
            method: 'put'
        })
    }
}
