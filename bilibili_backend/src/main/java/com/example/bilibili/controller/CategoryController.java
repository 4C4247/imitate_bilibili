package com.example.bilibili.controller;

import com.example.bilibili.entity.Category;
import com.example.bilibili.service.CategoryService;
import com.example.bilibili.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类控制器
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有分类（包括子分类）
     * @return 分类列表
     */
    @GetMapping("/all")
    public ResponseUtil<List<Category>> getAllCategoriesWithSubcategories() {
        List<Category> categories = categoryService.getAllCategoriesWithSubcategories();
        return ResponseUtil.success("获取分类列表成功", categories);
    }

}
