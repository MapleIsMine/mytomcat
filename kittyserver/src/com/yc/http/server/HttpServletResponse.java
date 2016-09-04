package com.yc.http.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpServletResponse {
	private OutputStream oos;
	private HttpServletRequest request;

	public HttpServletResponse(HttpServletRequest request, OutputStream oos) {
		this.oos = oos;
		this.request = request;
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
			protocal200 = "HTTP/1.1 200 OK\r\nContent-Type: image/JPEG\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		} else if ("PNG".equalsIgnoreCase(fileExtension)) {
			protocal200 = "HTTP/1.1 200 OK\r\nContent-Type: image/PNG\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		} else if ("json".equalsIgnoreCase(fileExtension)) {
			protocal200 = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: "
					+ bodylength + "\r\n\r\n";
		} else {
			protocal200 = "HTTP/1.0 200 OK\r\nContent-Type: text/html\r\nContent-Length: "
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

}
