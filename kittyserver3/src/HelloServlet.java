import java.io.IOException;





import com.yc.javax.servlet.ServletRequest;
import com.yc.javax.servlet.ServletResponse;
import com.yc.javax.servlet.http.HttpServlet;
import com.yc.javax.servlet.http.HttpServletRequest;
import com.yc.javax.servlet.http.HttpServletResponse;


public class HelloServlet extends HttpServlet {

	  public HelloServlet() {
	        super();
	        System.out.println("HelloServlet�Ĺ��췽��");
	    }
		
		public void init(){
			super.init();
			System.out.println("init����");
		}
		
		
		public void service(HttpServletRequest arg0, HttpServletResponse arg1){
			System.out.println("service��������...");
			super.service(arg0, arg1);
		}
		
		
		
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
			System.out.println("doGet()");
			doPost(request, response);
		}
		protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
			System.out.println("doPost��������");
			int r=5/0;
			
			
		}

	
	
}
