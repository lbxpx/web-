package com.xpxp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xpxp.mapper.CommentsMapper;
import com.xpxp.pojo.DTO.CommentsDTO;
import com.xpxp.pojo.VO.CommentsVO;
import com.xpxp.pojo.entity.Comments;
import com.xpxp.service.CommentsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsMapper commentsMapper;
    /**
     * 获取评论列表
     * @param id
     * @return
     */
    public List<CommentsVO> getComments(int id) {
        QueryWrapper<Comments> queryWrapper = new QueryWrapper();
        queryWrapper.select("id","content","author")
                .eq("blog_id", id);
        List<Comments> commentsList = commentsMapper.selectList(queryWrapper);

        return commentsList.stream()
                .map(comment -> new CommentsVO(comment.getId(), comment.getContent(), comment.getAuthor()))
                .collect(Collectors.toList());
    }

    /**
     * 发表新评论
     * @param id
     * @param commentsDTO
     */
    public void addComments(int id, CommentsDTO commentsDTO) {
        Comments comments = new Comments();
        BeanUtils.copyProperties(commentsDTO, comments);
        comments.setBlogId(id);
        commentsMapper.insert(comments);
    }
}
