package com.bxwl.admin.sys.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 分页工具
 * @author liuyu
 * @param <T>
 */
public class PageBean<T> {
	
	private int code;//状态码  0 正常
	private int page;//当前页
	private int pageSize;//数据量/页
	private int count;//总数
	private String item;  //特定参数
	private String msg;//返回消息
	private String params;//接入传入查询条件
	private Map<String, Object> where;//sql处理查询条件
	private List<T> data;//数据列表
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public Map<String, Object> getWhere() {
		return where;
	}
	public void setWhere(Map<String, Object> where) {
		this.where = where;
	}
	public int getStartSize() {
		return (page - 1) * pageSize;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
}
