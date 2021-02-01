package com.bxwl.admin.sys.service;

import com.bxwl.admin.sys.aop.CustomException;
import com.bxwl.admin.sys.dao.SysDictDataDao;
import com.bxwl.admin.sys.dao.SysDictTypeDao;
import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.SysDictType;
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

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackForClassName = {"RuntimeException", "Exception", "CustomException"})
public class SysDictTypeService {

    private static Logger logger = LoggerFactory.getLogger(SysDictTypeService.class);
    @Autowired
    private SysDictTypeDao sysDictTypeDao;
    @Autowired
    private SysDictDataDao sysDictDataDao;

    public PageBean<SysDictType> findPage(HashMap<String, String> map, PageBean<SysDictType> pageBean) {
        try {
            pageBean.setWhere(new HashMap<String, Object>());
            pageBean.setCount(sysDictTypeDao.getCount(pageBean));
            pageBean.getWhere().put("dictName", map.get("dictName"));
            pageBean.getWhere().put("dictType", map.get("dictType"));
            if (pageBean.getCount() > 0) {
                pageBean.setData(sysDictTypeDao.findPage(pageBean));
            }
            pageBean.setCode(0);//正常返回
        } catch (Exception ex) {
            logger.error("分页获取机构出错：", ex);
            pageBean.setCode(200);//错误返回
            pageBean.setMsg("系统繁忙，请稍后再试!");
        }
        return pageBean;
    }

    public SysDictType checkDictType(String dictType) {
        return sysDictTypeDao.getByDictType(dictType);
    }

    public SysDictType getById(String id) {
        return sysDictTypeDao.getById(id);
    }

    public void save(SysDictType sysDictType) throws CustomException {
        SysDictType dictObj = this.checkDictType(sysDictType.getDictType());
        if (StringUtils.isBlank(sysDictType.getId())) {
            if (dictObj != null) {
                throw new CustomException("字典类型重复，请重新输入!");
            }
            sysDictType.setId(UuidUtil.uuid());
            sysDictType.setStatus("0");
            sysDictType.setCreateDate(new Date());
            sysDictType.setUpdateDate(new Date());
            sysDictTypeDao.save(sysDictType);
        } else {
            if (dictObj != null) {
                if (!sysDictType.getId().equals(dictObj.getId())) {
                    throw new CustomException("字典类型重复，请重新输入!");
                }
            }
            sysDictType.setUpdateDate(new Date());
            sysDictTypeDao.update(sysDictType);
        }
    }

    public void delete(String id) throws CustomException {
        SysDictType sysDictType = this.getById(id);
        if (sysDictType == null){
            throw new CustomException("字典类型不存在!");
        }
        sysDictTypeDao.delete(id);
        sysDictDataDao.deleteByDictType(sysDictType.getDictType());
    }
}
