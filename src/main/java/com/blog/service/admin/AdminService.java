package com.blog.service.admin;

import java.util.List;
import java.util.Map;

import com.blog.framework.mybatis.Page;
import com.blog.model.Admin;
import com.blog.model.AdminExample;

public interface AdminService {

	Admin login(AdminExample example);

	List<Admin> list(AdminExample example);

	Page page(Map<String, Object> map);

	int update(Admin admin);

	int save(Admin admin);
}