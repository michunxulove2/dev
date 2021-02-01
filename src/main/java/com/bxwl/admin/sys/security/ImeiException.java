package com.bxwl.admin.sys.security;

import org.springframework.security.core.AuthenticationException;

public class ImeiException extends AuthenticationException{

	private static final long serialVersionUID = 1L;

	public ImeiException(String msg) {
		super(msg);
	}
	
}
