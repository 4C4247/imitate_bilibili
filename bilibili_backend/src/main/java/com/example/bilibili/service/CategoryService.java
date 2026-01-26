package com.example.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bilibili.entity.Category;

import java.util.List;

/**
 * 分类服务接口
 */
public interface CategoryService extends IService<Category> {

    /**
     * 获取所有分类（包括子分类）
     * @return 分类列表
     */
    List<Category> getAllCategoriesWithSubcategories();

}
