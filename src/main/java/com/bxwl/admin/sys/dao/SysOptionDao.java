package com.bxwl.admin.sys.dao;

import com.bxwl.admin.sys.model.SysOption;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
@Component
public interface SysOptionDao {
	void save(@Param("sysOption") SysOption sysOption);

	SysOption getConfig(String groupName);

	void updateSysOption(@Param("id") String id, @Param("jsonString") String jsonString);
}
