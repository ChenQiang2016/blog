package com.blog.web.controller.web.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.model.Admin;
import com.blog.model.AdminExample;
import com.blog.service.admin.AdminService;
import com.blog.web.mvc.util.JsonResult;
import com.blog.web.mvc.util.MVCUtil;

@Controller
@RequestMapping("admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@RequestMapping("login")
	@ResponseBody
	public JsonResult login(String adminName, String password) {
		AdminExample example = new AdminExample();
		example.createCriteria().andAdminNameEqualTo(adminName).andPasswordEqualTo(password);
		return MVCUtil.getJsonResult(adminService.login(example));
	}

	@RequestMapping("list")
	@ResponseBody
	public JsonResult list() {
		AdminExample example = new AdminExample();
		return MVCUtil.getJsonResult(adminService.list(example));
	}
	
	@RequestMapping("page")
	@ResponseBody
	public JsonResult page(Integer currentPage, Integer pageSize) {
		Map<String, Object> map = new HashMap<>();
		map.put("pageSize", pageSize);
		map.put("currentPage", currentPage);
		return MVCUtil.getJsonResult(adminService.page(map));
	}

	@RequestMapping("test")
	@ResponseBody
	public JsonResult test() {
		for (int i = 0; i < 10; i++) {
			Admin admin = new Admin();
			admin.setAdminName(i+"");
			admin.setPassword("123456");
			try {
				adminService.save(admin);
			} catch (DuplicateKeyException e) {
				e.printStackTrace();
			}
			
		}
		return MVCUtil.getJsonResult("啊啊啊");
	}
}