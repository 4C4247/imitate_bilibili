package com.example.bilibili.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 视频表
 */
@Data
public class Video implements Serializable {

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
     * 标题
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 封面
     */
    private String cover;

    /**
     * 视频地址
     */
    private String url;

    /**
     * 时长(秒)
     */
    private Integer duration;

    /**
     * 观看次数
     */
    private Long viewCount;

    /**
     * 点赞次数
     */
    private Long likeCount;

    /**
     * 评论次数
     */
    private Long commentCount;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 标签
     */
    private String tags;

    /**
     * 状态：0-审核中，1-已发布，2-已下架
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
