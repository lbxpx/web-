package com.xpxp.service;

import com.xpxp.pojo.DTO.EmailDTO;
import com.xpxp.pojo.DTO.UserLoginDTO;
import com.xpxp.pojo.DTO.UserRegisterDTO;
import com.xpxp.pojo.VO.UserDataVO;
import com.xpxp.pojo.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @Transactional
    User login(UserLoginDTO userLoginDTO);

    /**
     * 获取个人资料
     * @param id
     * @return
     */
    UserDataVO getUserData(int id);

    /**
     * 修改用户资料
     * @param userDataVO
     */
    void updateUserData(UserDataVO userDataVO, int userId);

    /**
     * 用户注册
     * @param userRegisterDTO
     */
    void register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户修改密码
     * @param userRegisterDTO
     */
    void updatePassword(UserRegisterDTO userRegisterDTO);

    /**
     * 获取验证码
     * @param emailDTO
     * @return
     */
    String getCode(EmailDTO emailDTO);
}
