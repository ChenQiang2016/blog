package com.blog.dao;

import com.blog.model.Category;
import com.blog.model.CategoryExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface CategoryDao {
    long countByExample(CategoryExample example);

    int deleteByExample(CategoryExample example);

    int deleteByPrimaryKey(Integer categoryId);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByExample(CategoryExample example);

    long countPageByMap(Map<String, Object> map);

    List selectPageByMap(Map<String, Object> map);

    Category selectByPrimaryKey(Integer categoryId);

    int updateByExampleSelective(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByExample(@Param("record") Category record, @Param("example") CategoryExample example);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}