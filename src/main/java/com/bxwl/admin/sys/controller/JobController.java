package com.bxwl.admin.sys.controller;

import javax.servlet.http.HttpServletRequest;

import com.bxwl.admin.sys.aop.Operation;
import com.bxwl.admin.sys.model.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.ResultBean;
import com.bxwl.admin.sys.model.SysJob;
import com.bxwl.admin.sys.service.SysJobService;
import com.bxwl.admin.sys.utils.SessionUtils;

/**
 * 岗位管理
 * @author liuyu
 *
 */
@Controller
@RequestMapping("admin/sys/job")
public class JobController {
	
	private static Logger logger = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	private SysJobService sysJobService;
	
	@RequestMapping(value="job_index",method=RequestMethod.GET)
	public void job_index(){
		
	}
	
	@RequestMapping("job_data_list")
	public @ResponseBody PageBean<SysJob> job_data_list(HttpServletRequest request,PageBean<SysJob> pageBean){
		sysJobService.getJobsByPage(SessionUtils.getUser(request), pageBean);
		return pageBean;
	}
	
	@RequestMapping(value="job_add",method=RequestMethod.GET)
	public void job_add_get() {
		
	}
	@Operation("新增岗位")
	@RequestMapping(value="job_add",method=RequestMethod.POST)
	public @ResponseBody ResultBean job_add_post(HttpServletRequest request,SysJob sysJob) {
		try {
			SysUser sysUser = SessionUtils.getUser(request);
			int result = sysJobService.checkJobName(null,sysJob.getJobName(),sysUser.getOrgId());
			if(result>0){
				return new ResultBean(200, "岗位名重复，请重新输入!",null);
			}
			sysJobService.addJob(SessionUtils.getUser(request), sysJob);
			return new ResultBean(0, null,null);
		}catch(Exception ex) {
			logger.error("新增岗位出错：",ex);
			return new ResultBean(200, "新增岗位出错!",null);
		}
	}
	
	@RequestMapping(value="job_edit",method=RequestMethod.GET)
	public void job_edit_get(Model model,String jobId) {
		model.addAttribute("sysJob", sysJobService.getJobByJobId(jobId));
	}

	@RequestMapping(value="job_view",method=RequestMethod.GET)
	public void job_view_get(Model model,String jobId) {
		model.addAttribute("sysJob", sysJobService.getJobByJobId(jobId));
	}
	@Operation("修改岗位")
	@RequestMapping(value="job_edit",method=RequestMethod.POST)
	public @ResponseBody ResultBean job_edit_post(SysJob sysJob,HttpServletRequest request) {
		try {
			SysUser sysUser = SessionUtils.getUser(request);
			int result = sysJobService.checkJobName(sysJob.getJobId(),sysJob.getJobName(),sysUser.getOrgId());
			if(result>0){
				return new ResultBean(200, "岗位名重复，请重新输入!",null);
			}
			sysJobService.updateJob(sysJob);
			return new ResultBean(0, null,null);
		}catch(Exception ex) {
			logger.error("编辑岗位出错：",ex);
			return new ResultBean(200, "编辑岗位出错!",null);
		}
	}

	@RequestMapping("job_2_res")
	public @ResponseBody ResultBean job2res(HttpServletRequest request,String jobId,boolean disabled) {
		return sysJobService.getJob2ResByJobId(SessionUtils.getUser(request),jobId,disabled);
	}
	@Operation("删除岗位")
	@RequestMapping("job_del")
	public @ResponseBody ResultBean job_del(String jobId) {
		try {
			String flag = sysJobService.delJob(jobId);
			if("exist".equals(flag)){
				return new ResultBean(200, "该岗位下存在员工不能删除!",null);
			}
			return new ResultBean(0, null,null);
		}catch(Exception ex) {
			logger.error("删除岗位出错：",ex);
			return new ResultBean(200, "删除岗位出错!",null);
		}
	}

}
