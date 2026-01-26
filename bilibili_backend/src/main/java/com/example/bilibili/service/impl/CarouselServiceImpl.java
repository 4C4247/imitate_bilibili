package com.example.bilibili.service.impl;

import com.example.bilibili.entity.Carousel;
import com.example.bilibili.service.CarouselService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 轮播图服务实现类
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    // 模拟轮播图数据存储
    private static final ConcurrentHashMap<Long, Carousel> carousels = new ConcurrentHashMap<>();
    private static final AtomicLong nextId = new AtomicLong(1);

    @Override
    public ResponseUtil<List<Carousel>> getEnabledCarousels() {
        List<Carousel> enabledCarousels = new ArrayList<>();
        for (Carousel carousel : carousels.values()) {
            if (carousel.getStatus() == 1) {
                enabledCarousels.add(carousel);
            }
        }
        // 按顺序排序
        enabledCarousels.sort((c1, c2) -> c1.getOrder().compareTo(c2.getOrder()));
        return ResponseUtil.success(enabledCarousels);
    }

    @Override
    public ResponseUtil<Carousel> createCarousel(Carousel carousel) {
        // 设置默认值
        carousel.setId(nextId.getAndIncrement());
        carousel.setStatus(1); // 默认启用

        // 保存轮播图
        carousels.put(carousel.getId(), carousel);

        return ResponseUtil.success("创建成功", carousel);
    }

    @Override
    public ResponseUtil<Carousel> updateCarousel(Carousel carousel) {
        carousels.put(carousel.getId(), carousel);
        return ResponseUtil.success("更新成功", carousel);
    }

    @Override
    public ResponseUtil<Void> deleteCarousel(Long carouselId) {
        carousels.remove(carouselId);
        return ResponseUtil.success(null);
    }

}
