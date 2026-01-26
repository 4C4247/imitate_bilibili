package com.example.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bilibili.entity.VideoCollection;
import com.example.bilibili.mapper.CollectionMapper;
import com.example.bilibili.service.CollectionService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 收藏服务实现类
 */
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, VideoCollection> implements CollectionService {

    @Override
    public ResponseUtil<VideoCollection> collectVideo(Long userId, Long videoId) {
        // 检查是否已收藏
        if (checkCollectionExists(userId, videoId)) {
            return ResponseUtil.error("已收藏该视频");
        }

        // 创建收藏记录
        VideoCollection collection = new VideoCollection();
        collection.setUserId(userId);
        collection.setVideoId(videoId);
        collection.setCreateTime(new Date());

        // 保存收藏记录
        save(collection);

        return ResponseUtil.success("收藏成功", collection);
    }

    @Override
    public ResponseUtil<Void> cancelCollectVideo(Long userId, Long videoId) {
        // 检查是否已收藏
        VideoCollection collection = lambdaQuery()
                .eq(VideoCollection::getUserId, userId)
                .eq(VideoCollection::getVideoId, videoId)
                .one();
        if (collection == null) {
            return ResponseUtil.error("未收藏该视频");
        }

        // 删除收藏记录
        removeById(collection.getId());

        return ResponseUtil.success(null);
    }

    @Override
    public ResponseUtil<List<VideoCollection>> getCollectionsByUserId(Long userId, Integer page, Integer size) {
        List<VideoCollection> userCollections = lambdaQuery()
                .eq(VideoCollection::getUserId, userId)
                .orderByDesc(VideoCollection::getCreateTime)
                .list();
        return ResponseUtil.success(userCollections);
    }

    @Override
    public boolean checkCollectionExists(Long userId, Long videoId) {
        return lambdaQuery()
                .eq(VideoCollection::getUserId, userId)
                .eq(VideoCollection::getVideoId, videoId)
                .exists();
    }

}
