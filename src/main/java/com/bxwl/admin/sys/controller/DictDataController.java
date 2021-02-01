package com.bxwl.admin.sys.controller;

import com.bxwl.admin.sys.aop.CustomException;
import com.bxwl.admin.sys.aop.Operation;
import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.ResultBean;
import com.bxwl.admin.sys.model.SysDictData;
import com.bxwl.admin.sys.model.SysDictType;
import com.bxwl.admin.sys.service.SysDictDataService;
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
 * 系统字典值管理
 *
 * @author xueyuliang
 */
@Controller
@RequestMapping("admin/sys/dictData")
public class DictDataController {
    @Autowired
    private SysDictDataService sysDictDataService;
    @Autowired
    private SysDictTypeService sysDictTypeService;

    private static Logger logger = LoggerFactory.getLogger(DictDataController.class);

    @RequestMapping("dictData_index")
    public void dictData_index(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        SysDictType sysDictType = sysDictTypeService.getById(id);
        model.addAttribute("dictType",sysDictType.getDictType());
    }

    @RequestMapping("dictData_data_list")
    public @ResponseBody
    PageBean<SysDictData> dictData_data_list(HttpServletRequest request, PageBean<SysDictData> pageBean) {
        String dictLabel = request.getParameter("dictLabel");
        String dictValue = request.getParameter("dictValue");
        String dictType = request.getParameter("dictType");
        HashMap<String, String> map = new HashMap<>();
        map.put("dictLabel", dictLabel);
        map.put("dictValue", dictValue);
        map.put("dictType", dictType);
        PageBean<SysDictData> page = sysDictDataService.findPage(map, pageBean);
        return page;
    }

    @RequestMapping(value = "dictData_add", method = RequestMethod.GET)
    public void dictData_add_get(HttpServletRequest request, Model model) {
        String dictType = request.getParameter("dictType");
        model.addAttribute("dictType",dictType);
    }

    @Operation("新增字典数据")
    @RequestMapping(value = "dictData_add", method = RequestMethod.POST)
    public @ResponseBody
    ResultBean dictData_add_post(HttpServletRequest request, SysDictData sysDictData) {
        try {
            sysDictDataService.save(sysDictData);
            return new ResultBean(0, null, null);
        } catch (CustomException e) {
            return new ResultBean(200, e.getMessage(), null);
        } catch (Exception ex) {
            logger.error("新增字典数据出错：", ex);
            return new ResultBean(200, "新增字典数据出错!", null);
        }
    }

    @Operation("删除字典数据")
    @RequestMapping("dictData_del")
    public @ResponseBody
    ResultBean dictData_del(String id) {
        try {
            sysDictDataService.delete(id);
            return new ResultBean(0, null, null);
        } catch (Exception ex) {
            logger.error("删除系统字典出错：", ex);
            return new ResultBean(200, "删除系统字典出错!", null);
        }
    }

    @RequestMapping(value = "dictData_edit", method = RequestMethod.GET)
    public void dictData_edit_get(HttpServletRequest request, Model model, String id) {
        try {
            SysDictData sysDictData = sysDictDataService.getById(id);
            model.addAttribute("sysDictData", sysDictData);
        } catch (Exception ex) {
            logger.error("进入修改字典数据界面出错：", ex);
        }
    }

    @Operation("修改字典数据")
    @RequestMapping(value = "dictData_edit", method = RequestMethod.POST)
    public @ResponseBody
    ResultBean dictData_edit_post(SysDictData sysDictData) {
        try {
            sysDictDataService.save(sysDictData);
            return new ResultBean(0, null, null);
        } catch (CustomException e) {
            return new ResultBean(200, e.getMessage(), null);
        } catch (Exception ex) {
            logger.error("编辑字典数据出错：", ex);
            return new ResultBean(200, "编辑字典数据出错!", null);
        }
    }

}
