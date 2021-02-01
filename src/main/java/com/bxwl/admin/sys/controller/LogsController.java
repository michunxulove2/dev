package com.bxwl.admin.sys.controller;

import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.SysLogs;
import com.bxwl.admin.sys.service.SysLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("admin/sys/logs")
public class LogsController {
    @Autowired
    private SysLogsService sysLogsService;
    @RequestMapping("logs_index")
    public void logs_index() {

    }

    @RequestMapping("logs_data_list")
    public @ResponseBody PageBean<SysLogs> logs_data_list(HttpServletRequest request, PageBean<SysLogs> pageBean){
        String name = request.getParameter("userName");
        return sysLogsService.getLogsByPage(name,pageBean);
    }
}
