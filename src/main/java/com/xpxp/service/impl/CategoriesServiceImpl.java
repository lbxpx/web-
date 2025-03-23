package com.xpxp.service.impl;

import com.xpxp.mapper.CategoriesMapper;
import com.xpxp.pojo.entity.Category;
import com.xpxp.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    @Autowired
    private CategoriesMapper categoriesMapper;
    /**
     * 获得分类标签列表
     */
    public List<Category> list() {
        return categoriesMapper.selectList(null);
    }
}
