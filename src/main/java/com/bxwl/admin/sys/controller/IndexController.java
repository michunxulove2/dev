package com.bxwl.admin.sys.controller;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.druid.support.json.JSONUtils;
import com.bxwl.admin.sys.aop.Operation;
import com.bxwl.admin.sys.model.SysOption;
import com.bxwl.admin.sys.model.SysUser;
import com.bxwl.admin.sys.service.SysOptionService;
import com.bxwl.admin.sys.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bxwl.admin.sys.model.ResultBean;
import com.bxwl.admin.sys.model.UpdatePwd;
import com.bxwl.admin.sys.service.IndexService;
import com.bxwl.admin.sys.utils.SessionUtils;

import java.util.Map;

@Controller
@RequestMapping("admin")
public class IndexController {

	private static Logger logger = LoggerFactory.getLogger(SysUserController.class);
	
	@Autowired
	private IndexService indexService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysOptionService sysOptionService;

	@RequestMapping("index")
	public void index(Model model){
		SysOption config = sysOptionService.getConfig("sys_group");
		Map mapTypes = (Map) JSONUtils.parse(config.getOptionValue());
		model.addAttribute("name",mapTypes.get("name"));
	}
	
	@RequestMapping(value="updatePwd",method=RequestMethod.GET)
	public void updatePwd_get() {
		
	}
	@Operation("修改密码")
	@RequestMapping(value="updatePwd",method=RequestMethod.POST)
	public @ResponseBody ResultBean updatePwd_post(HttpServletRequest request,UpdatePwd updatePwd) {
		return indexService.updatePwd(SessionUtils.getUser(request),updatePwd);
	}

	@RequestMapping(value="userInfo",method=RequestMethod.GET)
	public void userInfo_get(HttpServletRequest request,Model model) {
		try {
			SysUser user = SessionUtils.getUser(request);
			model.addAttribute("sysUser", sysUserService.getSysUserById(user.getUserId()));
			model.addAttribute("jobList", sysUserService.getAllJobs());
		}catch(Exception ex) {
			logger.error("进入用户基本资料界面出错：",ex);
		}
	}

	@RequestMapping("console")
	public void console(){
		
	}

}
