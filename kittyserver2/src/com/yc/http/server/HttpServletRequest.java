package com.yc.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

public class HttpServletRequest {
	private String method;
	private String protocal;
	private String serverName;
	private int serverPort;
	private String requestURI; // ��Դ�ĵ�ַ
	private String requestURL;
	private String contextPath;
	private String realPath = System.getProperty("user.dir") + "\\webapps";

	private InputStream iis;

	public HttpServletRequest(InputStream iis) {
		this.iis = iis;
		parseRequest();
	}

	public String getRealPath() {
		return realPath;
	}

	private void parseRequest() {
		String requestInfoString = readFromInputStream(); // ���������ж�ȡ����ͷ
		if(   requestInfoString==null|| "".equals(requestInfoString)){
			return;
		}
		// ����requestInfo�ַ���
		parseRequestInfoString(requestInfoString);

	}

	private void parseRequestInfoString(String requestInfoString) {
		StringTokenizer st = new StringTokenizer(requestInfoString);
		if (st.hasMoreTokens()) {
			this.method = st.nextToken();
			this.requestURI = st.nextToken();   //    /res/aaaa/index.html
			this.protocal = st.nextToken();
			this.contextPath=   "/"+     this.requestURI.split("/")[1];   //    contextPathӦ��������·��  => /res
		}
		// TODO: ������ʱ���ܣ��ټ�

	}

	private String readFromInputStream() {
		// 1. ��input�ж������е�����( http����Э�� =�� protocal)
		String protocal = null;
		// TODO: ������ȡprotocal
		StringBuffer sb = new StringBuffer(1024 * 10);  // 10K
		int length = -1;
		byte[] bs = new byte[1024 * 10];
		try {
			length = this.iis.read(bs);
		} catch (IOException e) {
			e.printStackTrace();
			length = -1;
		}
		for (int j = 0; j < length; j++) {
			sb.append((char) bs[j]);
		}
		protocal = sb.toString();
		return protocal;
	}

	public String getMethod() {
		return method;
	}

	public String getProtocal() {
		return protocal;
	}

	public String getServerName() {
		return serverName;
	}

	public int getServerPort() {
		return serverPort;
	}

	public String getRequestURI() {
		return requestURI;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public String getContextPath() {
		return contextPath;
	}

}
