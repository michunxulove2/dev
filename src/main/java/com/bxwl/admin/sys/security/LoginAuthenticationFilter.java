package com.bxwl.admin.sys.security;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private boolean postOnly = true;

    private SessionRegistry sessionRegistry;

    public LoginAuthenticationFilter(TokenBasedRememberMeServices tokenBasedRememberMeServices,SessionRegistry sessionRegistry) {
        this.setAuthenticationSuccessHandler(new LoginAuthenticationSuccess());
        this.setAuthenticationFailureHandler(new LoginAuthenticationFailure());
        this.setAuthenticationManager(getAuthenticationManager());
//        this.setRememberMeServices(tokenBasedRememberMeServices);
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String verifyCode = request.getParameter("verifyCode");
        String imei = request.getParameter("imei");
        request.getSession().setAttribute("imei",imei);
        if(StringUtil.isNotEmpty(verifyCode)){
            String verifyCodeUpper = verifyCode.toUpperCase();
            if(request.getSession() != null && request.getSession().getAttribute("verifyCode") != null){
                String serverVerifyCode = request.getSession().getAttribute("verifyCode").toString();
                if (StringUtils.isEmpty(verifyCodeUpper) || !verifyCodeUpper.equals(serverVerifyCode)) {
                    throw new VerifyCodeException("验证码错误");
                }
            }else{
                throw new VerifyCodeException("验证码错误");
            }
        }
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String username = this.obtainUsername(request);
            String password = this.obtainPassword(request);
            if (username == null) {
                username = "";
            }

            if (password == null) {
                password = "";
            }

            username = username.trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            //用户名密码验证通过后,注册session
            sessionRegistry.registerNewSession(request.getSession().getId(),authRequest.getPrincipal());
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }
}