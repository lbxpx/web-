package com.xpxp.controller;

import com.xpxp.pojo.DTO.CommentsDTO;
import com.xpxp.pojo.VO.CommentsVO;
import com.xpxp.pojo.entity.Comments;
import com.xpxp.result.Result;
import com.xpxp.service.CommentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    /**
     *获取评论
     */
    @GetMapping("/{id}")
    public Result<List<CommentsVO>> getComments(@PathVariable int id) {
        log.info("获得评论：{}",id);
        List<CommentsVO> list = commentsService.getComments(id);
        return Result.success(list);
    }
    /**
     * 发表新评论
     */
    @PostMapping("/{id}")
    public Result addComments(@PathVariable int id, @RequestBody CommentsDTO commentsDTO) {
        log.info("发表评论：{},{}",id,commentsDTO);
        commentsService.addComments(id,commentsDTO);
        return Result.success();
    }
}
