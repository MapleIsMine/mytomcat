package com.yc.javax.servlet;

/**
 * Servlet�ӿ����������������ڵĻص�����
 * @author lenovo
 *
 */
public interface Servlet {

	
	public void init();
	
	public void destroy();
	
	public void service( ServletRequest request, ServletResponse response  );
	
}
