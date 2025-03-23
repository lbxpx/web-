package com.xpxp.controller;


import com.xpxp.pojo.VO.UserDataVO;

import com.xpxp.result.Result;
import com.xpxp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 个人资料相关逻辑
 */
@RestController
@Slf4j
@RequestMapping("/api/userData")
public class UserDataController {
    @Autowired
    private UserService userService;

    /**
     * 获取个人资料
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<UserDataVO> getUserData(@PathVariable int id) {
        log.info("获取用户资料:{}",id);
        UserDataVO userDataVO = userService.getUserData(id);
        return Result.success(userDataVO);
    }

    /**
     * 修改用户资料
     */
    @PutMapping("/{userId}")
    public Result updateUserData(@RequestBody UserDataVO userDataVO,
                                 @PathVariable int userId) {
        log.info("修改用户资料：{},{}",userDataVO,userId);
        userService.updateUserData(userDataVO,userId);
        return Result.success();
    }
}
