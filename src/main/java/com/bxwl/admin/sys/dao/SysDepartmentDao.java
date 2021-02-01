package com.bxwl.admin.sys.dao;
import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.SysDepartment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysDepartmentDao {

	/**
	 * 获取部门总数
	 * @param pageBean
	 * @return
	 */
	int getDepartmentsCount(PageBean<SysDepartment> pageBean);
	/**
	 * 分页获取部门信息
	 * @param pageBean
	 * @return
	 */
	List<SysDepartment> getDepartmentsByPage(PageBean<SysDepartment> pageBean);

	void addDepartment(@Param("sysDepartment") SysDepartment sysDepartment);
	/**
	 * 根据id删除部门信息
	 * @param departmentId
	 */
	void delSysDepartmentById(String departmentId);
	/**
	 * 根据id查询部门信息
	 * @param departmentId
	 */
	SysDepartment getSysDepartmentById(String departmentId);
	/**
	 * 更新部门信息
	 * @param sysDepartment
	 */
	void updateSysDepartment(@Param("sysDepartment") SysDepartment sysDepartment);
	/**
	 * 获取部门list集合
	 * @param
	 */
    List<SysDepartment> getDepartments();
}
