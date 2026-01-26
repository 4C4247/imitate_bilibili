package com.example.bilibili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bilibili.entity.Category;
import com.example.bilibili.mapper.CategoryMapper;
import com.example.bilibili.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类服务实现类
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 获取所有分类（包括子分类）
     * @return 分类列表
     */
    @Override
    public List<Category> getAllCategoriesWithSubcategories() {
        // 查询所有主分类，按排序字段排序
        List<Category> mainCategories = baseMapper.getMainCategories();
        
        // 为每个主分类查询子分类
        for (Category category : mainCategories) {
            List<Category> subcategories = baseMapper.getSubcategoriesByParentId(category.getId());
            category.setSubcategories(subcategories);
        }
        
        return mainCategories;
    }

}
