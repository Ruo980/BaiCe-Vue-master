package com.ruoyi.project.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.project.domain.dto.ProjectVo;
import com.ruoyi.project.service.IProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;


/**
 * @Classname ProjectController
 * @Description 项目接口
 * @Date 2023/5/11 17:31
 * @Created by FunCoder
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager/project")
@Slf4j
public class ProjectController {

    private final IProjectService iProjectService;

    /**
     * 项目管理：创建项目，提交表单
     *
     * @param projectVo
     * @return
     */
    @PostMapping("/post")
    public AjaxResult createProject(@RequestBody ProjectVo projectVo) {
        log.info("接口:项目创建，" + projectVo.toString());
        try {
            iProjectService.saveProject(projectVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success();
    }

    /**
     * 查询自己所有项目接口
     *
     * @param username
     * @return
     */
    @GetMapping("/all/{username}")
    public AjaxResult listMyAllProjects(@PathVariable("username") String username) {
        log.info("接口：查询自己所有项目，包括创建的和处理的，用户为：" + username);
        List list = new LinkedList<>();
        try {
            list = iProjectService.getMyAllProjects(username);
            //list = iProjectService.getProjects("", username, 0, 3);//""表示全部查询，0表示查询者为发布人，3表示对项目状态无限制
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success("查询成功", list);
    }

    /**
     * 查询自己创建的所有项目接口
     *
     * @param username
     * @return
     */
    @GetMapping("/created/{username}")
    public AjaxResult listMyCreateProjects(@PathVariable("username") String username) {
        log.info("接口：查询自己创建的所有项目，用户为：" + username);
        List list = new LinkedList<>();
        try {
            list = iProjectService.getProjects("", username, 0, 3);//""表示全部查询，0表示查询者为发布人，3表示对项目状态无限制
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success("查询成功", list);
    }

    /**
     * 查询待处理项目接口：项目的关系人员必须有我
     *
     * @param username
     * @return
     */
    @GetMapping("/my/{username}")
    public AjaxResult listMyProjectsByName(@PathVariable("username") String username) {
        log.info("接口：查询待处理项目，用户为：" + username);
        List list = new LinkedList<>();
        try {
            list = iProjectService.getMyProjectsByName(username);//获取我需要处理的所有项目
            log.info("测试");
            log.info(list.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success("查询成功", list);
    }

    /**
     * 查询自己所完成项目接口：无论是成功的还是失败的
     *
     * @param username
     * @return
     */

    @GetMapping("/completed/{username}")
    public AjaxResult listMycCompletedProjectsByName(@PathVariable("username") String username) {
        log.info("接口：查询处理完成的所有项目，用户为：" + username);
        List list = new LinkedList<>();
        try {
            list = iProjectService.getMycCompletedProjectsByName(username);
            log.info("测试");
            log.info(list.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success("查询成功", list);
    }


}
