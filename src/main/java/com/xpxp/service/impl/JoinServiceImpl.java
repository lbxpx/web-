package com.xpxp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xpxp.mapper.JoinapplicationsMapper;
import com.xpxp.mapper.ProCategoriesMapper;
import com.xpxp.mapper.ProjectsMapper;
import com.xpxp.pojo.VO.JoinProjectVO;

import com.xpxp.pojo.entity.Category;
import com.xpxp.pojo.entity.Joinapplications;
import com.xpxp.pojo.entity.Project;
import com.xpxp.service.JoinService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JoinServiceImpl implements JoinService {
    @Autowired
    private ProjectsMapper projectsMapper;
    @Autowired
    private ProCategoriesMapper proCategoriesMapper;
    @Autowired
    private JoinapplicationsMapper joinapplicationsMapper;

    /**
     * 获取团队项目信息
     * @param id
     * @return
     */
    public JoinProjectVO getJoinProject(Integer id,Integer userId) {
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("title", "description", "publisher_name","image","publisher_id") // 只选择需要的字段
                .eq("id", id); // 添加查询条件
        JoinProjectVO joinProjectVO = new JoinProjectVO();
        BeanUtils.copyProperties(projectsMapper.selectOne(queryWrapper),joinProjectVO);
        QueryWrapper<Joinapplications> joinapplicationsQueryWrapper = new QueryWrapper<>();
        joinapplicationsQueryWrapper.eq("sender_id", userId)
                .eq("project_id", id)
                .eq("expired",0);
        // 执行查询
        Joinapplications joinApplication = joinapplicationsMapper.selectOne(joinapplicationsQueryWrapper);
        // 检查结果
        if (joinApplication != null) {
            // 如果找到数据，获取 status 字段的值
            String status = joinApplication.getStatus();
            joinProjectVO.setStatus(status);
        }
        //获取标签值
        List<Category> categories = getCategoriesByProjectId(id);
        joinProjectVO.setCategories(categories);
        return joinProjectVO;
    }

    /**
     * 储存申请信息
     */
    public void addJoinProject(Joinapplications joinapplications) {
        joinapplicationsMapper.insert(joinapplications);
    }
    public List<Category> getCategoriesByProjectId(Integer projectId) {
        return proCategoriesMapper.getCategoriesByProjectId(projectId);
    }
}
