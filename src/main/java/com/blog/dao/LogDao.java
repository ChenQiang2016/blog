package com.blog.dao;

import com.blog.model.Log;
import com.blog.model.LogExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface LogDao {
    long countByExample(LogExample example);

    int deleteByExample(LogExample example);

    int deleteByPrimaryKey(Integer logId);

    int insert(Log record);

    int insertSelective(Log record);

    List<Log> selectByExampleWithBLOBs(LogExample example);

    List<Log> selectByExample(LogExample example);

    long countPageByMap(Map<String, Object> map);

    List selectPageByMap(Map<String, Object> map);

    Log selectByPrimaryKey(Integer logId);

    int updateByExampleSelective(@Param("record") Log record, @Param("example") LogExample example);

    int updateByExampleWithBLOBs(@Param("record") Log record, @Param("example") LogExample example);

    int updateByExample(@Param("record") Log record, @Param("example") LogExample example);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKeyWithBLOBs(Log record);

    int updateByPrimaryKey(Log record);
}