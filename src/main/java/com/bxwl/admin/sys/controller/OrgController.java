package com.bxwl.admin.sys.controller;

import com.bxwl.admin.sys.aop.Operation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.ResultBean;
import com.bxwl.admin.sys.model.SysOrg;
import com.bxwl.admin.sys.service.SysOrgService;
import com.bxwl.admin.sys.service.SysUserService;
import com.bxwl.admin.sys.utils.SessionUtils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * 机构管理
 * @author liuyu
 *
 */
@Controller
@RequestMapping("admin/sys/org")
public class OrgController {
	
	private static Logger logger = LoggerFactory.getLogger(OrgController.class);
	
	@Autowired
	private SysOrgService sysOrgService;
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping(value="org_index",method=RequestMethod.GET)
	public void org_index(){
		
	}
	
	@RequestMapping("org_data_list")
	public @ResponseBody PageBean<SysOrg> org_data_list(HttpServletRequest request,PageBean<SysOrg> pageBean){
		sysOrgService.getOrgsByPage(SessionUtils.getUser(request), pageBean);
		return pageBean;
	}
	
	@RequestMapping(value="org_add",method=RequestMethod.GET)
	public void org_add_get() {
		
	}
	@Operation("新增机构")
	@RequestMapping(value="org_add",method=RequestMethod.POST)
	public @ResponseBody ResultBean org_add_post(HttpServletRequest request,SysOrg sysOrg) {
		try {
			int isExists = sysUserService.checkLoginName(sysOrg.getLoginName());
			if(isExists > 0) {
				return new ResultBean(200, "登录账号重复，请重新输入!",null);
			}
			sysOrgService.addSysOrg(SessionUtils.getUser(request), sysOrg);
			return new ResultBean(0, null,null);
		}catch(Exception ex) {
			logger.error("新增系统机构出错：",ex);
			return new ResultBean(200, "新增系统机构出错!",null);
		}
	}
	@Operation("删除机构")
	@RequestMapping("org_del")
	public @ResponseBody ResultBean org_del(String orgId) {
		try {
			sysOrgService.delSysOrg(orgId);
			return new ResultBean(0, null,null);
		}catch(Exception ex) {
			logger.error("删除系统机构出错：",ex);
			return new ResultBean(200, "删除系统机构出错!",null);
		}
	}
	
	@RequestMapping(value="org_edit",method=RequestMethod.GET)
	public void org_edit_get(Model model,String orgId){
		try {
			SysOrg sysOrg = sysOrgService.getSysOrgById(orgId);
			model.addAttribute("sysOrg", sysOrg);
		}catch(Exception ex) {
			logger.error("进入修改系统机构界面出错：",ex);
		}
	}
	@Operation("修改机构")
	@RequestMapping(value="org_edit",method=RequestMethod.POST)
	public @ResponseBody ResultBean org_edit_post(SysOrg sysOrg) {
		try {
			sysOrgService.updateSysOrg(sysOrg);
			return new ResultBean(0, null,null);
		}catch(Exception ex) {
			logger.error("编辑系统机构出错：",ex);
			return new ResultBean(200, "编辑系统机构出错!",null);
		}
	}

}
