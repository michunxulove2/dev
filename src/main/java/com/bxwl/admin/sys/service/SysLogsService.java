package com.bxwl.admin.sys.service;
import com.bxwl.admin.sys.dao.SysLogsDao;
import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.SysLogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
public class SysLogsService {
	
	private static Logger logger = LoggerFactory.getLogger(SysLogsService.class);
	@Autowired
	private SysLogsDao sysLogsDao;
	public PageBean<SysLogs> getLogsByPage(String name,PageBean<SysLogs> pageBean) {
		try {
			pageBean.setWhere(new HashMap<String,Object>());
			pageBean.setCount(sysLogsDao.getLogsCount(pageBean));
			pageBean.getWhere().put("userName",name);
			if(pageBean.getCount() > 0) {
				pageBean.setData(sysLogsDao.getLogsByPage(pageBean));
			}
			pageBean.setCode(0);//正常返回
		}catch(Exception ex) {
			logger.error("分页获取机构出错：",ex);
			pageBean.setCode(200);//错误返回
			pageBean.setMsg("系统繁忙，请稍后再试!");
		}
		return pageBean;
	}
}
