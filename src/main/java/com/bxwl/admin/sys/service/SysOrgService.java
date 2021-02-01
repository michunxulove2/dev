package com.bxwl.admin.sys.service;

import com.bxwl.admin.sys.constants.SysConstants;
import com.bxwl.admin.sys.dao.SysJobDao;
import com.bxwl.admin.sys.dao.SysOrgDao;
import com.bxwl.admin.sys.dao.SysUserDao;
import com.bxwl.admin.sys.model.*;
import com.bxwl.admin.sys.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SysOrgService {
	
	private static Logger logger = LoggerFactory.getLogger(SysOrgService.class);
	
	@Autowired
	private SysOrgDao sysOrgDao;
	@Autowired
	private SysJobDao sysJobDao;
	@Autowired
	private SysUserDao sysUserDao;
	
	/**
	 * 分页查询机构
	 * @param sysUser
	 * @param pageBean
	 * @return
	 */
	public PageBean<SysOrg> getOrgsByPage(SysUser sysUser,PageBean<SysOrg> pageBean){
		try {
			pageBean.setWhere(new HashMap<String,Object>());
			pageBean.getWhere().put("orgId", sysUser.getOrgId());
			pageBean.setCount(sysOrgDao.getOrgsCount(pageBean));
			if(pageBean.getCount() > 0) {
				pageBean.setData(sysOrgDao.getOrgsByPage(pageBean));
			}
			pageBean.setCode(0);//正常返回
		}catch(Exception ex) {
			logger.error("分页获取机构出错：",ex);
			pageBean.setCode(200);//错误返回
			pageBean.setMsg("系统繁忙，请稍后再试!");
		}
		return pageBean;
	}
	
	/**
	 * 新增系统机构
	 * @param sysUser
	 * @param sysOrg
	 */
	@Transactional
	public void addSysOrg(SysUser sysUser,SysOrg sysOrg) {
		
		/** 添加系统默认机构 */
		sysOrg.setOrgId(UuidUtil.uuid());
		sysOrg.setpId(sysUser.getOrgId());
		sysOrgDao.addSysOrg(sysOrg);
		
		/** 添加系统默认岗位 */
		SysJob sysJob = new SysJob();
		sysJob.setJobId(UuidUtil.uuid());
		sysJob.setOrgId(sysOrg.getOrgId());
		sysJob.setJobName(SysConstants.DEFAULT_ADMIN_JOB_NAME);
		sysJob.setHasDefault(SysConstants.YES);
		List<Job2Resource> job2ResList = new ArrayList<Job2Resource>();
		for(String authid : sysOrg.getAuthids()) {
			if(!StringUtils.isEmpty(authid)) {
				job2ResList.add(new Job2Resource(sysJob.getJobId(), authid));
			}
		}
		sysJobDao.addJob(sysJob);
		sysJobDao.addJob2Resources(job2ResList);
		
		/** 添加系统默认管理员 */
		SysUser addSysUser = new SysUser();
		addSysUser.setUserId(UuidUtil.uuid());
		addSysUser.setOrgId(sysOrg.getOrgId());
		addSysUser.setLoginName(sysOrg.getLoginName());
		addSysUser.setLoginPwd(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(SysConstants.RAWPWD));
		addSysUser.setHasDefault(SysConstants.YES);
		List<SysUser2Job> sysUser2JobList = new ArrayList<SysUser2Job>();
		SysUser2Job sysUser2Job = new SysUser2Job();
		sysUser2Job.setSysUserId(addSysUser.getUserId());
		sysUser2Job.setJobId(sysJob.getJobId());
		sysUser2JobList.add(sysUser2Job);
		sysUserDao.addSysUser(addSysUser);
		sysUserDao.addSysUser2Jobs(sysUser2JobList);
		
	}

	/**
	 * 新增系统机构（仅供旅游公司和商户使用）
	 * @param orgId 机构id
	 * @param pid 父机构id
	 * @param hasEnabled 是否启用 N 禁用 Y 启用
	 */
	@Transactional
	public void addSysOrg(String orgId,String pid,String hasEnabled) {
		SysOrg sysOrg = new SysOrg();
		sysOrg.setOrgId(orgId);
		sysOrg.setpId(pid);
		sysOrg.setHasEnabled(hasEnabled);
		sysOrgDao.addSysOrg(sysOrg);
	}

	/**
	 * 更新机构状态
	 * @param orgId 机构id，旅游公司和商户传对应的id
	 * @param hasEnabled 是否启用 N 禁用 Y 启用
	 */
	public void updateSysOrgStatus(String orgId,String hasEnabled) {
		SysOrg sysOrg = new SysOrg();
		sysOrg.setOrgId(orgId);
		sysOrg.setHasEnabled(hasEnabled);
		sysOrgDao.updateSysOrg(sysOrg);
	}

	/**
	 * 根据机构id删除系统机构
	 * @param orgId
	 */
	public void delSysOrg(String orgId) {
		sysOrgDao.delSysOrg(orgId);
	}
	
	/**
	 * 修改系统机构信息
	 * @param sysOrg
	 */
	@Transactional
	public void updateSysOrg(SysOrg sysOrg) {
		sysOrgDao.updateSysOrg(sysOrg);
		sysJobDao.delJob2ResByJobId(sysOrg.getJobId());
		List<Job2Resource> job2ResList = new ArrayList<Job2Resource>();
		for(String authid : sysOrg.getAuthids()) {
			if(!StringUtils.isEmpty(authid)) {
				job2ResList.add(new Job2Resource(sysOrg.getJobId(), authid));
			}
		}
		sysJobDao.addJob2Resources(job2ResList);
	}
	
	/**
	 * 根据系统机构id查询机构信息
	 * @param orgId
	 * @return
	 */
	public SysOrg getSysOrgById(String orgId) {
		return sysOrgDao.getSysOrgById(orgId);
	}

}
