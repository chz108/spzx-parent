package com.atguigu.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaozhen
 * @date 2023/10/30
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 2. 使用spring-cache框架基于注解的方式实现缓存功能
     *  Cacheable(value = "category",key = "'one'") 存在redis中的key值就是 category::all
     * @return
     */
    @Cacheable(value = "category",key = "'all'")
    @Override
    public List<Category> findOneCategory() {
        return categoryMapper.findOneCategory();
    }

    /**
     * 因为分类不会经常的改变,所以我们可以通过redis来加快查询效率
     * 1. 使用最初始的方式实现
     * @return
     */
   /* @Override
    public List<Category> findOneCategory() {
        // 1. 从redis中通过key获取value值
        String categoryStr = redisTemplate.opsForValue().get("category:one");
        // 2. 判断是否为空,如果不为空,就将字符串变为list集合返回
        if (StringUtils.hasText(categoryStr)){
            // 字符串变为list集合 使用JSON.parseArray
            return JSON.parseArray(categoryStr, Category.class);
        }
        // 3. 如果判断为空,将数据从数据库查出来,将list集合装成json字符串,然后再将数据存到redis中,返回list集合
        List<Category> oneCategory = categoryMapper.findOneCategory();
        redisTemplate.opsForValue().set("category:one", JSON.toJSONString(oneCategory), 7, TimeUnit.DAYS);
        //查询所有一级分类
        return oneCategory;
    }*/

    /**
     * 查询所有分类并一级一级封装
     *
     * @return
     */
    @Override
    public List<Category> findCategoryTree() {
        // 1. 需要查询出所有的分类
        List<Category> allCategoryList = categoryMapper.findAll();
        // 2. 使用stream流过滤出第一级
        List<Category> firstCategoryList = allCategoryList.stream().filter(category -> category.getParentId() == 0).toList();
        // 3. 遍历第一层分类,找出第二层分类(通过第一层分类id和全部分类的parentId找到第二层分类)
        firstCategoryList.forEach(oneCategory -> {
            // 通过第一层分类id和全部分类的parentId找到第二层分类
            List<Category> secondCategoryList = allCategoryList.stream().filter(category -> category.getParentId().longValue() == oneCategory.getId().longValue()).toList();
            // 将第二层分类分装到第一层分类的children中
            oneCategory.setChildren(secondCategoryList);
            // 4. 遍历第二层分类找出第三层分类,使用的方法和第二层的一样
            secondCategoryList.forEach(twoCategory -> {
                List<Category> thirdCategoryList = allCategoryList.stream().filter(category ->
                        twoCategory.getId().longValue() == category.getParentId().longValue()
                ).toList();
                // 将第三层分类封装到第二层分类的children中
                twoCategory.setChildren(thirdCategoryList);
            });
        });
        // 返回第一级
        return firstCategoryList;
    }

}
