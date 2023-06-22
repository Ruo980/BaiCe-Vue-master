import request from '@/utils/request'


// 查询系统公司用户表单
export function listUser(query){
  //查询所有用户信息
  return request({
    url: '/system/user/list',//该接口已经写好了
    method: 'get',
    params: query
  })
}

//提交创建的项目
export function createProject(data){
  return request({
    url: '/manager/project/post',
    method: 'post',
    data: data
  })
}

//获取用户创建的所有项目
export function listMyCreateProject(username) {
  return request({
    url: '/manager/project/'+username,//该接口已经写好了
    method: 'get'
  })
}

export function listMyCreateProjectByName() {

}

