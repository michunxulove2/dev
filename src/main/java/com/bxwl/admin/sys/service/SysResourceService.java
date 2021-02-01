package com.bxwl.admin.sys.service;

import com.bxwl.admin.sys.dao.SysResourceDao;
import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.SysResource;
import com.bxwl.admin.sys.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SysResourceService {
	
	private static Logger logger = LoggerFactory.getLogger(SysResourceService.class);
	
	@Autowired
	private SysResourceDao sysResourceDao;
	
	/**
	 * 分页获取系统资源
	 * @param pageBean
	 * @return pageBean
	 */
	public PageBean<SysResource> getResourcesByPage(String resName,Integer resType,PageBean<SysResource> pageBean){
		pageBean.setWhere(new HashMap<String, Object>());
		pageBean.getWhere().put("resType",resType);
		pageBean.getWhere().put("resName",resName);
		try {
			pageBean.setCount(sysResourceDao.getResourcesCount(pageBean));
			if(pageBean.getCount() > 0) {
				pageBean.setData(sysResourceDao.getResourcesByPage(pageBean));
			}
			pageBean.setCode(0);//正常返回
		}catch(Exception ex) {
			logger.error("分页获取系统资源出错：",ex);
			pageBean.setCode(200);//错误返回
			pageBean.setMsg("系统繁忙，请稍后再试!");
		}
		return pageBean;
	}
	
	/**
	 * 根据资源id获取资源
	 * @param resId
	 * @return
	 */
	public SysResource getResourcesById(String resId) {
		try {
			return sysResourceDao.getResourcesById(resId);
		}catch(Exception ex) {
			logger.error("根据资源id获取资源出错：",ex);
		}
		return null;
	}
	
	/**
	 * 根据资源类型获取所有资源
	 * @param resType
	 * @return
	 */
	public List<SysResource> getAllResourcesByType(int resType){
		try {
			return sysResourceDao.getAllResourcesByType(resType);
		}catch(Exception ex) {
			logger.error("根据资源类型获取所有资源出错：",ex);
		}
		return null;
	}
	
	/**
	 * 更新资源
	 * @param sysResource
	 */
	public void updateSysResource(SysResource sysResource) {
		
		String[] pNode = sysResource.getpName().split(",");
		if(pNode[0].equals("0")) {
			sysResource.setpName(null);
		}else {
			sysResource.setpId(pNode[0]);
			sysResource.setpName(pNode[1]);
		}
		sysResourceDao.updateSysResource(sysResource);
	}
	
	/**
	 * 根据资源id删除资源
	 * @param resId
	 */
	public void delSysResourceByResId(String resId) {
		sysResourceDao.delSysResourceByResId(resId);
	}
	
	/**
	 * 新增资源
	 * @param sysResource
	 */
	public void addSysResource(SysResource sysResource) {
		sysResource.setResId(UuidUtil.uuid());
		String[] pNode = sysResource.getpName().split(",");
		if(pNode[0].equals("0")) {
			sysResource.setpName(null);
		}else {
			sysResource.setpId(pNode[0]);
			sysResource.setpName(pNode[1]);
		}
		if(sysResource.getResType() == 0) {//菜单没有权限
			sysResource.setPerms(null);
		}
		sysResourceDao.addSysResource(sysResource);
	}

}
