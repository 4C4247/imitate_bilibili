package com.example.bilibili.service;

import com.example.bilibili.entity.Activity;
import com.example.bilibili.utils.ResponseUtil;

import java.util.List;

/**
 * 动态服务接口
 */
public interface ActivityService {

    /**
     * 创建动态
     * @param activity 动态信息
     * @return 创建结果
     */
    ResponseUtil<Activity> createActivity(Activity activity);

    /**
     * 根据用户ID查询动态列表
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 动态列表
     */
    ResponseUtil<List<Activity>> getActivitiesByUserId(Long userId, Integer page, Integer size);

}
