package com.bxwl.admin.sys.service;
import com.bxwl.admin.sys.dao.SysDepartmentDao;
import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.SysDepartment;
import com.bxwl.admin.sys.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.List;

@Service
public class SysDepartmentService {

	private static Logger logger = LoggerFactory.getLogger(SysDepartmentService.class);
	@Autowired
	private SysDepartmentDao sysDepartmentDao;
	public PageBean<SysDepartment> getDepartmentsByPage(String name,PageBean<SysDepartment> pageBean) {
		try {
			pageBean.setWhere(new HashMap<String,Object>());
			pageBean.setCount(sysDepartmentDao.getDepartmentsCount(pageBean));
			pageBean.getWhere().put("name",name);
			if(pageBean.getCount() > 0) {
				pageBean.setData(sysDepartmentDao.getDepartmentsByPage(pageBean));
			}
			pageBean.setCode(0);//正常返回
		}catch(Exception ex) {
			logger.error("分页获取机构出错：",ex);
			pageBean.setCode(200);//错误返回
			pageBean.setMsg("系统繁忙，请稍后再试!");
		}
		return pageBean;
	}

	public void addSysDepartment(SysDepartment sysDepartment) {
		sysDepartment.setId(UuidUtil.uuid());
		sysDepartmentDao.addDepartment(sysDepartment);
	}

	public void delSysDepartment(String departmentId) {
		sysDepartmentDao.delSysDepartmentById(departmentId);
	}
	/**
	 * 根据userId查询系统用户信息
	 * @param id
	 * @return
	 */

	public SysDepartment getSysDepartmentById(String id) {
		SysDepartment sysDepartment = sysDepartmentDao.getSysDepartmentById(id);
		return sysDepartment;
	}

	public void updateSysDepartment(SysDepartment sysDepartment) {
		sysDepartmentDao.updateSysDepartment(sysDepartment);
	}

	public List<SysDepartment> getDepartments() {
		return sysDepartmentDao.getDepartments();
	}
}
