package com.bxwl.admin.sys.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;

import com.bxwl.admin.sys.model.SysUser;
import com.bxwl.admin.sys.utils.SessionUtils;

public class CustomRememberMeAuthenticationFilter extends RememberMeAuthenticationFilter{

	private SessionRegistry sessionRegistry;

	public CustomRememberMeAuthenticationFilter(AuthenticationManager authenticationManager,
			RememberMeServices rememberMeServices,SessionRegistry sessionRegistry) {
		super(authenticationManager, rememberMeServices);
		this.sessionRegistry = sessionRegistry;
	}
	
	@Override
	protected void onSuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, Authentication authResult){
		SessionInformation info = sessionRegistry.getSessionInformation(request.getRequestedSessionId());
		if (info != null) {
			if (!info.isExpired()) {
				SysUser sysUser = (SysUser)authResult.getPrincipal();
				SessionUtils.login(request, sysUser);
			}else{
				getRememberMeServices().loginFail(request,response);
			}
		}else{
			getRememberMeServices().loginFail(request,response);
		}
	}

}
