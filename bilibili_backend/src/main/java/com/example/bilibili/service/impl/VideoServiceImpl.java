package com.example.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bilibili.entity.Video;
import com.example.bilibili.entity.VideoRecommend;
import com.example.bilibili.mapper.VideoMapper;
import com.example.bilibili.service.VideoRecommendService;
import com.example.bilibili.service.VideoService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 视频服务实现类
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VideoRecommendService videoRecommendService;

    @Override
    public ResponseUtil<Video> uploadVideo(Video video) {
        try {
            // 设置默认值
            video.setViewCount(0L);
            video.setLikeCount(0L);
            video.setCommentCount(0L);
            video.setStatus(1); // 默认已发布
            video.setCreateTime(new Date());
            video.setUpdateTime(new Date());

            // 保存视频到数据库
            this.save(video);

            // 自动将视频添加到推荐列表
            VideoRecommend videoRecommend = new VideoRecommend();
            videoRecommend.setVideoId(video.getId());
            videoRecommend.setRecommendType("hot");
            videoRecommend.setScore(90.0); // 默认分数
            videoRecommend.setRank(1); // 默认排名
            videoRecommend.setCreateTime(new Date());
            videoRecommend.setUpdateTime(new Date());

            // 保存推荐视频
            videoRecommendService.save(videoRecommend);

            return ResponseUtil.success("上传成功", video);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.error("上传失败：" + e.getMessage());
        }
    }

    @Override
    public Video getVideoById(Long videoId) {
        return this.getById(videoId);
    }

    @Override
    public ResponseUtil<List<Video>> getVideosByCategoryId(Long categoryId, Integer page, Integer size) {
        try {
            // 这里可以根据需要实现分页查询
            List<Video> categoryVideos = new ArrayList<>();
            // 实际项目中应该使用MyBatis Plus的条件查询
            return ResponseUtil.success(categoryVideos);
        } catch (Exception e) {
            return ResponseUtil.error("获取视频列表失败");
        }
    }

    @Override
    public ResponseUtil<List<Video>> getVideosByUserId(Long userId, Integer page, Integer size) {
        try {
            // 这里可以根据需要实现分页查询
            List<Video> userVideos = new ArrayList<>();
            // 实际项目中应该使用MyBatis Plus的条件查询
            return ResponseUtil.success(userVideos);
        } catch (Exception e) {
            return ResponseUtil.error("获取视频列表失败");
        }
    }

    @Override
    public ResponseUtil<Void> incrementViewCount(Long videoId) {
        try {
            Video video = this.getById(videoId);
            if (video != null) {
                video.setViewCount(video.getViewCount() + 1);
                this.updateById(video);
            }
            return ResponseUtil.success(null);
        } catch (Exception e) {
            return ResponseUtil.error("更新观看次数失败");
        }
    }

    @Override
    public ResponseUtil<Void> incrementLikeCount(Long videoId) {
        try {
            Video video = this.getById(videoId);
            if (video != null) {
                video.setLikeCount(video.getLikeCount() + 1);
                this.updateById(video);
            }
            return ResponseUtil.success(null);
        } catch (Exception e) {
            return ResponseUtil.error("更新点赞次数失败");
        }
    }

    @Override
    public ResponseUtil<Void> incrementCommentCount(Long videoId) {
        try {
            Video video = this.getById(videoId);
            if (video != null) {
                video.setCommentCount(video.getCommentCount() + 1);
                this.updateById(video);
            }
            return ResponseUtil.success(null);
        } catch (Exception e) {
            return ResponseUtil.error("更新评论次数失败");
        }
    }

    @Override
    public ResponseUtil<Video> updateVideo(Video video) {
        try {
            video.setUpdateTime(new Date());
            this.updateById(video);
            return ResponseUtil.success("更新成功", video);
        } catch (Exception e) {
            return ResponseUtil.error("更新失败");
        }
    }

    @Override
    public ResponseUtil<Void> deleteVideo(Long videoId) {
        try {
            this.removeById(videoId);
            return ResponseUtil.success(null);
        } catch (Exception e) {
            return ResponseUtil.error("删除失败");
        }
    }

}
