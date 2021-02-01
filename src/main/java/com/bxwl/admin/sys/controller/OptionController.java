package com.bxwl.admin.sys.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.bxwl.admin.sys.aop.Operation;
import com.bxwl.admin.sys.model.ResultBean;
import com.bxwl.admin.sys.model.SysOption;
import com.bxwl.admin.sys.service.SysOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 菜单资源管理
 *
 * @author liuyu
 */
@Controller
@RequestMapping("admin/sys/option")
public class OptionController {
    @Autowired
    private SysOptionService sysOptionService;

    private static Logger logger = LoggerFactory.getLogger(OptionController.class);


    @RequestMapping("option_index")
    public void option_index(Model model) {
        SysOption config = sysOptionService.getConfig("sys_group");
        SysOption printConfig = sysOptionService.getConfig("print_group");
        String value = "false";
        String printValue = "false";
        if (config != null) {
            value = config.getOptionValue();
        }
        if (printConfig != null) {
            printValue = printConfig.getOptionValue();
        }
        model.addAttribute("data", value);
        model.addAttribute("printValue", printValue);
    }

    @Operation("系统配置修改")
    @RequestMapping(value = "option_add", method = RequestMethod.POST)
    public @ResponseBody
    ResultBean option_add(HttpServletRequest request) {
        String groupName  = request.getParameter("groupName");
        String name = request.getParameter("name");
        String pay_url = request.getParameter("payUrl");
        String imei = request.getParameter("imei");
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("payUrl", pay_url);
        params.put("imei", imei);
        String jsonString = JSONUtils.toJSONString(params);
        try {
            SysOption config = sysOptionService.getConfig(groupName);
            if (config == null){
                sysOptionService.addSysOption(jsonString,groupName);
            }else{
                sysOptionService.updateSysOption(config.getId(),jsonString);
            }
            return new ResultBean(0, null, null);
        } catch (Exception ex) {
            logger.error("新增配置出错：", ex);
            return new ResultBean(200, "新增配置出错!", null);
        }
    }

    @RequestMapping(value = "option_add_print", method = RequestMethod.POST)
    public @ResponseBody
    ResultBean option_add_print(HttpServletRequest request) {
        String groupName  = request.getParameter("groupName");
        String title = request.getParameter("title");
        String weixin = request.getParameter("weixin");
        Map<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("weixin", weixin);
        String jsonString = JSONUtils.toJSONString(params);
        try {
            SysOption config = sysOptionService.getConfig(groupName);
            if (config == null){
                sysOptionService.addSysOption(jsonString,groupName);
            }else{
                sysOptionService.updateSysOption(config.getId(),jsonString);
            }
            return new ResultBean(0, null, null);
        } catch (Exception ex) {
            logger.error("新增配置出错：", ex);
            return new ResultBean(200, "新增配置出错!", null);
        }
    }
}
