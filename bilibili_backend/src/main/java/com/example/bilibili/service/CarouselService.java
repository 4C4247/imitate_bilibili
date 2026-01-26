package com.example.bilibili.service;

import com.example.bilibili.entity.Carousel;
import com.example.bilibili.utils.ResponseUtil;

import java.util.List;

/**
 * 轮播图服务接口
 */
public interface CarouselService {

    /**
     * 获取启用状态的轮播图列表
     * @return 轮播图列表
     */
    ResponseUtil<List<Carousel>> getEnabledCarousels();

    /**
     * 创建轮播图
     * @param carousel 轮播图信息
     * @return 创建结果
     */
    ResponseUtil<Carousel> createCarousel(Carousel carousel);

    /**
     * 更新轮播图
     * @param carousel 轮播图信息
     * @return 更新结果
     */
    ResponseUtil<Carousel> updateCarousel(Carousel carousel);

    /**
     * 删除轮播图
     * @param carouselId 轮播图ID
     * @return 删除结果
     */
    ResponseUtil<Void> deleteCarousel(Long carouselId);

}
