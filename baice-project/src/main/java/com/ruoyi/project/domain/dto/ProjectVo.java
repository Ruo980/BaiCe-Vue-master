package com.ruoyi.project.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname ProjectVo
 * @Description 创建项目提交的表单
 * @Date 2023/5/13 22:20
 * @Created by FunCoder
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectVo {

    //项目名称
    private String name;

    //发布人账号
    private String publisher;

    //项目发布和截止时间
    private String[] time;

    //项目人员
    private String[] usernames;

    //待办等级
    private Integer grade;

    //项目权限
    private String projectPermission;

}
