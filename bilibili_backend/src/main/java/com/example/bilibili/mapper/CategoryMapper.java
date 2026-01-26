package com.example.bilibili.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bilibili.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 分类表Mapper
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 获取所有主分类
     */
    @Select("SELECT * FROM category WHERE type = 0 ORDER BY `order` ASC")
    List<Category> getMainCategories();

    /**
     * 根据父分类id获取子分类
     */
    @Select("SELECT * FROM category WHERE parent_id = #{parentId} ORDER BY `order` ASC")
    List<Category> getSubcategoriesByParentId(Long parentId);

}
