package com.xpxp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xpxp.pojo.VO.TeamDetailVO;
import com.xpxp.pojo.entity.ProjectUsers;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProjectUsersMapper extends BaseMapper<ProjectUsers> {
    List<TeamDetailVO> getMyTeam(Integer projectId);
}
