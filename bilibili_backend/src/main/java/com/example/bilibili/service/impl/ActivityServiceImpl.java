package com.example.bilibili.service.impl;

import com.example.bilibili.entity.Activity;
import com.example.bilibili.service.ActivityService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 动态服务实现类
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    // 模拟动态数据存储
    private static final ConcurrentHashMap<Long, Activity> activities = new ConcurrentHashMap<>();
    private static final AtomicLong nextId = new AtomicLong(1);

    @Override
    public ResponseUtil<Activity> createActivity(Activity activity) {
        // 设置默认值
        activity.setId(nextId.getAndIncrement());

        // 保存动态
        activities.put(activity.getId(), activity);

        return ResponseUtil.success("创建成功", activity);
    }

    @Override
    public ResponseUtil<List<Activity>> getActivitiesByUserId(Long userId, Integer page, Integer size) {
        List<Activity> userActivities = new ArrayList<>();
        for (Activity activity : activities.values()) {
            if (activity.getUserId().equals(userId)) {
                userActivities.add(activity);
            }
        }
        return ResponseUtil.success(userActivities);
    }

}
