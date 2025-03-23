package com.xpxp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpxp.pojo.DTO.BlogDTO;
import com.xpxp.pojo.DTO.BolgsPageDTO;
import com.xpxp.pojo.VO.BlogVO;
import com.xpxp.pojo.VO.BlogsVO;

public interface BolgsService {
    /**
     * 获取博客列表
     * @param bolgsPageDTO
     * @return
     */
    Page<BlogsVO> getBlogsList(BolgsPageDTO bolgsPageDTO);

    /**
     * 根据id获取博客
     * @param id
     * @return
     */
    BlogVO getBlog(Integer id);

    /**
     * 撰写新博客
     * @param blogDTO
     */
    void addBlog(BlogDTO blogDTO);
}
