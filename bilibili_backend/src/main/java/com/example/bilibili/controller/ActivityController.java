package com.example.bilibili.controller;

import com.example.bilibili.entity.Activity;
import com.example.bilibili.service.ActivityService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 动态控制器
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * 创建动态
     * @param activity 动态信息
     * @return 创建结果
     */
    @PostMapping
    public ResponseUtil<Activity> createActivity(@RequestBody Activity activity) {
        return activityService.createActivity(activity);
    }

    /**
     * 根据用户ID查询动态列表
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 动态列表
     */
    @GetMapping("/user/{userId}")
    public ResponseUtil<List<Activity>> getActivitiesByUserId(@PathVariable Long userId, 
                                                           @RequestParam(defaultValue = "1") Integer page, 
                                                           @RequestParam(defaultValue = "10") Integer size) {
        return activityService.getActivitiesByUserId(userId, page, size);
    }

}
