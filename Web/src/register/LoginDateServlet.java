package register;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class LoginDateServlet extends HttpServlet{

	

	/**
	 * 
	 */
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
		if (dbUtils.panduan(name, password)) {
			data.setCode(-1);
			data.setData(userBean);
			data.setMsg("该账号已存在");
		} else if (!dbUtils.insertuser(name, password, sex)) {
			data.setCode(0);
			data.setMsg("注册成功!!");
			ResultSet rs = dbUtils.getUser();
			int id = -1;
			if (rs != null) {
				try {
					while (rs.next()) {
						if (rs.getString("name").equals(name)&& rs.getString("password").equals(password)&&rs.getString("sex").equals(sex)) {
							id = rs.getInt("id");
						}
					}
					userBean.getid(id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			userBean.getsex(sex);
			userBean.getname(name);
			userBean.getpassword(password);
			data.setData(userBean);
		} else {
			data.setCode(500);
			data.setData(userBean);
			data.setMsg("数据库错误");
		}
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

