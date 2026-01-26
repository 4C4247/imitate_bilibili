package com.example.bilibili.service;

import com.example.bilibili.entity.Video;
import com.example.bilibili.utils.ResponseUtil;

import java.util.List;

/**
 * 视频服务接口
 */
public interface VideoService {

    /**
     * 上传视频
     * @param video 视频信息
     * @return 上传结果
     */
    ResponseUtil<Video> uploadVideo(Video video);

    /**
     * 根据视频ID查询视频
     * @param videoId 视频ID
     * @return 视频信息
     */
    Video getVideoById(Long videoId);

    /**
     * 根据分类ID查询视频列表
     * @param categoryId 分类ID
     * @param page 页码
     * @param size 每页大小
     * @return 视频列表
     */
    ResponseUtil<List<Video>> getVideosByCategoryId(Long categoryId, Integer page, Integer size);

    /**
     * 根据用户ID查询视频列表
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 视频列表
     */
    ResponseUtil<List<Video>> getVideosByUserId(Long userId, Integer page, Integer size);

    /**
     * 增加视频观看次数
     * @param videoId 视频ID
     * @return 操作结果
     */
    ResponseUtil<Void> incrementViewCount(Long videoId);

    /**
     * 增加视频点赞次数
     * @param videoId 视频ID
     * @return 操作结果
     */
    ResponseUtil<Void> incrementLikeCount(Long videoId);

    /**
     * 增加视频评论次数
     * @param videoId 视频ID
     * @return 操作结果
     */
    ResponseUtil<Void> incrementCommentCount(Long videoId);

    /**
     * 更新视频信息
     * @param video 视频信息
     * @return 更新结果
     */
    ResponseUtil<Video> updateVideo(Video video);

    /**
     * 删除视频
     * @param videoId 视频ID
     * @return 删除结果
     */
    ResponseUtil<Void> deleteVideo(Long videoId);

}
