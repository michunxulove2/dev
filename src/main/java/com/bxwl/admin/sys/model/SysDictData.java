package com.bxwl.admin.sys.model;

import lombok.Data;

import java.util.Date;


/**
 * 字典数据管理
 * @author xueyuliang
 *
 */
@Data
public class SysDictData {
	private String id;            //字典id
	private String dictLabel;          //字典标签
	private String dictValue;          //字典键值
	private String dictType;          //字典类型
	private String description;       //描述
	private String status;          //状态
	private Date createDate;    //创建时间
	private Date updateDate;    //更新时间
	private String remarks;   //备注
}
