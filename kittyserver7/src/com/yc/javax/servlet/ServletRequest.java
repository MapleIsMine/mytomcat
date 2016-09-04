package com.yc.javax.servlet;

import java.util.Map;

public interface ServletRequest {

	public String getRealPath();
	
	public Object getAttribute(   String key );
	
	public void setAttribute(  String key, Object value);
	
	/**
	 * ��ȡ   ͨ��    http://localhost:8080/xxx/xxx.jsp?name=a&age=3
	 * @param key 
	 * @return
	 */
	public String getParameter( String key);
	
	public Map<String,String> getParameterMap();
	
	/**
	 * ��������: 1. ������uri    2. ����������name, age    3. ����������ķ�ʽ get/post/head
	 */
	public void parse();
	
	public String getServerName();
	
	public int getServerPort();
	
	
	
}
