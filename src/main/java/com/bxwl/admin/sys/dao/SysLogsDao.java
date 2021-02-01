package com.bxwl.admin.sys.dao;

import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.SysLogs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysLogsDao {

	/**
	 * 插入操作记录表
	 * @param sysLogs
	 */
	void save(@Param("sysLogs") SysLogs sysLogs);

	/**
	 * 获取记录总条数
	 * @param pageBean
	 * @return
	 */
	int getLogsCount(PageBean<SysLogs> pageBean);

	/**
	 * 分页查询日志记录
	 * @param pageBean
	 * @return
	 */

	List<SysLogs> getLogsByPage(PageBean<SysLogs> pageBean);
}
