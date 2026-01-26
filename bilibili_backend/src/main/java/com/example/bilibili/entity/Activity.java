package com.example.bilibili.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 动态表
 */
@Data
public class Activity implements Serializable {

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
     * 动态类型：0-系统通知，1-视频更新，2-评论回复
     */
    private Integer type;

    /**
     * 内容
     */
    private String content;

    /**
     * 关联id
     */
    private Long relatedId;

    /**
     * 创建时间
     */
    private Date createTime;

}
