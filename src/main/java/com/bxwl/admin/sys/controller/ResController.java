package com.bxwl.admin.sys.controller;

import com.bxwl.admin.sys.aop.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bxwl.admin.sys.constants.SysConstants;
import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.ResultBean;
import com.bxwl.admin.sys.model.SysResource;
import com.bxwl.admin.sys.service.SysResourceService;

/**
 * 菜单资源管理
 * @author liuyu
 *
 */
@Controller
@RequestMapping("admin/sys/res")
public class ResController {
	
	private static Logger logger = LoggerFactory.getLogger(ResController.class);
	
	@Autowired
	private SysResourceService sysResourceService;
	
	@RequestMapping("res_index")
	public void res_index(){
		
	}
	
	@RequestMapping("res_data_list")
	public @ResponseBody PageBean<SysResource> res_data_list(String resName,Integer resType,PageBean<SysResource> pageBean){
		return sysResourceService.getResourcesByPage(resName,resType,pageBean);
	}
	
	@RequestMapping(value="res_add",method=RequestMethod.GET)
	public void res_add_get(Model model){
		model.addAttribute("typeResList", sysResourceService.getAllResourcesByType(SysConstants.MENU));
	}
	@Operation("新增菜单资源")
	@RequestMapping(value="res_add",method=RequestMethod.POST)
	public @ResponseBody ResultBean res_add_post(SysResource sysResource){
		try {
			sysResourceService.addSysResource(sysResource);
			return new ResultBean(0,null,null);
		}catch(Exception ex) {
			logger.error("新增资源出错：",ex);
			return new ResultBean(200,"新增资源出错!",null);
		}
	}
	
	@RequestMapping(value="res_edit",method=RequestMethod.GET)
	public void res_edit_get(Model model,String resId){
		model.addAttribute("sysRes", sysResourceService.getResourcesById(resId));
		model.addAttribute("typeResList", sysResourceService.getAllResourcesByType(SysConstants.MENU));
	}
	@Operation("修改菜单资源")
	@RequestMapping(value="res_edit",method=RequestMethod.POST)
	public @ResponseBody ResultBean res_edit_post(SysResource sysResource){
		try {
			sysResourceService.updateSysResource(sysResource);
			return new ResultBean(0,null,null);
		}catch(Exception ex) {
			logger.error("更新资源出错：",ex);
			return new ResultBean(200,"更新资源出错!",null);
		}
	}
	@Operation("删除菜单资源")
	@RequestMapping("res_del")
	public @ResponseBody ResultBean res_del(String resId){
		try {
			sysResourceService.delSysResourceByResId(resId);
			return new ResultBean(0,null,null);
		}catch(Exception ex) {
			logger.error("删除资源出错：",ex);
			return new ResultBean(200,"删除资源出错!",null);
		}
	} 

}
