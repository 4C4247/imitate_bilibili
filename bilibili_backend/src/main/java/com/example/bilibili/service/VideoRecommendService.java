package com.example.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bilibili.entity.VideoRecommend;
import com.example.bilibili.utils.ResponseUtil;

import java.util.List;

/**
 * 推荐视频服务接口
 */
public interface VideoRecommendService extends IService<VideoRecommend> {
    /**
     * 根据推荐类型获取推荐视频列表
     * @param recommendType 推荐类型
     * @param limit 限制数量
     * @return 推荐视频列表
     */
    ResponseUtil<List<VideoRecommend>> getRecommendVideosByType(String recommendType, Integer limit);

    /**
     * 获取热门推荐视频
     * @param limit 限制数量
     * @return 热门推荐视频列表
     */
    ResponseUtil<List<VideoRecommend>> getHotRecommendVideos(Integer limit);

    /**
     * 获取最新推荐视频
     * @param limit 限制数量
     * @return 最新推荐视频列表
     */
    ResponseUtil<List<VideoRecommend>> getNewRecommendVideos(Integer limit);

    /**
     * 获取关注推荐视频
     * @param limit 限制数量
     * @return 关注推荐视频列表
     */
    ResponseUtil<List<VideoRecommend>> getFollowRecommendVideos(Integer limit);
}
