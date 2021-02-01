package com.bxwl.admin.sys.security;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.bxwl.admin.sys.model.SysUser;
import com.bxwl.admin.sys.utils.SessionUtils;

public class LoginAuthenticationSuccess implements AuthenticationSuccessHandler{
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		SysUser sysUser = (SysUser)authentication.getPrincipal();
		SessionUtils.login(request, sysUser);
		String contentType = "application/json;charset=UTF-8";  
        response.setContentType(contentType);
        PrintWriter out = response.getWriter();  
        out.print("{\"sta\":200,\"msg\":\"登录成功\"}");  
        out.flush();  
        out.close();
	}

}
