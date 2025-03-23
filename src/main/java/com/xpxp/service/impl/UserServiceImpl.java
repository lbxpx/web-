package com.xpxp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xpxp.constant.MessageConstant;
import com.xpxp.constant.StatusConstant;
import com.xpxp.exception.AccountHasExistedException;
import com.xpxp.exception.AccountLockedException;
import com.xpxp.exception.AccountNotFoundException;
import com.xpxp.exception.PasswordErrorException;
import com.xpxp.mapper.UserMapper;
import com.xpxp.pojo.DTO.EmailDTO;
import com.xpxp.pojo.DTO.UserLoginDTO;
import com.xpxp.pojo.DTO.UserRegisterDTO;
import com.xpxp.pojo.VO.UserDataVO;
import com.xpxp.pojo.entity.User;
import com.xpxp.service.UserService;
import com.xpxp.utils.GetCode;
import com.xpxp.utils.MailUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;



@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailUtil mailUtil;
    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    public User login(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, username);
        //1、根据用户名查询数据库中的数据
        User user = userMapper.selectOne(lqw);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // 对前端传来的密码进行MD5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (user.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return user;
    }

    /**
     * 获取用户资料
     * @param id
     * @return
     */
    public UserDataVO getUserData(int id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        User user = userMapper.selectOne(queryWrapper);
        UserDataVO userDataVO = new UserDataVO();
        BeanUtils.copyProperties(user, userDataVO);
        userDataVO.setPassword(null);
        return userDataVO;
    }

    /**
     * 修改用户信息
     * @param userDataVO
     */
    public void updateUserData(UserDataVO userDataVO,int userId) {
        User user = new User();
        BeanUtils.copyProperties(userDataVO, user);
        user.setId(userId);
        if (userDataVO.getPassword()!=null) {
            user.setPassword(DigestUtils.md5DigestAsHex(userDataVO.getPassword().getBytes()));
        }
        userMapper.updateById(user);
    }

    /**
     * 用户注册
     * @param userRegisterDTO
     */
    public void register(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername, userRegisterDTO.getUsername());
        //1、根据用户名查询数据库中的数据
        int count = Math.toIntExact(userMapper.selectCount(lqw));
        if (count > 0) {
            throw new AccountHasExistedException(MessageConstant.ACCOUNT_HAS_EXISTED);
        }
        user.setPassword(DigestUtils.md5DigestAsHex(userRegisterDTO.getPassword().getBytes()));
        userMapper.insert(user);
    }

    /**
     * 用户修改密码
     * @param userRegisterDTO
     */
    public void updatePassword(UserRegisterDTO userRegisterDTO) {
        userRegisterDTO.setPassword(DigestUtils.md5DigestAsHex(userRegisterDTO.getPassword().getBytes()));
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", userRegisterDTO.getUsername())
                .set("password",userRegisterDTO.getPassword()); // 设置要更新的字段
        userMapper.update(updateWrapper);
    }

    /**
     * 获取验证码
     * @param emailDTO
     * @return
     */
    public String getCode(EmailDTO emailDTO) {
        String code = GetCode.getSixRandom();
        mailUtil.sendVerificationCode(emailDTO.getEmail(),code);
        return code;
    }

}
