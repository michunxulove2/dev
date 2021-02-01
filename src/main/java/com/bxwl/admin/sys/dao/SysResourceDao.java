package com.bxwl.admin.sys.dao;

import java.util.List;

import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.SysResource;
import org.springframework.stereotype.Component;

@Component
public interface SysResourceDao {
	
	/**
	 * 获取系统资源总数
	 * @param pageBean
	 * @return
	 */
	int getResourcesCount(PageBean<SysResource> pageBean);
	
	/**
	 * 分页获取系统资源
	 * @param pageBean
	 * @return
	 */
	List<SysResource> getResourcesByPage(PageBean<SysResource> pageBean);
	
	/**
	 * 根据资源id获取资源
	 * @param resId
	 * @return
	 */
	SysResource getResourcesById(String resId);
	
	/**
	 * 根据资源类型获取所有资源
	 * @param resType
	 * @return
	 */
	List<SysResource> getAllResourcesByType(int resType);
	
	/**
	 * 更新资源
	 * @param sysResource
	 */
	void updateSysResource(SysResource sysResource);
	
	/**
	 * 根据资源id删除资源
	 * @param resId
	 */
	void delSysResourceByResId(String resId);
	
	/**
	 * 新增资源
	 * @param sysResource
	 */
	void addSysResource(SysResource sysResource);
	
	/**
	 * 查询所有资源树
	 * @return
	 */
	List<SysResource> getAllResourcesTree();
	
	/**
	 * 查询我的资源树
	 * @param userId
	 * @return
	 */
	List<SysResource> getMyResourcesTree(String userId);

	/**
	 * 获取指定资源下的按钮资源信息
	 * @param parentId
	 * @return
	 */
	List<SysResource> getBtnResources(String parentId);

}
