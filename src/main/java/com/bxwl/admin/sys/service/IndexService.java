package com.bxwl.admin.sys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bxwl.admin.sys.dao.SysUserDao;
import com.bxwl.admin.sys.model.ResultBean;
import com.bxwl.admin.sys.model.SysUser;
import com.bxwl.admin.sys.model.UpdatePwd;

@Service
public class IndexService {
	
	private static Logger logger = LoggerFactory.getLogger(IndexService.class);
	
	@Autowired
	private SysUserDao sysUserDao;
	
	public ResultBean updatePwd(SysUser sysUser, UpdatePwd updatePwd) {
		try {
			if(!updatePwd.getPassword().equals(updatePwd.getRepassword())) {
				return new ResultBean(200, "修改密码失败，两次输入的密码不一致，请检查!", null);
			}
			PasswordEncoder pwdEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
			if(pwdEncoder.matches(updatePwd.getPassword(), sysUser.getLoginPwd())) {
				return new ResultBean(200, "修改密码失败，输入新密码与旧密码不能相同!", null);
			}
			if(!pwdEncoder.matches(updatePwd.getOldPassword(), sysUser.getLoginPwd())) {
				return new ResultBean(200, "修改密码失败，旧密码输入错误!", null);
			}
			sysUser.setLoginPwd(pwdEncoder.encode(updatePwd.getPassword()));
			sysUserDao.updatePwd(sysUser);
			return new ResultBean(0, null, null);
		}catch(Exception ex) {
			logger.error("修改密码失败：",ex);
			return new ResultBean(200, "修改密码失败，请稍后再试!", null);
		}
	}

}
