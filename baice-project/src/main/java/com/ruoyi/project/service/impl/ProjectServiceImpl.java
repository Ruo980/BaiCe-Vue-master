package com.ruoyi.project.service.impl;


import com.ruoyi.project.dao.mapper.ProjectMapper;
import com.ruoyi.project.dao.model.Project;
import com.ruoyi.project.domain.dto.ProjectVo;
import com.ruoyi.project.domain.vo.ShowProjectVo;
import com.ruoyi.project.service.IProjectService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @Classname ProjectServiceImpl
 * @Description 项目管理业务操作
 * @Date 2023/5/13 15:49
 * @Created by FunCoder
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {

    private final ProjectMapper projectMapper;

    private final ISysUserService iSysUserService;

    @Override
    public int saveProject(ProjectVo projectVo) {
        Project project = new Project();
        for (String username : projectVo.getUsernames()) {//所属人员存在多个，遍历插入
            project.setName(projectVo.getName());
            project.setPublisher(projectVo.getPublisher());
            project.setPublishTime(projectVo.getTime()[0]);
            project.setEndTime(projectVo.getTime()[1]);
            project.setUsername(username);//这里重点是要toString将数组转为字符串存入数据库中
            project.setGrade(projectVo.getGrade());
            project.setProjectPermission(projectVo.getProjectPermission());
            project.setProjectStatus(0);//0表示已发布，正在处理
            projectMapper.insertProject(project);
        }
        return 0;
    }

    /**
     * 获取全部项目，无论项目何种状态，只要为自己处理或创建的
     *
     * @param username
     * @return
     */
    @Override
    public List getMyAllProjects(String username) {
        List<Project> list1 = projectMapper.selectMyAllProjects(username);
        List<ShowProjectVo> list = new LinkedList<ShowProjectVo>();
        getProjectVo(list1, list);//将数据转换为vo，交给前端页面
        return list;
    }

    @Override
    public List getProjects(String name, String username, int identity, int statusMethod) {
        List<ShowProjectVo> list = new LinkedList<ShowProjectVo>();
        if (name.equals("")) {
            if (identity == 0) {//发布者查询项目:此时username实际上就是publisher
                if (statusMethod == 3) {//验证项目状态：无限制，不需要进行项目状态区分
                    List<Project> list1 = new LinkedList<Project>();
                    list1 = projectMapper.selectMyCreateProjects(username);
                    //对list1进行处理:相同的项目名称将所属人员组成一串字符
                    getProjectVo(list1, list);
                }
            }
        }
        return list;
    }

    /**
     * 查询项目业务：获取我需要处理的所有项目
     *
     * @param username
     * @return
     */
    @Override
    public List getMyProjectsByName(String username) {
        List<Project> list1 = projectMapper.selectMyProjectsByName(username);
        List<ShowProjectVo> list = new LinkedList<ShowProjectVo>();
        getProjectVo(list1, list);//将数据转换为vo，交给前端页面
        return list;
    }

    private void getProjectVo(List<Project> list1, List<ShowProjectVo> list) {
        for (int i = 0; i < list1.size(); i++) {
            if (i == 0) {
                addShowProjectVo(list, list1, i);//插入相关数据到列表中
            } else {
                if (list1.get(i).getName().equals(list1.get(i - 1).getName())) {//与上一行项目名称相等时
                    ShowProjectVo showProjectVo = list.get(list.size() - 1);//获取上一个showProjectVo
                    showProjectVo.setUsernames(showProjectVo.getUsernames() + "、" + list1.get(i).getUsername());
                    System.out.println(showProjectVo.getUsernames());
                    showProjectVo.setUsernamesNickname(showProjectVo.getUsernamesNickname() + "、" + iSysUserService.selectUserByUserName(list1.get(i).getUsername()).getNickName());
                } else {//与上一行项目名称不相等时，插入数据
                    addShowProjectVo(list, list1, i);
                }
            }
        }
    }

    /**
     * 查询项目业务：获取我已经完成的项目
     *
     * @param username
     * @return
     */
    @Override
    public List getMycCompletedProjectsByName(String username) {
        List<Project> list1 = projectMapper.selectMycCompletedProjectsByName(username);
        List<ShowProjectVo> list = new LinkedList<ShowProjectVo>();
        getProjectVo(list1, list);//将数据转换为vo，交给前端页面
        return list;
    }

    /**
     * 原有行项目重复，设法将项目行合并：这里主要是读到不同行时操作合并
     *
     * @param list
     * @param list1
     * @param i
     */
    private void addShowProjectVo(List<ShowProjectVo> list, List<Project> list1, int i) {
        ShowProjectVo showProjectVo = new ShowProjectVo();
        showProjectVo.setName(list1.get(i).getName());
        showProjectVo.setPublisher(list1.get(i).getPublisher());
        showProjectVo.setNickName(iSysUserService.selectUserByUserName(list1.get(i).getPublisher()).getNickName());
        showProjectVo.setPublishTime(list1.get(i).getPublishTime());
        showProjectVo.setEndTime(list1.get(i).getEndTime());
        showProjectVo.setGrade(list1.get(i).getGrade());
        showProjectVo.setUsernames(list1.get(i).getUsername());
        showProjectVo.setUsernamesNickname(iSysUserService.selectUserByUserName(list1.get(i).getUsername()).getNickName());
        if (list1.get(i).getGrade() == 0) {
            showProjectVo.setGradeDescribe("不重要不紧急");
        } else if (list1.get(i).getGrade() == 1) {
            showProjectVo.setGradeDescribe("重要不紧急");
        } else if (list1.get(i).getGrade() == 2) {
            showProjectVo.setGradeDescribe("紧急不重要");
        } else {
            showProjectVo.setGradeDescribe("重要且紧急");
        }
        showProjectVo.setProjectPermission(list1.get(i).getProjectPermission());
        showProjectVo.setProjectStatus(list1.get(i).getProjectStatus());
        if (list1.get(i).getProjectStatus() == 0) {
            showProjectVo.setProjectStatusDescribe("正在处理");
        } else if (list1.get(i).getProjectStatus() == 1) {
            showProjectVo.setProjectStatusDescribe("处理成功");
        } else {
            showProjectVo.setProjectStatusDescribe("处理失败");
        }
        list.add(showProjectVo);
    }

}
