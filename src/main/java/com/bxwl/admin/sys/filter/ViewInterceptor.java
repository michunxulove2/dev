package com.bxwl.admin.sys.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.bxwl.admin.sys.model.SysResource;
import com.bxwl.admin.sys.model.SysUser;
import com.bxwl.admin.sys.utils.SessionUtils;

public class ViewInterceptor implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		SysUser sysUser = SessionUtils.getUser(httpRequest);
		String contextPath = httpRequest.getContextPath();
		String path = httpRequest.getRequestURI().substring(contextPath.length());
		if(sysUser != null) {
			for(SysResource sysRes : sysUser.getResList()) {
				if(path.equals(sysRes.getResUrl())) {
					request.setAttribute("viewId", sysRes.getResId());
					break;
				}
			}
		}
		chain.doFilter(httpRequest, response);
	}

	@Override
	public void destroy() {
		
	}

}
