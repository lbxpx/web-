package com.xpxp.service;

import com.xpxp.pojo.entity.Category;

import java.util.List;

public interface CategoriesService {
    /**
     * 获得分类标签列表
     */
    List<Category> list();
}
