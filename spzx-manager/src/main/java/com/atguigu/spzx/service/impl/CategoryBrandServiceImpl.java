package com.atguigu.spzx.service.impl;

import com.atguigu.spzx.mapper.CategoryBrandMapper;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.atguigu.spzx.service.CategoryBrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/21
 */
@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public PageInfo<CategoryBrand> queryPage(Integer page,
                                             Integer limit,
                                             CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(page, limit);
        List<CategoryBrand> list = categoryBrandMapper.queryPage(categoryBrandDto);
        return new PageInfo<>(list);
    }

    /**
     * 添加分类品牌信息
     * @param categoryBrand
     */
    @Override
    public void saveCategoryBrand(CategoryBrand categoryBrand) {
      categoryBrandMapper.save(categoryBrand);
    }

    @Override
    public void updateById(CategoryBrand categoryBrand) {
        categoryBrandMapper.update(categoryBrand);
    }

    @Override
    public void deleteById(Long id) {
        categoryBrandMapper.delete(id);
    }

    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {
        List<Brand> list = categoryBrandMapper.findBrandByCategoryId(categoryId);
        return list;
    }

}
