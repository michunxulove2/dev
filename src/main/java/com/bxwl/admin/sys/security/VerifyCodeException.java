package com.bxwl.admin.sys.security;

import org.springframework.security.core.AuthenticationException;

public class VerifyCodeException extends AuthenticationException{
	
	private static final long serialVersionUID = 1L;

	public VerifyCodeException(String msg) {
		super(msg);
	}
	
}
