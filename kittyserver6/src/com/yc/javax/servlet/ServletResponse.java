package com.yc.javax.servlet;

import java.io.PrintWriter;

public interface ServletResponse {
	
	/**
	 * ��ȡ����ַ���
	 */
	public PrintWriter getWriter();
	
	
	
	/**
	 * ��ȡ�����Դ������
	 * @return
	 */
	public String getContentType(); 

}
