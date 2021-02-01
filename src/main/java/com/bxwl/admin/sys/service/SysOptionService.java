package com.bxwl.admin.sys.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.bxwl.admin.sys.dao.SysOptionDao;
import com.bxwl.admin.sys.model.SysOption;
import com.bxwl.admin.sys.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
public class SysOptionService {
    @Autowired
    private SysOptionDao sysOptionDao;

    private static Logger logger = LoggerFactory.getLogger(SysOptionService.class);

    /**
     * 新增系统配置
     *
     * @param jsonString
     */
    @Transactional
    public void addSysOption(String jsonString,String groupName) {
        SysOption sysOption = new SysOption();
        sysOption.setId(UuidUtil.uuid());
        sysOption.setGroupName(groupName);
        sysOption.setOptionLabel("常规配置");
        sysOption.setOptionName("sys_normal");
        sysOption.setOptionValue(jsonString);
        sysOption.setCreateDate(new Date());
        sysOptionDao.save(sysOption);
    }

    public SysOption getConfig(String groupName) {
        return sysOptionDao.getConfig(groupName);
    }

    public void updateSysOption(String id, String jsonString) {
		sysOptionDao.updateSysOption(id,jsonString);
    }
}
