package com.ruoyi.project.service;


import com.ruoyi.project.dao.model.Project;
import com.ruoyi.project.domain.dto.ProjectVo;

import java.util.List;

/**
 * @Classname IProjectService
 * @Description 项目管理业务操作接口
 * @Date 2023/5/13 15:49
 * @Created by FunCoder
 */

public interface IProjectService {

    /**
     * 创建项目业务
     *
     * @param projectVo
     * @return
     */
    int saveProject(ProjectVo projectVo);

    /**
     * 获取全部项目，无论项目何种状态，只要为自己处理或创建的
     * @param username
     * @return
     */
    List<Project> getMyAllProjects(String username);

    /**
     * 查询项目业务：查询的项目是否有关键词模糊查询；是发布的还是接受的。判断查询依据任务状态：无限制、正在受理、完成成功、处理失败
     * 因此主要由三个参数进行限制：
     * 一个是项目名称：支持模糊查询
     * 一个是判断查询者身份：是publisher还是所属人员。0表示是publisher，1则是所属人员
     * 一个是判断项目状态查询依据：0表示正在受理，1表示处理成功，2表示处理失败，3表示无限制
     *
     * @param username
     * @return
     */
    List<Project> getProjects(String name, String username, int identity, int statusMethod);


    /**
     * 查询项目业务：获取我需要处理的所有项目
     *
     * @param username
     * @return
     */
    List<Project> getMyProjectsByName(String username);

    /**
     * 查询项目业务：获取我已经完成的项目
     *
     * @param username
     * @return
     */
    List<Project> getMycCompletedProjectsByName(String username);
}
