package com.bxwl.admin.sys.service;

import com.bxwl.admin.sys.aop.CustomException;
import com.bxwl.admin.sys.dao.SysDictDataDao;
import com.bxwl.admin.sys.dao.SysDictTypeDao;
import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.SysDictData;
import com.bxwl.admin.sys.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackForClassName = {"RuntimeException", "Exception", "CustomException"})
public class SysDictDataService {

    private static Logger logger = LoggerFactory.getLogger(SysDictDataService.class);
    @Autowired
    private SysDictTypeDao sysDictTypeDao;
    @Autowired
    private SysDictDataDao sysDictDataDao;

    public PageBean<SysDictData> findPage(HashMap<String, String> map, PageBean<SysDictData> pageBean) {
        try {
            pageBean.setWhere(new HashMap<String, Object>());
            pageBean.setCount(sysDictDataDao.getCount(pageBean));
            pageBean.getWhere().put("dictLabel", map.get("dictLabel"));
            pageBean.getWhere().put("dictValue", map.get("dictValue"));
            pageBean.getWhere().put("dictType", map.get("dictType"));
            if (pageBean.getCount() > 0) {
                pageBean.setData(sysDictDataDao.findPage(pageBean));
            }
            pageBean.setCode(0);//正常返回
        } catch (Exception ex) {
            logger.error("分页获取机构出错：", ex);
            pageBean.setCode(200);//错误返回
            pageBean.setMsg("系统繁忙，请稍后再试!");
        }
        return pageBean;
    }

    public SysDictData checkDictType(String dictValue, String dicType) {
        return sysDictDataDao.getByDictValue(dictValue, dicType);
    }

    public SysDictData getById(String id) {
        return sysDictDataDao.getById(id);
    }

    public void save(SysDictData sysDictData) throws CustomException {
        SysDictData dictObj = this.checkDictType(sysDictData.getDictValue(), sysDictData.getDictType());
        if (StringUtils.isBlank(sysDictData.getId())) {
            if (dictObj != null) {
                throw new CustomException("字典数据键值重复，请重新输入!");
            }
            sysDictData.setId(UuidUtil.uuid());
            sysDictData.setStatus("0");
            sysDictData.setCreateDate(new Date());
            sysDictData.setUpdateDate(new Date());
            sysDictDataDao.save(sysDictData);
        } else {
            if (dictObj != null) {
                if (!sysDictData.getId().equals(dictObj.getId())) {
                    throw new CustomException("字典数据键值重复，请重新输入!");
                }
            }
            sysDictData.setUpdateDate(new Date());
            sysDictDataDao.update(sysDictData);
        }
    }

    public void delete(String id) {
        sysDictDataDao.delete(id);
    }

    public List<SysDictData> findListByType(String dictType) {
        return sysDictDataDao.findListByType(dictType);
    }
}
