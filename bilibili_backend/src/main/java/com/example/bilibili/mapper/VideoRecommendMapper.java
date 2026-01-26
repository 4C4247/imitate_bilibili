package com.example.bilibili.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bilibili.entity.VideoRecommend;

import java.util.List;

/**
 * 推荐视频Mapper接口
 */
public interface VideoRecommendMapper extends BaseMapper<VideoRecommend> {
    /**
     * 根据推荐类型获取推荐视频列表
     * @param recommendType 推荐类型
     * @param limit 限制数量
     * @return 推荐视频列表
     */
    List<VideoRecommend> getRecommendVideosByType(String recommendType, Integer limit);

    /**
     * 根据推荐类型和排名范围获取推荐视频列表
     * @param recommendType 推荐类型
     * @param startRank 起始排名
     * @param endRank 结束排名
     * @return 推荐视频列表
     */
    List<VideoRecommend> getRecommendVideosByRank(String recommendType, Integer startRank, Integer endRank);
}
