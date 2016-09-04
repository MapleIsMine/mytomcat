package com.yc.javax.servlet.http;

import com.yc.javax.servlet.ServletContext;
import com.yc.javax.servlet.ServletRequest;

public interface HttpServletRequest extends ServletRequest {
	/**
	 * ����ķ���
	 * @return
	 */
	public String getMethod();
	
	public String getRequestURI();
	
	public ServletContext getServletContext();
}
