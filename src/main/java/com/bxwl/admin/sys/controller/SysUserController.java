package com.bxwl.admin.sys.controller;

import javax.servlet.http.HttpServletRequest;

import com.bxwl.admin.sys.aop.Operation;
import com.bxwl.admin.sys.constants.SysConstants;
import com.bxwl.admin.sys.service.SysDepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.ResultBean;
import com.bxwl.admin.sys.model.SysUser;
import com.bxwl.admin.sys.service.SysUserService;
import com.bxwl.admin.sys.utils.SessionUtils;

/**
 * 系统用户管理
 *
 * @author liuyu
 */
@Controller
@RequestMapping("admin/sys/user")
public class SysUserController {

    private static Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDepartmentService sysDepartmentService;

    @RequestMapping("user_pop")
    public String user_pop() {
        return "/admin/sys/user/user_pop";
    }

    @RequestMapping("user_index")
    public void user_index(HttpServletRequest request, Model model) {
        model.addAttribute("jobList", sysUserService.getJobsByOrgId(SessionUtils.getUser(request).getOrgId()));
    }

    @RequestMapping("user_data_list")
    public @ResponseBody
    PageBean<SysUser> res_data_list(HttpServletRequest request, SysUser param, PageBean<SysUser> pageBean) {
        param.setOrgId(SessionUtils.getUser(request).getOrgId());
        return sysUserService.getSysUsersByPage(param, pageBean);
    }

    @RequestMapping(value = "user_add", method = RequestMethod.GET)
    public void user_add_get(HttpServletRequest request, Model model, String userId) {
        try {
            model.addAttribute("jobList", sysUserService.getJobsByOrgId(SessionUtils.getUser(request).getOrgId()));
            model.addAttribute("departmentList", sysDepartmentService.getDepartments());
            model.addAttribute("initPwd", SysConstants.RAWPWD);
        } catch (Exception ex) {
            logger.error("进入新增系统用户界面出错：", ex);
        }
    }

    @Operation("新增系统用户")
    @RequestMapping(value = "user_add", method = RequestMethod.POST)
    public @ResponseBody
    ResultBean user_add_post(HttpServletRequest request, SysUser sysUser) {
        try {
            int isExists = sysUserService.checkLoginName(sysUser.getLoginName());
            if (isExists > 0) {
                return new ResultBean(200, "登录名重复，请重新输入!", null);
            }
            sysUserService.addSysUser(SessionUtils.getUser(request), sysUser);
            return new ResultBean(0, null, null);
        } catch (Exception ex) {
            logger.error("新增系统用户出错：", ex);
            return new ResultBean(200, "新增系统用户出错!", null);
        }
    }

    @Operation("删除系统用户")
    @RequestMapping("user_del")
    public @ResponseBody
    ResultBean user_del(String userId) {
        try {
            sysUserService.delSysUser(userId);
            return new ResultBean(0, null, null);
        } catch (Exception ex) {
            logger.error("删除系统用户出错：", ex);
            return new ResultBean(200, "删除系统用户出错!", null);
        }
    }

    @RequestMapping(value = "user_edit", method = RequestMethod.GET)
    public void user_edit_get(HttpServletRequest request, Model model, String userId) {
        try {
            model.addAttribute("sysUser", sysUserService.getSysUserById(userId));
            model.addAttribute("departmentList", sysDepartmentService.getDepartments());
            model.addAttribute("jobList", sysUserService.getJobsByOrgId(SessionUtils.getUser(request).getOrgId()));
        } catch (Exception ex) {
            logger.error("进入修改系统用户界面出错：", ex);
        }
    }

    @RequestMapping(value = "user_view", method = RequestMethod.GET)
    public void user_view_get(HttpServletRequest request, Model model, String userId) {
        try {
            model.addAttribute("sysUser", sysUserService.getSysUserById(userId));
            model.addAttribute("departmentList", sysDepartmentService.getDepartments());
            model.addAttribute("jobList", sysUserService.getAllJobs());
        } catch (Exception ex) {
            logger.error("进入系统用户详情界面出错：", ex);
        }
    }

    @Operation("修改系统用户")
    @RequestMapping(value = "user_edit", method = RequestMethod.POST)
    public @ResponseBody
    ResultBean user_edit_post(SysUser sysUser) {
        try {
            sysUserService.updateSysUser(sysUser);
            return new ResultBean(0, null, null);
        } catch (Exception ex) {
            logger.error("编辑系统用户出错：", ex);
            return new ResultBean(200, "编辑系统用户出错!", null);
        }
    }
}
