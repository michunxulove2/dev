package com.bxwl.admin.sys.security;

import com.bxwl.admin.sys.constants.SysConstants;
import com.bxwl.admin.sys.dao.SysResourceDao;
import com.bxwl.admin.sys.dao.SysUserDao;
import com.bxwl.admin.sys.model.SysOption;
import com.bxwl.admin.sys.model.SysUser;
import com.bxwl.admin.sys.service.SysOptionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysResourceDao sysResourceDao;
	@Autowired
	private SysOptionService sysOptionService;

	@Override
	public UserDetails loadUserByUsername(String loginName)
			throws UsernameNotFoundException {
		SysUser loginUser = sysUserDao.doLogin(loginName);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		if(loginUser == null){
			throw new UsernameNotFoundException("该用户名不存在");
		}
		if(loginUser!=null&&"N".equals(loginUser.getOrgHasEnabled())){
			throw new DisabledException("对不起，你的账号已被禁用，如有问题请联系管理员！");
		}
		String imei = request.getSession().getAttribute("imei").toString();
		if (StringUtil.isNotEmpty(imei)){
			SysOption config = sysOptionService.getConfig("sys_group");
			if(config!= null){
				JSONObject jsonObject = JSONObject.fromObject(config.getOptionValue());
				String isImei = jsonObject.get("imei").toString();
				if("true".equals(isImei)){
					if(!imei.equals(loginUser.getImei())){
						throw new ImeiException("IMEI配置错误，请联系管理员！");
					}
				}
			}
		}
		if(SysConstants.SUPER_ADMIN_ID.equals(loginUser.getUserId())) {//超级管理员
			loginUser.setResList(sysResourceDao.getAllResourcesTree());
			loginUser.getAuthorityList().add(new Authority(SysConstants.SUPER_ADMIN_JOB_ID));
		}else {//非超级管理员
			loginUser.setResList(sysResourceDao.getMyResourcesTree(loginUser.getUserId()));
			List<String> jobIdList = sysUserDao.getJobIdsByUserId(loginUser.getUserId());
			for(String jobId : jobIdList) {
				loginUser.getAuthorityList().add(new Authority(jobId));
			}
		}
		return loginUser;
	}
}
