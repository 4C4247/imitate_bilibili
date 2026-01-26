package com.example.bilibili.service.impl;

import com.example.bilibili.entity.Comment;
import com.example.bilibili.service.CommentService;
import com.example.bilibili.service.VideoService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 评论服务实现类
 */
@Service
public class CommentServiceImpl implements CommentService {

    // 模拟评论数据存储
    private static final ConcurrentHashMap<Long, Comment> comments = new ConcurrentHashMap<>();
    private static final AtomicLong nextId = new AtomicLong(1);

    @Autowired
    private VideoService videoService;

    @Override
    public ResponseUtil<Comment> publishComment(Comment comment) {
        // 设置默认值
        comment.setId(nextId.getAndIncrement());
        comment.setLikeCount(0L);

        // 保存评论
        comments.put(comment.getId(), comment);

        // 增加视频评论次数
        videoService.incrementCommentCount(comment.getVideoId());

        return ResponseUtil.success("评论成功", comment);
    }

    @Override
    public ResponseUtil<List<Comment>> getCommentsByVideoId(Long videoId, Integer page, Integer size) {
        List<Comment> videoComments = new ArrayList<>();
        for (Comment comment : comments.values()) {
            if (comment.getVideoId().equals(videoId)) {
                videoComments.add(comment);
            }
        }
        return ResponseUtil.success(videoComments);
    }

    @Override
    public ResponseUtil<List<Comment>> getCommentsByUserId(Long userId, Integer page, Integer size) {
        List<Comment> userComments = new ArrayList<>();
        for (Comment comment : comments.values()) {
            if (comment.getUserId().equals(userId)) {
                userComments.add(comment);
            }
        }
        return ResponseUtil.success(userComments);
    }

    @Override
    public ResponseUtil<List<Comment>> getCommentsByParentId(Long parentId, Integer page, Integer size) {
        List<Comment> replyComments = new ArrayList<>();
        for (Comment comment : comments.values()) {
            if (comment.getParentId().equals(parentId)) {
                replyComments.add(comment);
            }
        }
        return ResponseUtil.success(replyComments);
    }

    @Override
    public ResponseUtil<Void> incrementLikeCount(Long commentId) {
        Comment comment = comments.get(commentId);
        if (comment != null) {
            comment.setLikeCount(comment.getLikeCount() + 1);
        }
        return ResponseUtil.success(null);
    }

    @Override
    public ResponseUtil<Void> deleteComment(Long commentId) {
        comments.remove(commentId);
        return ResponseUtil.success(null);
    }

}
