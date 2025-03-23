package com.xpxp.controller;

import com.xpxp.pojo.VO.JoinProjectVO;
import com.xpxp.pojo.entity.Joinapplications;
import com.xpxp.result.Result;
import com.xpxp.service.JoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 加入团队相关逻辑
 */
@RestController
@Slf4j
@RequestMapping("/api/joins")
public class JoinController {

    @Autowired
    private JoinService joinService;

    /**
     * 根据id获得团队项目信息
     * @return
     */
    @GetMapping("/{id}/{userId}")
    public Result<JoinProjectVO> getJoinProject(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("获取项目信息：{},{}",id,userId);
        JoinProjectVO joinProjectVO = joinService.getJoinProject(id,userId);
        return Result.success(joinProjectVO);
    }

    /**
     * 储存申请信息
     */
    @PostMapping
    public Result addJoinProject(@RequestBody Joinapplications joinapplications) {
        joinService.addJoinProject(joinapplications);
        return Result.success();
    }

}
