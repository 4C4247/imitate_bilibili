package com.example.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bilibili.entity.VideoCollection;
import com.example.bilibili.utils.ResponseUtil;

import java.util.List;

/**
 * 收藏服务接口
 */
public interface CollectionService extends IService<VideoCollection> {

    /**
     * 收藏视频
     * @param userId 用户ID
     * @param videoId 视频ID
     * @return 收藏结果
     */
    ResponseUtil<VideoCollection> collectVideo(Long userId, Long videoId);

    /**
     * 取消收藏视频
     * @param userId 用户ID
     * @param videoId 视频ID
     * @return 取消收藏结果
     */
    ResponseUtil<Void> cancelCollectVideo(Long userId, Long videoId);

    /**
     * 根据用户ID查询收藏列表
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 收藏列表
     */
    ResponseUtil<List<VideoCollection>> getCollectionsByUserId(Long userId, Integer page, Integer size);

    /**
     * 检查用户是否已收藏视频
     * @param userId 用户ID
     * @param videoId 视频ID
     * @return 是否已收藏
     */
    boolean checkCollectionExists(Long userId, Long videoId);

}
