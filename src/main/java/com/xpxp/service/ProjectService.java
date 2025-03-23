package com.xpxp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpxp.pojo.DTO.NewProjectDTO;
import com.xpxp.pojo.DTO.ProjectsPageQueryDTO;
import com.xpxp.pojo.VO.ProjectsVO;
import com.xpxp.pojo.VO.TeamDetailVO;
import com.xpxp.pojo.entity.ProjectUsers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectService {
    /**
     * 查询项目列表
     * @param projectsPageQueryDTO
     * @return
     */
    @Transactional
    Page<ProjectsVO> getProjectsList(ProjectsPageQueryDTO projectsPageQueryDTO);

    /**
     * 发布招募
     * @param newProjectDTO
     */
    @Transactional
    void addProject(NewProjectDTO newProjectDTO);

    /**
     *删除项目
     * @param id
     */
    @Transactional
    void deleteProject(Integer id);

    /**
     * 获取我的项目
     * @param id
     * @return
     */
    List<ProjectUsers> getMyprojects(Integer id);

    /**
     * 退出项目
     * @param userId
     * @param projectId
     */
    void deleteMyProjects(Integer userId, Integer projectId);

    /**
     * 获取团队信息
     * @param projectId
     * @return
     */
    List<TeamDetailVO> getMyteam(Integer projectId);
}
