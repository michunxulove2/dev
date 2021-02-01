package com.bxwl.admin.sys.service;

import com.bxwl.admin.sys.constants.SysConstants;
import com.bxwl.admin.sys.dao.SysJobDao;
import com.bxwl.admin.sys.dao.SysUserDao;
import com.bxwl.admin.sys.model.PageBean;
import com.bxwl.admin.sys.model.SysJob;
import com.bxwl.admin.sys.model.SysUser;
import com.bxwl.admin.sys.model.SysUser2Job;
import com.bxwl.admin.sys.utils.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SysUserService {

    private static Logger logger = LoggerFactory.getLogger(SysUserService.class);

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysJobDao sysJobDao;
    @Autowired
    private SysOrgService sysOrgService;


    /**
     * 分页查询系统用户
     *
     * @param sysUser
     * @param pageBean
     * @return
     */
    public PageBean<SysUser> getSysUsersByPage(SysUser sysUser, PageBean<SysUser> pageBean) {
        try {
            pageBean.setWhere(new HashMap<String, Object>());
            pageBean.getWhere().put("orgId", sysUser.getOrgId());
            pageBean.getWhere().put("jobId", sysUser.getJobIds());
            pageBean.getWhere().put("name", sysUser.getNickname());
            pageBean.getWhere().put("tel", sysUser.getContactPhone());
            pageBean.setCount(sysUserDao.getSysUsersCount(pageBean));
            if (pageBean.getCount() > 0) {
                pageBean.setData(sysUserDao.getSysUsersByPage(pageBean));
            }
            pageBean.setCode(0);//正常返回
        } catch (Exception ex) {
            logger.error("分页获取系统用户出错：", ex);
            pageBean.setCode(200);//错误返回
            pageBean.setMsg("系统繁忙，请稍后再试!");
        }
        return pageBean;
    }

    /**
     * 根据userId查询系统用户信息
     *
     * @param userId
     * @return
     */
    public SysUser getSysUserById(String userId) {
        SysUser sysUser = sysUserDao.getSysUserById(userId);
        if (sysUser != null) {
            List<String> job = sysUserDao.getJobIdsByUserId(userId);
            sysUser.setOwnerJobIdList(job);
        }
        return sysUser;
    }

    /**
     * 根据机构id查询岗位信息
     *
     * @param orgId
     * @return
     */
    public List<SysJob> getJobsByOrgId(String orgId) {
        return sysJobDao.getJobsByOrgId(orgId);
    }

    /**
     * 查询所有岗位信息
     *
     * @return
     */
    public List<SysJob> getAllJobs() {
        return sysJobDao.getAllJobs();
    }

    /**
     * 新增系统用户
     *
     * @param sysUser
     * @param addSysUser
     */
    @Transactional
    public void addSysUser(SysUser sysUser, SysUser addSysUser) {
        addSysUser.setUserId(UuidUtil.uuid());
        addSysUser.setOrgId(sysUser.getOrgId());
        addSysUser.setLoginPwd(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(SysConstants.RAWPWD));
        addSysUser.setHasDefault(SysConstants.NO);
        String[] jobIds = addSysUser.getJobIds().split(",");
        List<SysUser2Job> sysUser2JobList = new ArrayList<SysUser2Job>();
        SysUser2Job sysUser2Job = null;
        for (String jobId : jobIds) {
            sysUser2Job = new SysUser2Job();
            sysUser2Job.setSysUserId(addSysUser.getUserId());
            sysUser2Job.setJobId(jobId);
            sysUser2JobList.add(sysUser2Job);
        }
        sysUserDao.addSysUser(addSysUser);
        sysUserDao.addSysUser2Jobs(sysUser2JobList);
    }

    /**
     * 新增系统用户（仅供旅游公司和商户使用）
     *
     * @param sysUser    调用者用户信息，获取方式：SessionUtils.getUser(request)
     * @param orgId      机构id，即旅游公司id或者商户id
     * @param type       调用者类型（1.旅游公司，2.商户，3.雪场商户,5.课程预约, 6餐饮商户 ）
     * @param addSysUser 创建用户信息，loginName(必设)、hasEnabled(必设)、loginPwd(必设)、nickname(选设)、contactPhone(选设)、sex(选设)、email(选设)
     * @param
     */
    @Transactional
    public void addSpecialSysUser(SysUser sysUser, String orgId, Integer type, SysUser addSysUser) {
        //新增机构
        sysOrgService.addSysOrg(orgId, sysUser.getOrgId(), addSysUser.getHasEnabled());
        //新增用户
        addSysUser.setUserId(UuidUtil.uuid());
        addSysUser.setOrgId(orgId);
        addSysUser.setLoginPwd(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(addSysUser.getLoginPwd()));
        addSysUser.setHasDefault(SysConstants.YES);
        addSysUser.setHasEnabled(addSysUser.getHasEnabled());
        //分配默认岗位权限 1.旅游公司默认岗位id，2.商户默认岗位id，3.雪场商户默认岗位id，5.课程预约商户默认岗位id，6餐饮商户
        List<SysUser2Job> sysUser2JobList = new ArrayList<SysUser2Job>();
        SysUser2Job sysUser2Job = new SysUser2Job();
        sysUser2Job.setSysUserId(addSysUser.getUserId());
        sysUser2Job.setJobId(type.toString());
        sysUser2JobList.add(sysUser2Job);
        sysUserDao.addSysUser(addSysUser);
        sysUserDao.addSysUser2Jobs(sysUser2JobList);
    }

    /**
     * 根据id删除系统用户
     *
     * @param userId
     */
    public void delSysUser(String userId) {
        sysUserDao.delSysUserById(userId);
    }

    /**
     * 更新系统用户
     *
     * @param sysUser
     */
    @Transactional
    public void updateSysUser(SysUser sysUser) {
        String[] jobIds = sysUser.getJobIds().split(",");
        List<SysUser2Job> sysUser2JobList = new ArrayList<SysUser2Job>();
        SysUser2Job sysUser2Job = null;
        for (String jobId : jobIds) {
            sysUser2Job = new SysUser2Job();
            sysUser2Job.setSysUserId(sysUser.getUserId());
            sysUser2Job.setJobId(jobId);
            sysUser2JobList.add(sysUser2Job);
        }
        sysUserDao.updateSysUser(sysUser);
        sysUserDao.delSysUser2JobsByUserId(sysUser.getUserId());
        sysUserDao.addSysUser2Jobs(sysUser2JobList);
    }

    /**
     * 检查登录名是否存在
     *
     * @param loginName
     * @return
     */
    public int checkLoginName(String loginName) {
        return sysUserDao.checkLoginName(loginName);
    }

    /**
     * 根据登录名和机构id修改用户密码
     *
     * @param orgId     机构id
     * @param loginName 登录名
     * @param pwd       新密码
     */
    public void updateUserPwd(String orgId, String loginName, String pwd) {
        SysUser sysUser = new SysUser();
        sysUser.setLoginPwd(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(pwd));
        sysUser.setOrgId(orgId);
        sysUser.setLoginName(loginName);
        sysUserDao.updateUserPwd(sysUser);
    }

    public List<SysUser> getSysUsers() {
        return sysUserDao.getSysUsers();
    }
}
