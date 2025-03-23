package com.xpxp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpxp.pojo.DTO.ProjectsPageQueryDTO;
import com.xpxp.pojo.VO.ProjectsVO;
import com.xpxp.pojo.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectsMapper extends BaseMapper<Project> {
    /**
     * 分页条件查询
     * @param projectsPageQueryDTO
     * @return
     */
    Page<ProjectsVO> getProjectsList(Page<ProjectsVO> pageInfo, @Param("condition")ProjectsPageQueryDTO projectsPageQueryDTO);
}
