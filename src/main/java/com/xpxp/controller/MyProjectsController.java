package com.xpxp.controller;


import com.xpxp.pojo.VO.TeamDetailVO;
import com.xpxp.pojo.entity.ProjectUsers;
import com.xpxp.result.Result;
import com.xpxp.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/myprojects")
public class MyProjectsController {
    @Autowired
    private ProjectService projectService;

    /**
     * 获取我的项目
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<List<ProjectUsers>> getMyProjects(@PathVariable Integer id) {
        log.info("getMyProjects id:{}", id);
        List<ProjectUsers> myProjects = projectService.getMyprojects(id);
        return Result.success(myProjects);
    }

    /**
     * 退出项目
     */
    @DeleteMapping("/{userId}/{projectId}")
    public Result deleteMyProjects(@PathVariable Integer userId, @PathVariable Integer projectId) {
        log.info("deleteMyProjects userId:{} projectId:{}", userId, projectId);
        projectService.deleteMyProjects(userId,projectId);
        return Result.success();
    }

    /**
     * 获取团队信息
     * @param projectId
     * @return
     */
    @GetMapping("/team/{projectId}")
    public Result<List<TeamDetailVO>> getMyTeam(@PathVariable Integer projectId) {
        log.info("getMyTeam projectId:{}", projectId);
        List<TeamDetailVO> list = projectService.getMyteam(projectId);
        return Result.success(list);
    }

}
