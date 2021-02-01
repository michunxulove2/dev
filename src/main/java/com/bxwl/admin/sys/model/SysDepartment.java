package com.bxwl.admin.sys.model;

import lombok.Data;

import java.util.Date;


/**
 * 部门管理
 * @author xyl
 *
 */
@Data
public class SysDepartment {
	private String id;            //部门id
	private String name;          //部门名称
	private Date createTime;    //创建时间
	private String hasEnabled;   //是否启用
}
