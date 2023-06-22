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
     * 按照publisher查询项目集
     *
     * @param publisher
     */
    List<Project> selectMyCreateProjects(String publisher);
}
