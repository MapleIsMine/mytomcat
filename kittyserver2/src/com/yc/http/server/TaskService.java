package com.yc.http.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import threadpool.Taskable;

public class TaskService implements Taskable {
	private Socket socket;
	private InputStream iis;
	private OutputStream oos;
	private boolean flag;

	public TaskService(Socket socket) {
		this.socket = socket;
		try {
			this.iis = this.socket.getInputStream();
			this.oos = this.socket.getOutputStream();
			flag = true;
		} catch (IOException e) {
			YcConstants.logger.error(" failed to get stream  ",e);
			flag=false;
			throw new RuntimeException ( e);
		}
	}

	@Override   // HTTPЭ����һ���������Ӧ.  http����״̬(һ���������Ӧ�ͻ�Ͽ�����..
	public Object doTask() {
		if( flag){
			//��װһ��HttpServletRequest����
			HttpServletRequest request=new HttpServletRequest( this.iis );  // uri 
			//��װһ��HttpServletResponse����
			HttpServletResponse response=new HttpServletResponse(   request,    this.oos);
			response.sendRedirect();
		}
		try {
			this.socket.close();
		} catch (IOException e) {
			YcConstants.logger.error(" failed to close connection to client  ",e);
		}
		return null;
	}

}
