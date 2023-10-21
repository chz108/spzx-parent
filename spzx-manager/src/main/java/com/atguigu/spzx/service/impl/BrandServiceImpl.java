package com.atguigu.spzx.service.impl;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.mapper.BrandMapper;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/20
 */
@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageInfo queryPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Brand> list =  brandMapper.findAll();
        return new PageInfo<>(list);
    }

    @Override
    public void save(Brand brand) {
        Brand brandByName = brandMapper.findBrandByName(brand);
        if (brandByName != null){
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        brandMapper.save(brand);
    }

    @Override
    public void updateById(Brand brand) {
        brandMapper.update(brand);
    }

    @Override
    public void deleteById(Integer id) {
        brandMapper.delete(id);
    }
}
