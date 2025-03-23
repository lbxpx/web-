package com.xpxp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xpxp.pojo.DTO.BolgsPageDTO;
import com.xpxp.pojo.VO.BlogsVO;
import com.xpxp.pojo.entity.Blogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BlogsMapper extends BaseMapper<Blogs> {
    /**
     * 分页条件查询
     * @param bolgsPageDTO
     * @return
     */
    Page<BlogsVO> getBlogsList(Page<BlogsVO> pageInfo, @Param("condition")BolgsPageDTO bolgsPageDTO);
}
