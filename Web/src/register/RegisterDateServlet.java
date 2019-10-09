package register;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class RegisterDateServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		doPost(request, response);  
     }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("request--->"+request.getRequestURL()+"===="+request.getParameterMap().toString());
		String name = request.getParameter("name"); 
		String password = request.getParameter("password");
		String sex=request.getParameter("sex");
		response.setContentType("text/html;charset=utf-8");
		if (name == null ||name.equals("") || password == null || password.equals("")) {
			System.out.println("用户名或密码为空");
			return;
		} 
		connection dbUtils = new connection();
		dbUtils.openConnect();
		Basebean data = new Basebean(); 
		Userbean userBean = new Userbean(); 
		data.setCode(-1);
		data.setData(userBean);
		data.setMsg("该账号存在");
		try {
			userBean=dbUtils.getuser(name, password);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		data.setData(userBean);
		Gson gson = new Gson();
		String json = gson.toJson(data);
		try {
			response.getWriter().println(json);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); 
		}
		dbUtils.closeconnect(); 
	}

}
