package com.blog.dao;

import com.blog.model.Admin;
import com.blog.model.AdminExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface AdminDao {
    long countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer adminId);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    long countPageByMap(Map<String, Object> map);

    List selectPageByMap(Map<String, Object> map);

    Admin selectByPrimaryKey(Integer adminId);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}