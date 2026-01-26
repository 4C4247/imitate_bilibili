package com.example.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bilibili.entity.VideoRecommend;
import com.example.bilibili.mapper.VideoRecommendMapper;
import com.example.bilibili.service.VideoRecommendService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 推荐视频服务实现类
 */
@Service
public class VideoRecommendServiceImpl extends ServiceImpl<VideoRecommendMapper, VideoRecommend> implements VideoRecommendService {

    @Autowired
    private VideoRecommendMapper videoRecommendMapper;

    @Override
    public ResponseUtil<List<VideoRecommend>> getRecommendVideosByType(String recommendType, Integer limit) {
        try {
            List<VideoRecommend> videoRecommends = videoRecommendMapper.getRecommendVideosByType(recommendType, limit);
            return ResponseUtil.success("获取推荐视频成功", videoRecommends);
        } catch (Exception e) {
            return ResponseUtil.error("获取推荐视频失败");
        }
    }

    @Override
    public ResponseUtil<List<VideoRecommend>> getHotRecommendVideos(Integer limit) {
        return getRecommendVideosByType("hot", limit);
    }

    @Override
    public ResponseUtil<List<VideoRecommend>> getNewRecommendVideos(Integer limit) {
        return getRecommendVideosByType("new", limit);
    }

    @Override
    public ResponseUtil<List<VideoRecommend>> getFollowRecommendVideos(Integer limit) {
        return getRecommendVideosByType("follow", limit);
    }
}
