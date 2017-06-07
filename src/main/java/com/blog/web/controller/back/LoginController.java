package com.blog.web.controller.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.blog.model.AdminExample;
import com.blog.service.admin.AdminService;
import com.blog.web.mvc.util.MVCUtil;

@Controller
@RequestMapping("back")
public class LoginController {

	@Autowired
	private AdminService adminService;
	
	@RequestMapping("angel/toLogin")
	public ModelAndView toLogin(){
		return new ModelAndView("back/login");
	}
	
	@RequestMapping("angel/login")
	public Object login(String adminName, String password ){
		AdminExample example = new AdminExample();
		example.createCriteria().andAdminNameEqualTo(adminName).andPasswordEqualTo(password);
		return MVCUtil.getJsonResult(adminService.login(example));
	}
}