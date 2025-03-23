package com.xpxp.controller;

import com.xpxp.config.JwtProperties;
import com.xpxp.constant.JwtClaimsConstant;
import com.xpxp.pojo.DTO.DataRequestDTO;
import com.xpxp.pojo.DTO.EmailDTO;
import com.xpxp.pojo.DTO.UserLoginDTO;
import com.xpxp.pojo.DTO.UserRegisterDTO;
import com.xpxp.pojo.VO.DataRequestVO;
import com.xpxp.pojo.VO.UserLoginVO;
import com.xpxp.pojo.entity.User;
import com.xpxp.result.Result;
import com.xpxp.service.UserService;
import com.xpxp.utils.JwtUtil;
import com.xpxp.utils.LoginControllUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 登录相关逻辑
 */
@RestController
@Slf4j
@RequestMapping("api/users")
public class LoginContorller {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginControllUtil loginControllUtil;

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录：{}", userLoginDTO);

        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .imageUrl(user.getImage())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("用户注册{}", userRegisterDTO);
        userService.register(userRegisterDTO);
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PostMapping("/update")
    public Result updatePassword(@RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("用户修改密码{}", userRegisterDTO);
        userService.updatePassword(userRegisterDTO);
        return Result.success();
    }

    /**
     * 获取验证码
     */
    @PostMapping("/code")
    public Result getCode(@RequestBody EmailDTO emailDTO) {
        log.info("获取验证码：{}", emailDTO);
        String code = userService.getCode(emailDTO);
        String identity = "web2023-21"+emailDTO.getEmail();
        redisTemplate.opsForValue().set(identity, code, 2, TimeUnit.MINUTES); // 将验证码存入 Redis，设置过期时间为 2 分钟
        return Result.success();
    }

    /**
     * 校验验证码
     */
    @PostMapping("/checkcode")
    public Result check(@RequestBody EmailDTO emailDTO) {
        log.info("校验验证码{}", emailDTO);
        String code = emailDTO.getCode();
        String identity ="web2023-21"+ emailDTO.getEmail();
        // 从 Redis 中获取存储的验证码
        String storedCode = (String) redisTemplate.opsForValue().get(identity);
        if (code.equals(storedCode)) {
            return Result.success();
        }else return Result.error("验证码错误！");
    }

    @GetMapping("/getValidateCode")
    public Result<DataRequestVO> getValidateCode() {
        log.info("获得图片验证码{}");
        DataRequestVO dataRequestVO = new DataRequestVO();
        String identity ="web2023-21"+ loginControllUtil.generateValidateCodeId();
        String code = loginControllUtil.getRandomCode();
        String imgstr = loginControllUtil.getValidateCodeImage(code);
        dataRequestVO.setImgCode(imgstr);
        dataRequestVO.setIdentity(identity);
        redisTemplate.opsForValue().set(identity, code, 2, TimeUnit.MINUTES);
        return Result.success(dataRequestVO);
    }

    @PostMapping("/testValidateInfo")
    public Result testValidateInfo(@RequestBody DataRequestDTO dataRequestDTO) {
        log.info("校验验证码：{}", dataRequestDTO);
        String code = (String) redisTemplate.opsForValue().get(dataRequestDTO.getIdentity());
        if(code == null) {
            return Result.error("验证码已过期");
        }else if(dataRequestDTO.getVerifycode().equals(code)) {
            return Result.success();
        }else return Result.error("验证码错误！");
    }

}
