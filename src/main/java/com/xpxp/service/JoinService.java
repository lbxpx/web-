package com.xpxp.service;

import com.xpxp.pojo.VO.JoinProjectVO;
import com.xpxp.pojo.entity.Joinapplications;
import org.springframework.transaction.annotation.Transactional;


public interface JoinService {
    /**
     * 获取团队项目信息
     * @param id
     * @return
     */
    @Transactional
    JoinProjectVO getJoinProject(Integer id,Integer userId);

    /**
     * 储存申请信息
     */
    void addJoinProject(Joinapplications joinapplications);
}
