package com.bxwl.admin.sys.model;

import lombok.Data;

import java.util.Date;


/**
 * 字典管理
 * @author xueyuliang
 *
 */
@Data
public class SysDictType {
	private String id;            //字典id
	private String dictName;          //字典名称
	private String dictType;          //字典类型
	private String isSys;          //是否系统
	private String status;          //状态
	private Date createDate;    //创建时间
	private Date updateDate;    //更新时间
	private String remarks;   //备注
}
