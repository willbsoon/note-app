package com.willbsoon.na.api.advice.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CAuthenticationEntryPointException extends RuntimeException{
	public CAuthenticationEntryPointException(String msg, Throwable t) {
		super(msg,t);
	}
	public CAuthenticationEntryPointException(String msg) {
		super(msg);
	}
	public CAuthenticationEntryPointException() {
		super();
	}
}
