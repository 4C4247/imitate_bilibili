package com.example.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bilibili.entity.User;
import com.example.bilibili.mapper.UserMapper;
import com.example.bilibili.service.UserService;
import com.example.bilibili.utils.JWTUtil;
import com.example.bilibili.utils.PasswordUtil;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public ResponseUtil<User> register(User user) {
        // 检查用户名是否已存在
        if (checkUsernameExists(user.getUsername())) {
            return ResponseUtil.error("用户名已存在");
        }

        // 检查邮箱是否已存在
        if (checkEmailExists(user.getEmail())) {
            return ResponseUtil.error("邮箱已存在");
        }

        // 密码加密
        user.setPassword(PasswordUtil.encryptPassword(user.getPassword()));

        // 设置默认值
        user.setNickname(user.getUsername()); // 默认昵称为用户名
        user.setAvatar(null); // 默认头像
        user.setVipStatus(0); // 默认非会员

        // 保存用户
        save(user);

        return ResponseUtil.success("注册成功", user);
    }

    @Override
    public ResponseUtil<Map<String, Object>> login(String username, String password) {
        // 根据用户名查询用户
        User user = lambdaQuery().eq(User::getUsername, username).one();
        if (user == null) {
            return ResponseUtil.error("该账号未注册");
        }

        // 验证密码
        if (!PasswordUtil.validatePassword(password, user.getPassword())) {
            return ResponseUtil.error("账号或密码错误");
        }

        // 生成JWT令牌
        String token = jwtUtil.generateToken(user.getId());

        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", user);

        return ResponseUtil.success("登录成功", result);
    }

    @Override
    public ResponseUtil<User> updateUser(User user) {
        // 检查用户是否存在
        if (getById(user.getId()) == null) {
            return ResponseUtil.error("用户不存在");
        }

        // 检查用户名是否已存在（排除当前用户）
        User existingUserByUsername = lambdaQuery().eq(User::getUsername, user.getUsername()).ne(User::getId, user.getId()).one();
        if (existingUserByUsername != null) {
            return ResponseUtil.error("用户名已存在");
        }

        // 检查邮箱是否已存在（排除当前用户）
        User existingUserByEmail = lambdaQuery().eq(User::getEmail, user.getEmail()).ne(User::getId, user.getId()).one();
        if (existingUserByEmail != null) {
            return ResponseUtil.error("邮箱已存在");
        }

        // 更新用户信息
        updateById(user);

        return ResponseUtil.success("更新成功", user);
    }

    @Override
    public boolean checkUsernameExists(String username) {
        return lambdaQuery().eq(User::getUsername, username).exists();
    }

    @Override
    public boolean checkEmailExists(String email) {
        return lambdaQuery().eq(User::getEmail, email).exists();
    }

}
