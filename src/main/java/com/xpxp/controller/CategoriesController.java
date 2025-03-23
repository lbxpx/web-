package com.xpxp.controller;

import com.xpxp.pojo.entity.Category;
import com.xpxp.result.Result;
import com.xpxp.service.CategoriesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/categories")
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;

    /**
     * 获得分类标签列表
     */
    @GetMapping
    public Result<List<Category>> categoriesList(){
        log.info("获得分类标签列表");
        List<Category> list = categoriesService.list();
        return Result.success(list);
    }
}
