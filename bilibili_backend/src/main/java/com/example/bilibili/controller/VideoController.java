package com.example.bilibili.controller;

import com.example.bilibili.entity.Video;
import com.example.bilibili.service.VideoService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视频控制器
 */
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    /**
     * 上传视频
     * @param video 视频信息
     * @return 上传结果
     */
    @PostMapping
    public ResponseUtil<Video> uploadVideo(@RequestBody Video video) {
        return videoService.uploadVideo(video);
    }

    /**
     * 根据视频ID查询视频
     * @param videoId 视频ID
     * @return 视频信息
     */
    @GetMapping("/{videoId}")
    public ResponseUtil<Video> getVideoById(@PathVariable Long videoId) {
        Video video = videoService.getVideoById(videoId);
        return ResponseUtil.success(video);
    }

    /**
     * 根据分类ID查询视频列表
     * @param categoryId 分类ID
     * @param page 页码
     * @param size 每页大小
     * @return 视频列表
     */
    @GetMapping("/category/{categoryId}")
    public ResponseUtil<List<Video>> getVideosByCategoryId(@PathVariable Long categoryId, 
                                                          @RequestParam(defaultValue = "1") Integer page, 
                                                          @RequestParam(defaultValue = "10") Integer size) {
        return videoService.getVideosByCategoryId(categoryId, page, size);
    }

    /**
     * 根据用户ID查询视频列表
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 视频列表
     */
    @GetMapping("/user/{userId}")
    public ResponseUtil<List<Video>> getVideosByUserId(@PathVariable Long userId, 
                                                      @RequestParam(defaultValue = "1") Integer page, 
                                                      @RequestParam(defaultValue = "10") Integer size) {
        return videoService.getVideosByUserId(userId, page, size);
    }

    /**
     * 增加视频观看次数
     * @param videoId 视频ID
     * @return 操作结果
     */
    @PutMapping("/view/{videoId}")
    public ResponseUtil<Void> incrementViewCount(@PathVariable Long videoId) {
        return videoService.incrementViewCount(videoId);
    }

    /**
     * 增加视频点赞次数
     * @param videoId 视频ID
     * @return 操作结果
     */
    @PutMapping("/like/{videoId}")
    public ResponseUtil<Void> incrementLikeCount(@PathVariable Long videoId) {
        return videoService.incrementLikeCount(videoId);
    }

    /**
     * 更新视频信息
     * @param video 视频信息
     * @return 更新结果
     */
    @PutMapping
    public ResponseUtil<Video> updateVideo(@RequestBody Video video) {
        return videoService.updateVideo(video);
    }

    /**
     * 删除视频
     * @param videoId 视频ID
     * @return 删除结果
     */
    @DeleteMapping("/{videoId}")
    public ResponseUtil<Void> deleteVideo(@PathVariable Long videoId) {
        return videoService.deleteVideo(videoId);
    }

}
