package com.yc.http.server;

import org.apache.log4j.Logger;

public class YcConstants {
	/* ����server.xml�ļ����� */
	public final static String SERVERCONFIG="conf/server.xml";
	
	
	/** ������·��  */
	public final static String KITTYSERVER_BASEPATH=System.getProperty("user.dir");
	
	/**
	 * ��־����
	 */
	public final static Logger logger=Logger.getLogger(YcConstants.class);
	
	/**
	 * session��ͷ����Ϣ�ļ���
	 * 
	 */
	public final static String SESSIONID="kittysessionid";
}
