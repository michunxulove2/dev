package com.bxwl.admin.sys.dao;

import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.SysDictData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysDictDataDao {

	int getCount(PageBean<SysDictData> pageBean);

	List<SysDictData> findPage(PageBean<SysDictData> pageBean);

	SysDictData getByDictValue(@Param("dictValue") String dictValue,@Param("dictType") String dictType);

	SysDictData getById(String id);

	void save(@Param("sysDictData") SysDictData sysDictData);

	void update(@Param("sysDictData") SysDictData sysDictData);

    void delete(String id);

	void deleteByDictType(String dictType);

    List<SysDictData> findListByType(String dictType);
}
