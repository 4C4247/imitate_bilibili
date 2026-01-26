package com.example.bilibili.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论表
 */
@Data
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 内容
     */
    private String content;

    /**
     * 父评论id
     */
    private Long parentId;

    /**
     * 点赞次数
     */
    private Long likeCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
