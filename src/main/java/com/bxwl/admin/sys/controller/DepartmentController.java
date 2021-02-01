package com.bxwl.admin.sys.controller;

import com.bxwl.admin.sys.aop.Operation;
import com.bxwl.admin.sys.model.*;
import com.bxwl.admin.sys.service.SysDepartmentService;
import com.bxwl.admin.sys.utils.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("admin/sys/department")
public class DepartmentController {
    private static Logger logger = LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    private SysDepartmentService sysDepartmentService;
    @RequestMapping("department_index")
    public void department_index() {

    }

    @RequestMapping("department_data_list")
    public @ResponseBody PageBean<SysDepartment> department_data_list(HttpServletRequest request, PageBean<SysDepartment> pageBean){
        String name = request.getParameter("name");
        return sysDepartmentService.getDepartmentsByPage(name,pageBean);
    }

    @RequestMapping(value="department_add",method= RequestMethod.GET)
    public void department_add(HttpServletRequest request){

    }
    @Operation("新增部门")
    @RequestMapping(value="department_add",method= RequestMethod.POST)
    public @ResponseBody ResultBean department_add(SysDepartment sysDepartment){
        try {
            sysDepartmentService.addSysDepartment(sysDepartment);
            return new ResultBean(0,null,null);
        }catch(Exception ex) {
            logger.error("新增部门出错：",ex);
            return new ResultBean(200,"新增部门出错!",null);
        }
    }


    @RequestMapping("department_del")
    @Operation("删除部门")
    public @ResponseBody ResultBean department_del(String id) {
        try {
            sysDepartmentService.delSysDepartment(id);
            return new ResultBean(0, null,null);
        }catch(Exception ex) {
            logger.error("删除部门信息出错：",ex);
            return new ResultBean(200, "删除部门信息出错!",null);
        }
    }

    @RequestMapping(value="department_edit",method=RequestMethod.GET)
    public void department_edit_get(HttpServletRequest request, Model model, String id){
        try {
            SysDepartment sysDepartment = sysDepartmentService.getSysDepartmentById(id);
            model.addAttribute("sysDepartment",sysDepartment );
        }catch(Exception ex) {
            logger.error("进入修改部门信息界面出错：",ex);
        }
    }
    @Operation("编辑部门信息")
    @RequestMapping(value="department_edit",method=RequestMethod.POST)
    public @ResponseBody ResultBean department_edit_post(SysDepartment sysDepartment) {
        try {
            sysDepartmentService.updateSysDepartment(sysDepartment);
            return new ResultBean(0, null,null);
        }catch(Exception ex) {
            logger.error("编辑部门信息出错：",ex);
            return new ResultBean(200, "编辑部门信息出错!",null);
        }
    }
}
