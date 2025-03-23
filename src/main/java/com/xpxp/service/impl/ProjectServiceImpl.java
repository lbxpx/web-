package com.xpxp.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpxp.mapper.JoinapplicationsMapper;
import com.xpxp.mapper.ProCategoriesMapper;
import com.xpxp.mapper.ProjectUsersMapper;
import com.xpxp.mapper.ProjectsMapper;
import com.xpxp.pojo.DTO.NewProjectDTO;
import com.xpxp.pojo.DTO.ProjectsPageQueryDTO;


import com.xpxp.pojo.VO.ProjectsVO;


import com.xpxp.pojo.VO.TeamDetailVO;
import com.xpxp.pojo.entity.*;
import com.xpxp.service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectsMapper projectsMapper;
    @Autowired
    private ProCategoriesMapper proCategoriesMapper;
    @Autowired
    private ProjectUsersMapper projectUsersMapper;
    @Autowired
    private JoinapplicationsMapper joinapplicationsMapper;
    /**
     * 查询项目列表
     * @param projectsPageQueryDTO
     * @return
     */
    public Page<ProjectsVO> getProjectsList(ProjectsPageQueryDTO projectsPageQueryDTO) {
        Page<ProjectsVO> pageInfo = new Page<>(projectsPageQueryDTO.getPage(),projectsPageQueryDTO.getPageSize());
        // 获取项目列表
        pageInfo = projectsMapper.getProjectsList(pageInfo, projectsPageQueryDTO);

        // 遍历项目列表，为每个项目赋值其类别
        for (ProjectsVO project : pageInfo.getRecords()) {
            // 调用方法获取该项目的类别
            List<Category> categories = getCategoriesByProjectId(project.getId());
            project.setCategories(categories);
        }
        return pageInfo;
    }

    /**
     * 发布招募
     * @param newProjectDTO
     */
    public void addProject(NewProjectDTO newProjectDTO) {
        Project project = new Project();
        BeanUtils.copyProperties(newProjectDTO,project);
        project.setPublisherId(newProjectDTO.getUserId());
        projectsMapper.insert(project);
        int id = project.getId();
        List<Integer> categoryIds = newProjectDTO.getCategoryIds();
        // 使用流（Stream）API进行转换
        List<ProjectCategories> projectCategories = categoryIds.stream()
                .map(categoryId -> new ProjectCategories(
                        null, // id
                        id,       // projectId
                        categoryId // categoryId
                ))
                .collect(Collectors.toList());
        proCategoriesMapper.insert(projectCategories);
        ProjectUsers projectUsers = ProjectUsers.builder()
                .projectId(id)
                .userId(newProjectDTO.getUserId())
                .title(newProjectDTO.getTitle())
                .role("队长")
                .assign("统筹一切，分配任务")
                .build();
        projectUsersMapper.insert(projectUsers);
    }

    /**
     * 删除项目
     * @param id
     */
    public void deleteProject(Integer id) {
        projectsMapper.deleteById(id);
        QueryWrapper<ProjectCategories> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id", id); // 设置删除条件
        proCategoriesMapper.delete(queryWrapper);
        projectUsersMapper.delete(new QueryWrapper<ProjectUsers>().eq("project_id", id));
    }

    /**
     * 获取我的项目
     * @param id
     * @return
     */
    public List<ProjectUsers> getMyprojects(Integer id) {
        QueryWrapper<ProjectUsers> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", id);
        return projectUsersMapper.selectList(queryWrapper);
    }

    /**
     * 退出项目
     * @param userId
     * @param projectId
     */
    public void deleteMyProjects(Integer userId, Integer projectId) {
        // 删除 ProjectUsers 表中的记录
        QueryWrapper<ProjectUsers> projectUsersQuery = QueryWrapperUtil.buildQueryWrapper(userId, projectId, "user_id", "project_id");
        projectUsersMapper.delete(projectUsersQuery);

        // 修改 Joinapplications 表中的状态
        QueryWrapper<Joinapplications> joinApplicationsQuery = QueryWrapperUtil.buildQueryWrapper(userId, projectId, "sender_id", "project_id");

        // 创建一个新的实体对象只设置要更新的字段
        Joinapplications updateObject = new Joinapplications();
        updateObject.setExpired(1);
        joinapplicationsMapper.update(updateObject, joinApplicationsQuery);
    }


    /**
     * 获取团队信息
     * @param projectId
     * @return
     */
    public List<TeamDetailVO> getMyteam(Integer projectId) {
        return projectUsersMapper.getMyTeam(projectId);
    }

    public List<Category> getCategoriesByProjectId(Integer projectId) {
        return proCategoriesMapper.getCategoriesByProjectId(projectId);
    }

    public class QueryWrapperUtil {

        // 泛型方法，构建查询条件
        public static <T> QueryWrapper<T> buildQueryWrapper(int userId, int projectId, String userIdField, String projectIdField) {
            QueryWrapper<T> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(userIdField, userId)
                    .eq(projectIdField, projectId);
            return queryWrapper;
        }
    }
}
