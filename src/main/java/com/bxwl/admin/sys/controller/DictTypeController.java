package com.bxwl.admin.sys.controller;

import com.bxwl.admin.sys.aop.CustomException;
import com.bxwl.admin.sys.aop.Operation;
import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.ResultBean;
import com.bxwl.admin.sys.model.SysDictType;
import com.bxwl.admin.sys.service.SysDictTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * 系统字典类型管理
 *
 * @author xueyuliang
 */
@Controller
@RequestMapping("admin/sys/dictType")
public class DictTypeController {
    @Autowired
    private SysDictTypeService sysDictTypeService;

    private static Logger logger = LoggerFactory.getLogger(DictTypeController.class);

    @RequestMapping("dictType_index")
    public void dictType_index(HttpServletRequest request, Model model) {

    }

    @RequestMapping("dictType_data_list")
    public @ResponseBody
    PageBean<SysDictType> dictType_data_list(HttpServletRequest request, PageBean<SysDictType> pageBean) {
        String dictName = request.getParameter("dictName");
        String dictType = request.getParameter("dictType");
        HashMap<String, String> map = new HashMap<>();
        map.put("dictName", dictName);
        map.put("dictType", dictType);
        PageBean<SysDictType> page = sysDictTypeService.findPage(map, pageBean);
        return page;
    }

    @RequestMapping(value = "dictType_add", method = RequestMethod.GET)
    public void dictType_add_get(HttpServletRequest request, Model model) {

    }

    @Operation("新增字典类型")
    @RequestMapping(value = "dictType_add", method = RequestMethod.POST)
    public @ResponseBody
    ResultBean dictType_add_post(HttpServletRequest request, SysDictType sysDictType) {
        try {
            sysDictTypeService.save(sysDictType);
            return new ResultBean(0, null, null);
        } catch (CustomException e) {
            return new ResultBean(200, e.getMessage(), null);
        } catch (Exception ex) {
            logger.error("新增字典类型出错：", ex);
            return new ResultBean(200, "新增字典类型出错!", null);
        }
    }

    @Operation("删除系统字典类型")
    @RequestMapping("dictType_del")
    public @ResponseBody
    ResultBean dictType_del(String id) {
        try {
            sysDictTypeService.delete(id);
            return new ResultBean(0, null, null);
        } catch (Exception ex) {
            logger.error("删除系统字典出错：", ex);
            return new ResultBean(200, "删除系统字典出错!", null);
        }
    }

    @RequestMapping(value = "dictType_edit", method = RequestMethod.GET)
    public void dictType_edit_get(HttpServletRequest request, Model model, String id) {
        try {
            SysDictType sysDict = sysDictTypeService.getById(id);
            model.addAttribute("sysDictType", sysDict);
        } catch (Exception ex) {
            logger.error("进入修改系统用户界面出错：", ex);
        }
    }

    @Operation("修改系统字典")
    @RequestMapping(value = "dictType_edit", method = RequestMethod.POST)
    public @ResponseBody
    ResultBean dictType_edit_post(SysDictType sysDictType) {
        try {
            sysDictTypeService.save(sysDictType);
            return new ResultBean(0, null, null);
        } catch (CustomException e) {
            return new ResultBean(200, e.getMessage(), null);
        } catch (Exception ex) {
            logger.error("编辑系统字典出错：", ex);
            return new ResultBean(200, "编辑系统字典出错!", null);
        }
    }

}
