/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.bxwl.admin.sys.utils;

import java.util.List;

import com.bxwl.admin.sys.dao.SysDictDataDao;
import com.bxwl.admin.sys.model.SysDictData;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
@Component
public class DictUtils {
	@Autowired
	SysDictDataDao sysDictDataDao;
	static SysDictDataDao sysDictData;
	@PostConstruct
	public void init() {
		sysDictData = sysDictDataDao;
	}
	public static final String CACHE_DICT_MAP = "dictMap";

	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (SysDictData sysDictData : getDictList(type)){
				if (type.equals(sysDictData.getDictType()) && value.equals(sysDictData.getDictValue())){
					return sysDictData.getDictLabel();
				}
			}
		}
		return defaultValue;
	}

	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (SysDictData dict : getDictList(type)){
				if (type.equals(dict.getDictType()) && label.equals(dict.getDictLabel())){
					return dict.getDictValue();
				}
			}
		}
		return defaultLabel;
	}

	public static List<SysDictData> getDictList(String type){
		return 	sysDictData.findListByType(type);
	}

	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
//	public static String getDictListJson(String type){
//		return JsonMapper.toJsonString(getDictList(type));
//	}

}
