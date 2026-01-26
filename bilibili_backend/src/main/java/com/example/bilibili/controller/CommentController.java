package com.example.bilibili.controller;

import com.example.bilibili.entity.Comment;
import com.example.bilibili.service.CommentService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论控制器
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 发表评论
     * @param comment 评论信息
     * @return 发表结果
     */
    @PostMapping
    public ResponseUtil<Comment> publishComment(@RequestBody Comment comment) {
        return commentService.publishComment(comment);
    }

    /**
     * 根据视频ID查询评论列表
     * @param videoId 视频ID
     * @param page 页码
     * @param size 每页大小
     * @return 评论列表
     */
    @GetMapping("/video/{videoId}")
    public ResponseUtil<List<Comment>> getCommentsByVideoId(@PathVariable Long videoId, 
                                                           @RequestParam(defaultValue = "1") Integer page, 
                                                           @RequestParam(defaultValue = "10") Integer size) {
        return commentService.getCommentsByVideoId(videoId, page, size);
    }

    /**
     * 根据用户ID查询评论列表
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 评论列表
     */
    @GetMapping("/user/{userId}")
    public ResponseUtil<List<Comment>> getCommentsByUserId(@PathVariable Long userId, 
                                                         @RequestParam(defaultValue = "1") Integer page, 
                                                         @RequestParam(defaultValue = "10") Integer size) {
        return commentService.getCommentsByUserId(userId, page, size);
    }

    /**
     * 根据父评论ID查询子评论列表
     * @param parentId 父评论ID
     * @param page 页码
     * @param size 每页大小
     * @return 子评论列表
     */
    @GetMapping("/parent/{parentId}")
    public ResponseUtil<List<Comment>> getCommentsByParentId(@PathVariable Long parentId, 
                                                            @RequestParam(defaultValue = "1") Integer page, 
                                                            @RequestParam(defaultValue = "10") Integer size) {
        return commentService.getCommentsByParentId(parentId, page, size);
    }

    /**
     * 增加评论点赞次数
     * @param commentId 评论ID
     * @return 操作结果
     */
    @PutMapping("/like/{commentId}")
    public ResponseUtil<Void> incrementLikeCount(@PathVariable Long commentId) {
        return commentService.incrementLikeCount(commentId);
    }

    /**
     * 删除评论
     * @param commentId 评论ID
     * @return 删除结果
     */
    @DeleteMapping("/{commentId}")
    public ResponseUtil<Void> deleteComment(@PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }

}
