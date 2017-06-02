package com.blog.dao;

import com.blog.model.Label;
import com.blog.model.LabelExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface LabelDao {
    long countByExample(LabelExample example);

    int deleteByExample(LabelExample example);

    int deleteByPrimaryKey(Integer labelId);

    int insert(Label record);

    int insertSelective(Label record);

    List<Label> selectByExample(LabelExample example);

    long countPageByMap(Map<String, Object> map);

    List selectPageByMap(Map<String, Object> map);

    Label selectByPrimaryKey(Integer labelId);

    int updateByExampleSelective(@Param("record") Label record, @Param("example") LabelExample example);

    int updateByExample(@Param("record") Label record, @Param("example") LabelExample example);

    int updateByPrimaryKeySelective(Label record);

    int updateByPrimaryKey(Label record);
}