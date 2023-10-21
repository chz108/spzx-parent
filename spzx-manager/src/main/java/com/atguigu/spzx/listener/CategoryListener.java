package com.atguigu.spzx.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.BeanMapUtils;
import com.alibaba.excel.util.ListUtils;
import com.atguigu.spzx.mapper.CategoryMapper;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/20
 */
public class CategoryListener<T> implements ReadListener<T> {
    private CategoryMapper categoryMapper;
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 10;
    /**
     * 缓存的数据
     */
    private List<CategoryExcelVo> categoryExcelVoList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public CategoryListener(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void invoke(T o, AnalysisContext analysisContext) {
        CategoryExcelVo categoryExcelVo = (CategoryExcelVo) o;
        categoryExcelVoList.add(categoryExcelVo);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (categoryExcelVoList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            categoryExcelVoList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    private void saveData() {
        // 批量添加
        categoryMapper.save(categoryExcelVoList);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 如果list中没有达到设置只还需要把剩余的添加到数据库
        saveData();
    }
}
