package com.xpxp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xpxp.pojo.entity.Category;
import com.xpxp.pojo.entity.ProjectCategories;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProCategoriesMapper extends BaseMapper<ProjectCategories> {
    List<Category> getCategoriesByProjectId(Integer projectId);
}
