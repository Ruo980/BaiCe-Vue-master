package com.ruoyi.project.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname MyCreateProjectVo
 * @Description 项目表格显示
 * @Date 2023/5/16 17:43
 * @Created by FunCoder
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowProjectVo {

    //项目名称
    private String name;

    //发布人账号
    private String publisher;

    //发布人昵称
    private String nickName;

    //项目发布时间
    private String publishTime;

    //项目截止时间
    private String endTime;

    //项目所属人员账号
    private String usernames;

    //项目所属人员昵称列表
    private String usernamesNickname;

    //待办等级
    private Integer grade;

    //待办等级描述
    private String gradeDescribe;

    //项目权限
    private String projectPermission;

    //项目状态
    private Integer projectStatus;

    //项目描述
    private String projectStatusDescribe;

}
