package com.example.bilibili.utils;

import lombok.Data;

/**
 * 响应结果工具类
 */
@Data
public class ResponseUtil<T> {

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 成功响应
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> ResponseUtil<T> success(T data) {
        ResponseUtil<T> response = new ResponseUtil<>();
        response.setCode(200);
        response.setMessage("success");
        response.setData(data);
        return response;
    }

    /**
     * 成功响应
     * @param message 响应消息
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> ResponseUtil<T> success(String message, T data) {
        ResponseUtil<T> response = new ResponseUtil<>();
        response.setCode(200);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    /**
     * 失败响应
     * @param code 状态码
     * @param message 响应消息
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> ResponseUtil<T> error(int code, String message) {
        ResponseUtil<T> response = new ResponseUtil<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    /**
     * 失败响应
     * @param message 响应消息
     * @param <T> 数据类型
     * @return 响应结果
     */
    public static <T> ResponseUtil<T> error(String message) {
        ResponseUtil<T> response = new ResponseUtil<>();
        response.setCode(400);
        response.setMessage(message);
        return response;
    }

}
