package com.bxwl.admin.sys.security;

import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 自定义退出handler，删除cookie信息
 * 解决框架在项目路径不为/时删除不掉cookie的bug,修改自org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler
 * @author Fangbb
 * 2018-12-18
 */
public final class CustomLogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {

    private final List<String> cookiesToClear;

    public CustomLogoutHandler(String...cookiesToClear){
        Assert.notNull(cookiesToClear, "List of cookies cannot be null");
        this.cookiesToClear = Arrays.asList(cookiesToClear);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Iterator var4 = this.cookiesToClear.iterator();
        while(var4.hasNext()) {
            String cookieName = (String)var4.next();
            Cookie cookie = new Cookie(cookieName, (String)null);
            String cookiePath = this.getCookiePath(request);
            cookie.setPath(cookiePath);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }

    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }
}