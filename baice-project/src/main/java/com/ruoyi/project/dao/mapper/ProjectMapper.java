package com.ruoyi.project.dao.mapper;


import com.ruoyi.project.dao.model.Project;

import java.util.List;

/**
 * @Classname ProjectMapper
 * @Description 项目实体操作 mapper
 * @Date 2023/5/13 15:51
 * @Created by FunCoder
 */

public interface ProjectMapper {

    /**
     * 插入项目
     *
     * @param project
     * @return
     */
    int insertProject(Project project);

    /**
     * 查询用户所有项目，只要是和自己相关的（创建或需要处理的），无论项目何种状态
     *
     * @param username
     * @return
     */
    List<Project> selectMyAllProjects(String username);

    /**
     * 按照publisher查询项目集
     *
     * @param publisher
     */
    List<Project> selectMyCreateProjects(String publisher);

    /**
     * 查询用户待处理的项目：此时项目状态应该为0
     *
     * @param username
     * @return
     */
    List<Project> selectMyProjectsByName(String username);

    /**
     * 查询用户已经完成的项目：查看我发布的任务中已经完成的部分，“完成”包括成功的和失败的
     *
     * @param username
     * @return
     */
    List<Project> selectMycCompletedProjectsByName(String username);


}
