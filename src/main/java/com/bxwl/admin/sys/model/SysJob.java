package com.bxwl.admin.sys.model;

import java.util.Date;
import java.util.List;

/**
 * 系统岗位
 * @author liuyu
 *
 */
public class SysJob {
	
	private String jobId;//岗位id
	private String orgId;//机构id
	private String jobName;//岗位名称
	private Date createTime;//创建时间
	List<Node> nodeList;//资源节点
	private String[] authids;//权限id集合
	private String hasDefault;//N 非默认  Y 默认
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String[] getAuthids() {
		return authids;
	}
	public void setAuthids(String[] authids) {
		this.authids = authids;
	}
	public List<Node> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<Node> nodeList) {
		this.nodeList = nodeList;
	}
	public String getHasDefault() {
		return hasDefault;
	}
	public void setHasDefault(String hasDefault) {
		this.hasDefault = hasDefault;
	}

}
