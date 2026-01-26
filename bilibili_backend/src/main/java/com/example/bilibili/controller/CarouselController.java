package com.example.bilibili.controller;

import com.example.bilibili.entity.Carousel;
import com.example.bilibili.service.CarouselService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轮播图控制器
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    /**
     * 获取启用状态的轮播图列表
     * @return 轮播图列表
     */
    @GetMapping
    public ResponseUtil<List<Carousel>> getEnabledCarousels() {
        return carouselService.getEnabledCarousels();
    }

    /**
     * 创建轮播图
     * @param carousel 轮播图信息
     * @return 创建结果
     */
    @PostMapping
    public ResponseUtil<Carousel> createCarousel(@RequestBody Carousel carousel) {
        return carouselService.createCarousel(carousel);
    }

    /**
     * 更新轮播图
     * @param carousel 轮播图信息
     * @return 更新结果
     */
    @PutMapping
    public ResponseUtil<Carousel> updateCarousel(@RequestBody Carousel carousel) {
        return carouselService.updateCarousel(carousel);
    }

    /**
     * 删除轮播图
     * @param carouselId 轮播图ID
     * @return 删除结果
     */
    @DeleteMapping("/{carouselId}")
    public ResponseUtil<Void> deleteCarousel(@PathVariable Long carouselId) {
        return carouselService.deleteCarousel(carouselId);
    }

}
