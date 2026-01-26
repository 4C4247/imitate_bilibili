package com.example.bilibili.controller;

import com.example.bilibili.entity.VideoCollection;
import com.example.bilibili.service.CollectionService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 收藏控制器
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    /**
     * 收藏视频
     * @param userId 用户ID
     * @param videoId 视频ID
     * @return 收藏结果
     */
    @PostMapping
    public ResponseUtil<VideoCollection> collectVideo(@RequestParam Long userId, @RequestParam Long videoId) {
        return collectionService.collectVideo(userId, videoId);
    }

    /**
     * 取消收藏视频
     * @param userId 用户ID
     * @param videoId 视频ID
     * @return 取消收藏结果
     */
    @DeleteMapping
    public ResponseUtil<Void> cancelCollectVideo(@RequestParam Long userId, @RequestParam Long videoId) {
        return collectionService.cancelCollectVideo(userId, videoId);
    }

    /**
     * 根据用户ID查询收藏列表
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 收藏列表
     */
    @GetMapping("/user/{userId}")
    public ResponseUtil<List<VideoCollection>> getCollectionsByUserId(@PathVariable Long userId, 
                                                               @RequestParam(defaultValue = "1") Integer page, 
                                                               @RequestParam(defaultValue = "10") Integer size) {
        return collectionService.getCollectionsByUserId(userId, page, size);
    }

    /**
     * 检查用户是否已收藏视频
     * @param userId 用户ID
     * @param videoId 视频ID
     * @return 是否已收藏
     */
    @GetMapping("/check")
    public ResponseUtil<Boolean> checkCollectionExists(@RequestParam Long userId, @RequestParam Long videoId) {
        boolean exists = collectionService.checkCollectionExists(userId, videoId);
        return ResponseUtil.success(exists);
    }

}
