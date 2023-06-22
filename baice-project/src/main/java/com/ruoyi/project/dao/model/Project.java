package com.ruoyi.project.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname Project
 * @Description 项目实体
 * @Date 2023/5/13 15:51
 * @Created by FunCoder
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    //项目名称
    private String name;

    //发布人账号
    private String publisher;

    //项目发布时间
    private String publishTime;

    //项目截止时间
    private String endTime;

    //项目所属人员账号
    private String username;

    //待办等级
    private Integer grade;

    //项目权限
    private String projectPermission;

    //项目状态
    private Integer projectStatus;

}
