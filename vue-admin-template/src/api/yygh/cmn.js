import request from '@/utils/request'

export default{
    //根据父id查询所有子元素列表
    getList(id){
      return request({
        url: `/admin/cmn/dict/childList/${id}`,
        method: 'get'
      })
    }
}
