package com.example.bilibili.controller;

import com.example.bilibili.service.VideoRecommendService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 推荐视频控制器
 */
@RestController
@RequestMapping("/video/recommend")
public class VideoRecommendController {

    @Autowired
    private VideoRecommendService videoRecommendService;

    /**
     * 根据推荐类型获取推荐视频列表
     * @param type 推荐类型
     * @param limit 限制数量
     * @return 推荐视频列表
     */
    @GetMapping("/type/{type}")
    public ResponseUtil<?> getRecommendVideosByType(@PathVariable String type, 
                                                  @RequestParam(defaultValue = "6") Integer limit) {
        return videoRecommendService.getRecommendVideosByType(type, limit);
    }

    /**
     * 获取热门推荐视频
     * @param limit 限制数量
     * @return 热门推荐视频列表
     */
    @GetMapping("/hot")
    public ResponseUtil<?> getHotRecommendVideos(@RequestParam(defaultValue = "6") Integer limit) {
        return videoRecommendService.getHotRecommendVideos(limit);
    }

    /**
     * 获取最新推荐视频
     * @param limit 限制数量
     * @return 最新推荐视频列表
     */
    @GetMapping("/new")
    public ResponseUtil<?> getNewRecommendVideos(@RequestParam(defaultValue = "6") Integer limit) {
        return videoRecommendService.getNewRecommendVideos(limit);
    }

    /**
     * 获取关注推荐视频
     * @param limit 限制数量
     * @return 关注推荐视频列表
     */
    @GetMapping("/follow")
    public ResponseUtil<?> getFollowRecommendVideos(@RequestParam(defaultValue = "6") Integer limit) {
        return videoRecommendService.getFollowRecommendVideos(limit);
    }
}
