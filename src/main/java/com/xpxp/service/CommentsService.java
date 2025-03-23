package com.xpxp.service;

import com.xpxp.pojo.DTO.CommentsDTO;
import com.xpxp.pojo.VO.CommentsVO;

import java.util.List;

public interface CommentsService {
    /**
     * 获取评论列表
     * @param id
     * @return
     */
    List<CommentsVO> getComments(int id);

    /**
     * 发表新评论
     * @param id
     * @param commentsDTO
     */
    void addComments(int id, CommentsDTO commentsDTO);
}
