package com.atguigu.spzx.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.listener.CategoryListener;
import com.atguigu.spzx.mapper.CategoryMapper;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import com.atguigu.spzx.service.CategoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author xiaozhen
 * @date 2023/10/20
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> findByParentId(Long parentId) {
        // 第一次传递过来的值是第一层,第一层的parentId为0,
        List<Category> categoryList = categoryMapper.findCategoryById(parentId);
        // 前端如何实现懒加载的方式,已经如何判断这一层下面是否还有下一层的呢?
        // 我们返回前端的数据中有一个属性hasChildren如果这个属性是true就表示有下一层(页面上也就会多出一个>),如果为false就表示没有下一层
       // 判断集合是否不为空,如果不为空就看看他是否有有下一层
        if (!CollectionUtils.isEmpty(categoryList)) {
           // 遍历集合
            categoryList.forEach(category -> {
                Integer count = categoryMapper.countCategory(category.getId());
                if(count>0){
                    category.setHasChildren(true);
                }else {
                    category.setHasChildren(false);
                }
            });
        }
        return categoryList;
    }

    /**
     * 分类导出
     * @param response
     */
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            // 设置响应的结果类型
            //在windows里面文件有后缀名，每个后缀名对应名称是服务器认识的
           /* response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");*/
            // 如果我们使用的是中文,可能出现中文乱码问题,我们可以通过URLEncoder来进行处理
            String fileName = URLEncoder.encode("分类数据", StandardCharsets.UTF_8);
            // 因为我们不能确定用户的有哪些盘服不知道该往哪里下载,我们使用setHeader让用户自己选择要防止的路径
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 查询所有分类
            List<Category> categoryList = categoryMapper.findAll();
            List<CategoryExcelVo> categoryExcelVoList = ListUtils.newArrayList();
            // 将获取到的分类数据转换成CateGoryExcelVo
            categoryList.forEach(category ->{
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                // 将category中相同字段的数据赋值给categoryEcxelVo
                BeanUtils.copyProperties(category, categoryExcelVo);
                // 将赋值好的categoryExcelVo对象添加到List中
                categoryExcelVoList.add(categoryExcelVo);
            });
            // 使用EasyExcel来写数据
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet()
                    .doWrite(categoryExcelVoList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
    }

    /**
     * 导入分类
     * @param file
     */
    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class, new CategoryListener<CategoryExcelVo>(categoryMapper))
                    .sheet()
                    .doRead();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
