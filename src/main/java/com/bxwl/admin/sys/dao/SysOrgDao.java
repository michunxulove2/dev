package com.bxwl.admin.sys.dao;

import java.util.List;

import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.SysOrg;
import org.springframework.stereotype.Repository;

@Repository
public interface SysOrgDao {
	
	/**
	 * 分页查询机构
	 * @param pageBean
	 * @return
	 */
	List<SysOrg> getOrgsByPage(PageBean<SysOrg> pageBean);
	
	/**
	 * 查询机构总数
	 * @param pageBean
	 * @return
	 */
	int getOrgsCount(PageBean<SysOrg> pageBean);
	
	/**
	 * 新增系统机构
	 * @param sysOrg
	 */
	void addSysOrg(SysOrg sysOrg);
	
	/**
	 * 根据机构id删除系统机构
	 * @param orgId
	 */
	void delSysOrg(String orgId);
	
	/**
	 * 修改系统机构信息
	 * @param sysOrg
	 */
	void updateSysOrg(SysOrg sysOrg);
	
	/**
	 * 根据系统机构id查询机构信息
	 * @param orgId
	 * @return
	 */
	SysOrg getSysOrgById(String orgId);

}
