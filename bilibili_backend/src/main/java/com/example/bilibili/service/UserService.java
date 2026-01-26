package com.example.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bilibili.entity.User;
import com.example.bilibili.utils.ResponseUtil;

import java.util.Map;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param user 用户信息
     * @return 注册结果
     */
    ResponseUtil<User> register(User user);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    ResponseUtil<Map<String, Object>> login(String username, String password);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新结果
     */
    ResponseUtil<User> updateUser(User user);

    /**
     * 检查用户名是否已存在
     * @param username 用户名
     * @return 是否存在
     */
    boolean checkUsernameExists(String username);

    /**
     * 检查邮箱是否已存在
     * @param email 邮箱
     * @return 是否存在
     */
    boolean checkEmailExists(String email);

}
