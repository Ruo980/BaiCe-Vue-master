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

/**
 * 初始化表格：无论该项目处于何种状态，将用户创建与所处理的项目全部查询出来
 *
 */
export function listMyAllProjectsByname(username) {
  return request({
    url: '/manager/project/all/'+username,//该接口已经写好了
    method: 'get'
  })
}
/**
 * 获取用户创建的所有项目
 * @param username
 * @returns {*}
 */
export function listMyCreateProjectsByName(username) {
  return request({
    url: '/manager/project/created/'+username,//该接口已经写好了
    method: 'get'
  })
}

/**
 * 查询我的项目：即获取我本身需要处理的项目
 * @param username
 */
export  function listMyProjectsByName(username) {
  return request({
    url:'/manager/project/my/'+username,//该
    method: 'get'
  })
}


/**
 * 已处理项目：我已经完成的所有项目
 */
export function listMyCompletedProjectsByName(username) {
  return request({
    url:'/manager/project/completed/'+username,//该
    method: 'get'
  })
}

