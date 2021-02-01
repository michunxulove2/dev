package com.bxwl.admin.sys.model;

/**
 * 系统机构
 * @author liuyu
 *
 */
public class SysOrg {

	private String orgId;//机构id
	private String pId;//父机构id
	private String orgName;//机构名称
	private String loginName;//登陆账号
	private String loginPwd;//登录密码
	private String jobId;//默认岗位id
	private String contactName;//联系人
	private String contactPhone;//联系方式
	private double lng;//经度
	private double lat;//纬度
	private String address;//地址
	private String createTime;//创建时间
	private String hasEnabled;//是否启用 N 禁用    Y 启用
	private String hasDeleted;//是否删除  N 未删除   Y 已删除
	private String[] authids;//权限id集合

	public String getHasEnabled() {
		return hasEnabled;
	}

	public void setHasEnabled(String hasEnabled) {
		this.hasEnabled = hasEnabled;
	}

	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getHasDeleted() {
		return hasDeleted;
	}
	public void setHasDeleted(String hasDeleted) {
		this.hasDeleted = hasDeleted;
	}
	public String[] getAuthids() {
		return authids;
	}
	public void setAuthids(String[] authids) {
		this.authids = authids;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

}
