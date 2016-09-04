package com.yc.http.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.yc.javax.servlet.http.Cookie;
import com.yc.javax.servlet.http.HttpServletResponse;
import com.yc.javax.servlet.http.JspWriter;

public class YcHttpServletResponse implements HttpServletResponse{
	private OutputStream oos;
	private YcHttpServletRequest request;
	private String contentType;
	
	private Cookie[] cookies=new Cookie[50];
	private int cookieIndex=0;

	public YcHttpServletResponse(YcHttpServletRequest request, OutputStream oos) {
		this.oos = oos;
		this.request = request;
		
		Cookie c=new Cookie(  YcConstants.SESSIONID, request.getSessionid()  );
		cookies[cookieIndex]=c;
		cookieIndex++;
	}
	
	
	@Override
	public void addCookie(Cookie cookie) {
		if(   cookieIndex>=cookies.length  ){
			return;
		}
		cookies[cookieIndex]=cookie;
		cookieIndex++;
	}

	@Override
	public JspWriter getJspWriter() {
		JspWriter jspWriter=new JspWriter(  this.oos, this  );
		return jspWriter;
	}

	@Override
	public Cookie[] getCookies() {
		Cookie[] cs=new Cookie[cookieIndex];
		for( int i=0;i<cookieIndex;i++){
			cs[i]=cookies[i];
		}
		return cs;
	}

	/*
	 * ��request��ȡ��uri 2. �ж��Ƿ��ڱ��ش������uriָ�����ļ� û��, 404 �� 3. ����������ȡ����ļ� 4.
	 * ����������ļ�д���ͻ���,Ҫ������Ӧ��Э��.
	 */
	public void sendRedirect() {
		String responseprotocal = null; // ��ӦЭ��ͷ
		byte[] fileContent = null; // ��Ӧ������
		String uri = request.getRequestURI();  //������Դ�ĵ�ַ
		File f = new File(request.getRealPath(), uri);   //������ļ�
		if (!f.exists()) {
			fileContent = readFile(new File(request.getRealPath(), request.getContextPath()+"/404.html"));
			responseprotocal = gen404(fileContent.length);
		} else {
			fileContent = readFile(f);
			responseprotocal = gen200(fileContent.length);
		}
		try {
			oos.write(responseprotocal.getBytes()); // дЭ��
			oos.flush();
			oos.write(fileContent); // д���ļ�
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private byte[] readFile(File f) {
		FileInputStream fis = null;
		//�ֽ����������:   ��ȡ���ֽ�����󣬴浽�ڴ�. 
		ByteArrayOutputStream boas = new ByteArrayOutputStream(); // �ֽ���������� (
																	// ���ֽ��������ݱ��浽�ڴ�
																	// )
		try {
			// ��ȡ����ļ�
			fis = new FileInputStream(f);
			byte[] bs = new byte[1024];
			int length = -1;
			while ((length = fis.read(bs, 0, bs.length)) != -1) {
				boas.write(bs, 0, length); // д���ڴ滺��
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return boas.toByteArray(); // һ���Եش��ڴ��ж�ȡ���е��ֽ����鷵��
	}

	/**
	 * Ҫ���Ǿ�̬�ļ�������
	 * @param bodylength
	 *            , ���ݵĳ���
	 * @return
	 */
	private String gen200(long bodylength) {
		String uri = this.request.getRequestURI(); // ȡ��Ҫ���ʵ��ļ��ĵ�ַ
		int index = uri.lastIndexOf(".");
		if (index >= 0) {
			index = index + 1;
		}
		String fileExtension = uri.substring(index); // �ļ��ĺ�׺��
		String protocal200 = "";
		if ("JPG".equalsIgnoreCase(fileExtension)
				|| "JPEG".equalsIgnoreCase(fileExtension)) {
			contentType="image/JPEG";
			protocal200 = "HTTP/1.1 200 OK\r\nContent-Type: "+contentType+"\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		} else if ("PNG".equalsIgnoreCase(fileExtension)) {
			contentType="image/PNG";
			protocal200 = "HTTP/1.1 200 OK\r\nContent-Type: "+ contentType+"\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		} else if ("json".equalsIgnoreCase(fileExtension)) {
			contentType="application/json";
			protocal200 = "HTTP/1.1 200 OK\r\nContent-Type: "+contentType+"\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		} else {
			contentType="text/html";
			protocal200 = "HTTP/1.0 200 OK\r\nContent-Type: "+contentType+"\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		}
		return protocal200;
	}

	/**
	 * ����404��Ӧ
	 * 
	 * @return
	 */
	private String gen404(long bodylength) {
		String protocal404 = "HTTP/1.1 404 File Not Found\r\nContent-Type: text/html\r\nContent-Length: "
				+ bodylength + "\r\n\r\n";
		return protocal404;
	}

	@Override
	public PrintWriter getWriter() {
		PrintWriter pw=new PrintWriter(   this.oos );
		return pw;
	}

	@Override
	public String getContentType() {
		return this.contentType;
	}



}
