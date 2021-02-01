package com.bxwl.admin.sys.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class SysLogs implements Serializable {
    private String id;

    private String userName; //用户名

    private String operation; //操作

    private String method; //方法名

    private String params; //参数

    private String ip; //ip地址

    private Date createDate; //操作时间
}
