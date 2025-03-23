package com.xpxp.controller;


import com.xpxp.pojo.DTO.ApproveMessageDTO;
import com.xpxp.pojo.DTO.RejectMessageDTO;
import com.xpxp.pojo.VO.MessageVO;
import com.xpxp.result.Result;
import com.xpxp.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 处理消息相关接口
 */
@RestController
@Slf4j
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 获取用户消息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Result<List<MessageVO>> getMessages(@PathVariable Integer id) {
        log.info("获取用户消息：{}",id);
        List<MessageVO> messages = messageService.getMessages(id);
        return Result.success(messages);
    }

    /**
     * 同意申请
     */
    @PostMapping("/approved")
    public Result approveMessage(@RequestBody ApproveMessageDTO approveMessageDTO) {
        log.info("同意申请并将用户纳入团队{}",approveMessageDTO);
        messageService.approveMessage(approveMessageDTO);
        return Result.success();
    }

    /**
     * 拒绝申请
     */
    @PostMapping("/rejected")
    public Result rejectMessage(@RequestBody RejectMessageDTO rejectMessageDTO) {
        log.info("拒绝申请{}",rejectMessageDTO);
        messageService.rejectMessage(rejectMessageDTO);
        return Result.success();
    }
}
