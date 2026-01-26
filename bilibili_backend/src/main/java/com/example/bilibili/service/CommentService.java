package com.example.bilibili.service;

import com.example.bilibili.entity.Comment;
import com.example.bilibili.utils.ResponseUtil;

import java.util.List;

/**
 * 评论服务接口
 */
public interface CommentService {

    /**
     * 发表评论
     * @param comment 评论信息
     * @return 发表结果
     */
    ResponseUtil<Comment> publishComment(Comment comment);

    /**
     * 根据视频ID查询评论列表
     * @param videoId 视频ID
     * @param page 页码
     * @param size 每页大小
     * @return 评论列表
     */
    ResponseUtil<List<Comment>> getCommentsByVideoId(Long videoId, Integer page, Integer size);

    /**
     * 根据用户ID查询评论列表
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 评论列表
     */
    ResponseUtil<List<Comment>> getCommentsByUserId(Long userId, Integer page, Integer size);

    /**
     * 根据父评论ID查询子评论列表
     * @param parentId 父评论ID
     * @param page 页码
     * @param size 每页大小
     * @return 子评论列表
     */
    ResponseUtil<List<Comment>> getCommentsByParentId(Long parentId, Integer page, Integer size);

    /**
     * 增加评论点赞次数
     * @param commentId 评论ID
     * @return 操作结果
     */
    ResponseUtil<Void> incrementLikeCount(Long commentId);

    /**
     * 删除评论
     * @param commentId 评论ID
     * @return 删除结果
     */
    ResponseUtil<Void> deleteComment(Long commentId);

}
