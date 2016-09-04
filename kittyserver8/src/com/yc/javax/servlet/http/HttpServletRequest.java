package com.yc.javax.servlet.http;

import com.yc.javax.servlet.ServletContext;
import com.yc.javax.servlet.ServletRequest;

public interface HttpServletRequest extends ServletRequest {
	
	public String getMethod();
	
	public String getRequestURI();
	
	public ServletContext getServletContext();
	
	//��������ͷ������ȡֵ
	public String getHeader(String name);
	
	public HttpSession getSession(   );
}
