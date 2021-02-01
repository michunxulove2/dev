package com.bxwl.admin.sys.model;

import lombok.Data;

import java.util.Date;

/**
 * 系统岗位
 *
 * @author xue
 */
@Data
public class SysOption {

    private String id;//id
    private String groupName;//分组名称
    private String optionLabel;//配置名称
    private String optionName;//配置名称
    private String optionValue;//配置值
    private Date createDate;//创建时间

}
