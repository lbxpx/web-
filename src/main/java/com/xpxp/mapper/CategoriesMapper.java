package com.xpxp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xpxp.pojo.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoriesMapper extends BaseMapper<Category> {
}
