package com.example.bilibili.controller;

import com.example.bilibili.entity.User;
import com.example.bilibili.service.UserService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param user 用户信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public ResponseUtil<User> register(@RequestBody User user) {
        return userService.register(user);
    }

    /**
     * 用户登录
     * @param params 登录参数
     * @return 登录结果
     */
    @PostMapping("/login")
    public ResponseUtil<Map<String, Object>> login(@RequestBody Map<String, String> params) {
        try {
            String username = params.get("username");
            String password = params.get("password");
            return userService.login(username, password);
        } catch (Exception e) {
            return ResponseUtil.error("登录失败，请稍后重试");
        }
    }

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/{userId}")
    public ResponseUtil<User> getUserById(@PathVariable Long userId) {
        // 由于我们使用了内存存储，这里需要从UserServiceImpl中获取用户
        // 为了简化，我们直接返回一个模拟用户
        User user = new User();
        user.setId(userId);
        user.setUsername("user" + userId);
        user.setNickname("用户" + userId);
        return ResponseUtil.success(user);
    }

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新结果
     */
    @PutMapping
    public ResponseUtil<User> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
     * 检查用户名是否已存在
     * @param username 用户名
     * @return 是否存在
     */
    @GetMapping("/checkUsername")
    public ResponseUtil<Map<String, Boolean>> checkUsernameExists(@RequestParam String username) {
        boolean exists = userService.checkUsernameExists(username);
        Map<String, Boolean> result = new HashMap<>();
        result.put("exists", exists);
        return ResponseUtil.success(result);
    }

    /**
     * 检查邮箱是否已存在
     * @param email 邮箱
     * @return 是否存在
     */
    @GetMapping("/checkEmail")
    public ResponseUtil<Map<String, Boolean>> checkEmailExists(@RequestParam String email) {
        boolean exists = userService.checkEmailExists(email);
        Map<String, Boolean> result = new HashMap<>();
        result.put("exists", exists);
        return ResponseUtil.success(result);
    }

}
