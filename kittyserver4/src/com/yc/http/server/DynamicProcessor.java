package com.yc.http.server;

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import com.yc.javax.servlet.Servlet;
import com.yc.javax.servlet.ServletContext;
import com.yc.javax.servlet.ServletRequest;
import com.yc.javax.servlet.ServletResponse;
import com.yc.javax.servlet.http.HttpServlet;
import com.yc.javax.servlet.http.HttpServletRequest;
import com.yc.javax.servlet.http.HttpServletResponse;

public class DynamicProcessor implements Processor {

	@Override
	// request�� requestURI=> /res/HelloServlet.do
	public void process(ServletRequest request, ServletResponse response) {
		try {
			// 1 ȡ��uri, ��uri��ȡ�������servlet����
			String uri = ((HttpServletRequest) request).getRequestURI();
			String servletName = uri.substring(uri.lastIndexOf("/") + 1,
					uri.lastIndexOf("."));
			Servlet servlet=null;
			
			//��application���ж��Ƿ������servletName
			ServletContext application =YcServletContext.getInstance();
			if(  application.getServlet(servletName)!=null){
				servlet=application.getServlet(servletName);
			}else{
				// 2. ��̬�ֽ������ �� res/��servlet.class�ļ�
				// URLClassLoader
				URL[] urls = new URL[1];
				urls[0] = new URL("file", null, YcConstants.KITTYSERVER_BASEPATH); // ??
				URLClassLoader ucl = new URLClassLoader(urls);
				// 3. URL��ַ => file:\\\
				// 4. Class urlclassloader.loadClass( ������� );
				Class c = ucl.loadClass(servletName);
				// 5. �Է������ʽ newInstance()���� servletʵ��.
				 servlet= (Servlet) c.newInstance(); // -> ���� ���췽��
				 application.setServlet(servletName, servlet);
				 if( servlet!=null&& servlet instanceof Servlet){
					 servlet.init();
				 }
			}
			// 6. �����������ڵķ�ʽ ������servlet�еĸ�����
			if (servlet != null && servlet instanceof Servlet) {
				// ��������ֻ�ܵ���������д�˸���ķ��������ܵ������������еķ���
				((HttpServlet) servlet).service((HttpServletRequest) request,
						(HttpServletResponse) response);
			}
		} catch (Exception e) {
			String bodyentity = e.toString();
			String protocal = gen500(bodyentity.getBytes().length);
			PrintWriter pw = response.getWriter();
			pw.println(protocal);
			pw.println(bodyentity);
			pw.flush();
		}

	}

	private String gen500(long bodylength) {
		String protocal500 = "HTTP/1.1 500 Internal Server Error\r\nContent-Type: text/html;charset=utf-8\r\nContent-Length: "
				+ bodylength + "\r\n\r\n";
		return protocal500;
	}

}
