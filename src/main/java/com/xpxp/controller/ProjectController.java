package com.xpxp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpxp.pojo.DTO.NewProjectDTO;
import com.xpxp.pojo.DTO.ProjectsPageQueryDTO;
import com.xpxp.pojo.VO.ProjectsVO;
import com.xpxp.result.Result;
import com.xpxp.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    /**
     * 查询项目列表
     */
    @GetMapping("/page")
    public Result<Page<ProjectsVO>> getProjectsList(ProjectsPageQueryDTO projectsPageQueryDTO) {
        log.info("查询项目列表：{}", projectsPageQueryDTO);
        Page<ProjectsVO> page = projectService.getProjectsList(projectsPageQueryDTO);
        return Result.success(page);
    }

    /**
     * 发布招募
     */
    @PostMapping
    public Result addProject(@RequestBody NewProjectDTO newProjectDTO) {
        log.info("发布招募：{}", newProjectDTO);
        projectService.addProject(newProjectDTO);
        return Result.success();
    }

    /**
     * 删除项目
     */
    @DeleteMapping("/{id}")
    public Result deleteProject(@PathVariable Integer id) {
        log.info("删除项目：{}",id);
        projectService.deleteProject(id);
        return Result.success();
    }
}
