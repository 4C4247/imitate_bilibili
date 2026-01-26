package com.example.bilibili.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bilibili.entity.Video;
import org.apache.ibatis.annotations.Mapper;

/**
 * 视频表Mapper
 */
@Mapper
public interface VideoMapper extends BaseMapper<Video> {

}
