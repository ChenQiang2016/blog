package com.blog.service.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.dao.AdminDao;
import com.blog.framework.config.Config;
import com.blog.framework.exception.BusiException;
import com.blog.framework.mybatis.Page;
import com.blog.model.Admin;
import com.blog.model.AdminExample;

@Service
@Transactional(rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao adminDao;

	@Override
	public Admin login(AdminExample example) {
		List<Admin> list = adminDao.selectByExample(example);
		if (list == null || list.size() < 1) {
			throw new BusiException(Config.PARAMS_ERROR_CODE, "用户名或密码错误");
		}
		Admin admin = list.get(0);
		
		Admin record = new Admin();
		record.setAdminId(admin.getAdminId());
		record.setLastLoginTime(new Date());
		adminDao.updateByPrimaryKeySelective(record);
		
		return admin;
	}

	@Override
	public List<Admin> list(AdminExample example) {
		return adminDao.selectByExample(example);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Admin> page(Map<String, Object> map) {
		Page<Admin> page = new Page<Admin>();
		page.setCurrentPage((Integer)map.get("currentPage"));
		page.setPageSize((Integer)map.get("pageSize"));
		map.put("limitStart", page.getLimitStart());
		page.setRows(adminDao.selectPageByMap(map));
		page.setTotalCount(adminDao.countPageByMap(map));
		return page;
	}

	@Override
	public int update(Admin admin) {
		return adminDao.updateByPrimaryKeySelective(admin);
	}

	@Override
	public int save(Admin admin) {
		return adminDao.insertSelective(admin);
	}
}