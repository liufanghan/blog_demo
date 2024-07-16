package org.lfh.blog_demo.service.impl;

import jakarta.annotation.Resource;
import org.lfh.blog_demo.entity.User;
import org.lfh.blog_demo.mapper.UserMapper;
import org.lfh.blog_demo.service.UserService;
import org.lfh.blog_demo.util.JWTUtil;
import org.lfh.blog_demo.util.Result;
import org.lfh.blog_demo.util.ResultEnum;
import org.lfh.blog_demo.util.UserHolder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import vo.param.UserParam;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    private boolean isValidEmail(String email) {
        // 简单的邮箱格式验证
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public Result register(UserParam userParam) {
        //1.判断用户填写数据是否完整且符合规范
        //2.判断邮箱是否被注册
        //3.无误则为用户注册，密码加密
        if (userParam.getUserName() != null && userParam.getEmail() != null && userParam.getPassword() != null && isValidEmail(userParam.getEmail())) {
            User user = userMapper.findByEmail(userParam.getEmail());
            if (user != null) {
                return Result.fail(ResultEnum.REGISTER_ERROR_2);
            }
            LocalDateTime dateTime = LocalDateTime.now();
            User user1 = new User();
            user1.setUserName(userParam.getUserName());
            user1.setPassword(BCrypt.hashpw(userParam.getPassword(), BCrypt.gensalt())); //BCrypt密码加密
            user1.setEmail(userParam.getEmail());
            user1.setCreateTime(dateTime);
            user1.setUpdateTime(dateTime);
            int i = userMapper.insertUser(user1);
            return i > 0 ? Result.success() : Result.fail(ResultEnum.REGISTER_ERROR_3);
        }
        return Result.fail(ResultEnum.REGISTER_ERROR_1);
    }

    @Override
    public Result login(UserParam userParam) {
        if (userParam.getEmail() != null && userParam.getPassword() != null && isValidEmail(userParam.getEmail())) {
            // User user = userMapper.findByEmailAndPassword(userParam);
            User user = userMapper.findByEmail(userParam.getEmail());
            System.out.println(userParam.getPassword());
            if (user != null && BCrypt.checkpw(userParam.getPassword(), user.getPassword())) {
                //生成token
                String token = JWTUtil.createJWT(user.getUserId());
                return Result.success(token);
            }
            return Result.fail(ResultEnum.LOGIN_ERROR_1);
        }
        return Result.fail(ResultEnum.LOGIN_ERROR_2);
    }

    @Override
    public Result me() {
        Integer userId = UserHolder.getUser();
        User user = userMapper.findByUserId(userId);
        return user != null ? Result.success(user) : Result.fail(ResultEnum.INTERCEPTOR_ERROR);
    }
}
