package com.example.bilibili.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 推荐视频实体类
 */
@Data
@TableName("video_recommend")
public class VideoRecommend {
    /**
     * 推荐视频ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 视频ID
     */
    private Long videoId;

    /**
     * 推荐类型：hot, new, follow等
     */
    private String recommendType;

    /**
     * 推荐分数
     */
    private Double score;

    /**
     * 推荐排名
     */
    private Integer rank;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
