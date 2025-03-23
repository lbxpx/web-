package com.xpxp.service;

import com.xpxp.pojo.DTO.ApproveMessageDTO;
import com.xpxp.pojo.DTO.RejectMessageDTO;
import com.xpxp.pojo.VO.MessageVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageService {

    /**
     * 获取用户消息
     * @param id
     * @return
     */
    List<MessageVO> getMessages(Integer id);

    /**
     * 同意申请并将用户纳入团队
     * @param approveMessageDTO
     */
    @Transactional
    void approveMessage(ApproveMessageDTO approveMessageDTO);

    /**
     * 拒绝申请
     * @param rejectMessageDTO
     */
    void rejectMessage(RejectMessageDTO rejectMessageDTO);
}
