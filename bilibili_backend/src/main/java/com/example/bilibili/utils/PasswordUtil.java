package com.example.bilibili.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * 密码加密工具类
 */
public class PasswordUtil {

    /**
     * 密码加密
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 验证密码
     * @param rawPassword 原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean validatePassword(String rawPassword, String encodedPassword) {
        return encodedPassword.equals(encryptPassword(rawPassword));
    }

}
