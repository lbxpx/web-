package com.xpxp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpxp.pojo.DTO.BlogDTO;
import com.xpxp.pojo.DTO.BolgsPageDTO;
import com.xpxp.pojo.VO.BlogVO;
import com.xpxp.pojo.VO.BlogsVO;
import com.xpxp.result.Result;
import com.xpxp.service.BolgsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/api/blogs")
public class BolgsController {
    @Autowired
    private BolgsService bolgsService;

    /**
     * 查询博客列表
     */
    @GetMapping("/page")
    public Result<Page<BlogsVO>> getBlogsList(BolgsPageDTO bolgsPageDTO) {
        log.info("获取博客列表：{}",bolgsPageDTO);
        Page<BlogsVO> page = bolgsService.getBlogsList(bolgsPageDTO);
        return Result.success(page);
    }

    /**
     * 根据id获取博客
     */
    @GetMapping("/{id}")
    public Result<BlogVO> getBlog(@PathVariable Integer id) {
        log.info("根据id获取博客：{}",id);
        BlogVO blogVO = bolgsService.getBlog(id);
        return Result.success(blogVO);
    }

    /**
     * 撰写新的博客
     */
    @PutMapping("/write")
    public Result writeBlog(@RequestBody BlogDTO blogDTO) {
        log.info("撰写新的博客：{}",blogDTO);
        bolgsService.addBlog(blogDTO);
        return Result.success();
    }
}
