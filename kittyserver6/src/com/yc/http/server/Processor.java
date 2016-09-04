package com.yc.http.server;

import com.yc.javax.servlet.ServletRequest;
import com.yc.javax.servlet.ServletResponse;

/**
 * ��Դ������: ����̬��̬����Դ 
 */
public interface Processor {
	
	/**
	 * ������Դ(������) �ķ�����
	 * @param request:�������:   ��������ͷ,�õ�  uri, method( http), parse, parameter
	 * @param response:��Ӧ����    ���.
	 */
	public void process(  ServletRequest request,   ServletResponse response     );
}
