package com.xpxp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xpxp.mapper.JoinapplicationsMapper;
import com.xpxp.mapper.ProjectUsersMapper;
import com.xpxp.pojo.DTO.ApproveMessageDTO;
import com.xpxp.pojo.DTO.RejectMessageDTO;
import com.xpxp.pojo.VO.MessageVO;
import com.xpxp.pojo.entity.Joinapplications;
import com.xpxp.pojo.entity.ProjectUsers;
import com.xpxp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private JoinapplicationsMapper joinapplicationsMapper;
    @Autowired
    private ProjectUsersMapper projectUsersMapper;
    /**
     * 获取用户消息
     * @param id
     * @return
     */
    public List<MessageVO> getMessages(Integer id) {
        return joinapplicationsMapper.getMessages(id);
    }

    /**
     * 同意申请并将用户纳入团队
     * @param approveMessageDTO
     */
    public void approveMessage(ApproveMessageDTO approveMessageDTO) {
        //修改申请表中的状态
        QueryWrapper<Joinapplications> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sender_id", approveMessageDTO.getSenderId())
                .eq("project_id", approveMessageDTO.getProjectId());
        // 创建一个新的实体对象，只设置要更新的字段
        Joinapplications updateObject = new Joinapplications();
        updateObject.setStatus(approveMessageDTO.getStatus());
        joinapplicationsMapper.update(updateObject, queryWrapper);

        //向项目-用户表中插入用户数据
        ProjectUsers projectUsers = ProjectUsers.builder()
                .userId(approveMessageDTO.getSenderId())
                .projectId(approveMessageDTO.getProjectId())
                .title(approveMessageDTO.getTitle())
                .assign(approveMessageDTO.getAssign())
                .build();
        projectUsersMapper.insert(projectUsers);
    }

    /**
     * 拒绝申请
     * @param rejectMessageDTO
     */
    public void rejectMessage(RejectMessageDTO rejectMessageDTO) {
        //修改申请表中的状态
        QueryWrapper<Joinapplications> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sender_id", rejectMessageDTO.getSenderId())
                .eq("project_id", rejectMessageDTO.getProjectId());
        Joinapplications updateObject = new Joinapplications();
        updateObject.setStatus(rejectMessageDTO.getStatus());
        joinapplicationsMapper.update(updateObject, queryWrapper);
    }
}
