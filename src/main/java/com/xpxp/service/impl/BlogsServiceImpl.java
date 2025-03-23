package com.xpxp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpxp.mapper.BlogsMapper;
import com.xpxp.pojo.DTO.BlogDTO;
import com.xpxp.pojo.DTO.BolgsPageDTO;
import com.xpxp.pojo.VO.BlogVO;
import com.xpxp.pojo.VO.BlogsVO;
import com.xpxp.pojo.entity.Blogs;
import com.xpxp.service.BolgsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogsServiceImpl implements BolgsService {
    @Autowired
    private BlogsMapper blogsMapper;
    /**
     * 获取博客列表
     * @param bolgsPageDTO
     * @return
     */
    public Page<BlogsVO> getBlogsList(BolgsPageDTO bolgsPageDTO) {
        Page<BlogsVO> pageInfo = new Page<>(bolgsPageDTO.getPage(), bolgsPageDTO.getPageSize());
        return blogsMapper.getBlogsList(pageInfo,bolgsPageDTO);
    }

    /**
     * 根据id获取博客内容
     * @param id
     * @return
     */
    public BlogVO getBlog(Integer id) {
        QueryWrapper<Blogs> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("title","content","author_name","tags","created_at")
                .eq("id", id);
        BlogVO blogVO = new BlogVO();
        BeanUtils.copyProperties(blogsMapper.selectOne(queryWrapper),blogVO);
        return blogVO;
    }

    /**
     * 撰写新博客
     * @param blogDTO
     */
    public void addBlog(BlogDTO blogDTO) {
        Blogs blogs = new Blogs();
        BeanUtils.copyProperties(blogDTO,blogs);
        Blogs blog = blogsMapper.selectById(blogs.getId());
        if(blog==null){
            blogsMapper.insert(blogs);
        }
        else {
            blogsMapper.updateById(blogs);
        }
    }
}
