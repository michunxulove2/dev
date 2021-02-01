package com.bxwl.admin.sys.dao;

import java.util.List;
import java.util.Map;

import com.bxwl.admin.sys.model.Job2Resource;
import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.SysJob;
import org.springframework.stereotype.Component;

@Component
public interface SysJobDao {
	
	/**
	 * 新增岗位
	 * @param sysJob
	 */
	void addJob(SysJob sysJob);
	
	/**
	 * 分页查询岗位
	 * @param pageBean
	 * @return
	 */
	List<SysJob> getJobsByPage(PageBean<SysJob> pageBean);
	
	/**
	 * 查询岗位总数
	 * @param pageBean
	 * @return
	 */
	int getJobsCount(PageBean<SysJob> pageBean);
	
	/**
	 * 批量新增岗位对应资源
	 * @param job2ResList
	 */
	void addJob2Resources(List<Job2Resource> job2ResList);
	
	/**
	 * 根据id删除岗位
	 * @param jobId
	 */
	void delJobById(String jobId);
	
	/**
	 * 根据jobId删除岗位对应资源关系
	 * @param jobId
	 */
	void delJob2ResByJobId(String jobId);
	
	/**
	 * 根据jobId获取岗位对应资源关系
	 * @param jobId
	 * @return
	 */
	List<String> getResByJobId(String jobId);
	
	/**
	 * 根据jobId获取岗位信息
	 * @param jobId
	 * @return
	 */
	SysJob getJobById(String jobId);
	
	/**
	 * 更新岗位信息
	 * @param sysJob
	 */
	void updateJobById(SysJob sysJob);
	
	/**
	 * 根据机构id查询岗位信息
	 * @param orgId
	 * @return
	 */
	List<SysJob> getJobsByOrgId(String orgId);

	/**
	 * 获取所有的岗位信息
	 * @return
	 */
	List<SysJob> getAllJobs();
	
	/**
	 * 获取所有岗位对应资源信息
	 * @return List<Job2Resource>
	 */
	List<Job2Resource> getAllJob2Res();

	/**
	 * 检查岗位名是否存在
	 * @param map
	 * 			jobId 岗位id
	 * 			jobName 岗位名
	 * 			orgId 机构id
	 * @return
	 */
	int checkJobName(Map<String,Object> map);
}
