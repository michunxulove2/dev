package com.bxwl.admin.sys.dao;
import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.SysDictType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface SysDictTypeDao {

	int getCount(PageBean<SysDictType> pageBean);

	List<SysDictType> findPage(PageBean<SysDictType> pageBean);

    SysDictType getByDictType(String dictType);

	SysDictType getById(String id);

	void save(@Param("sysDictType") SysDictType sysDictType);

	void update(@Param("sysDictType") SysDictType sysDictType);

    void delete(String id);
}
