package com.bxwl.admin.sys.dao;

import java.util.List;
import java.util.Map;

import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.SysUser;
import com.bxwl.admin.sys.model.SysUser2Job;
import org.springframework.stereotype.Component;

@Component
public interface SysUserDao {
	
	/**
	 * 系统用户登录
	 * @param loginName
	 * @return SysUser
	 */
	SysUser doLogin(String loginName);
	
	/**
	 * 根据机构id获取系统用户总数
	 * @param pageBean
	 * @return
	 */
	int getSysUsersCount(PageBean<SysUser> pageBean);
	
	/**
	 * 分页查询系统用户
	 * @param pageBean
	 * @return
	 */
	List<SysUser> getSysUsersByPage(PageBean<SysUser> pageBean);
	
	/**
	 * 根据userId查询系统用户信息
	 * @param userId
	 * @return
	 */
	SysUser getSysUserById(String userId);
	
	/**
	 * 批量添加系统用户对应岗位
	 * @param sysUser2JobList
	 */
	void addSysUser2Jobs(List<SysUser2Job> sysUser2JobList);
	
	/**
	 * 新增系统用户
	 * @param sysUser
	 */
	void addSysUser(SysUser sysUser);
	
	/**
	 * 修改系统用户
	 * @param sysUser
	 */
	void updateSysUser(SysUser sysUser);
	
	/**
	 * 检查登录名是否存在
	 * @param loginName
	 * @return
	 */
	int checkLoginName(String loginName);
	
	/**
	 * 根据id删除系统用户
	 * @param userId
	 */
	void delSysUserById(String userId);
	
	/**
	 * 修改系统用户启用状态
	 * @param params
	 */
	void updateSysUserEnabled(Map<String, Object> params);
	
	/**
	 * 根据系统用户id查询对应的岗位id集合
	 * @param userId
	 * @return
	 */
	List<String> getJobIdsByUserId(String userId);

	/**
	 * 根据岗位id查询对应的用户集合
	 * @param jobId
	 * @return
	 */
	List<String> getUserIdsByJobId(String jobId);
	
	/**
	 * 根据系统用户id删除系统用户对应岗位信息
	 * @param userId
	 */
	void delSysUser2JobsByUserId(String userId);
	
	/**
	 * 修改系统用户密码
	 * @param sysUser
	 */
	void updatePwd(SysUser sysUser);

	/**
	 * 修改系统用户密码
	 * @param sysUser
	 */
	void updateUserPwd(SysUser sysUser);

    List<SysUser> getSysUsers();
}
