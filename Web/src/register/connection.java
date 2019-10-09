package register;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class connection {
	private Connection cn;
	private String url="jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
	private String name="root";
	private String password="123456";
	private Statement st;
	private ResultSet rs;
	
	
	
	public void openConnect(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			cn=DriverManager.getConnection(url,name,password);
			if(cn!=null) {
				System.out.println("连接成功！");
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}	
	}
	public ResultSet getUser() {
		try {
			st=cn.createStatement();
			rs=st.executeQuery("select * from user");
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	public boolean panduan(String user_name,String user_password) {
		boolean a=false;
		try {
			System.out.println("判断用户名密码");
			st=cn.createStatement();
			rs=st.executeQuery("select * from user");
			if(rs!=null) {
				while(rs.next()) {
					if(rs.getString("name").equals(user_name)) {
						if(rs.getString("password").equals(user_password)) {
							a=true;
							break;
						}
					}
				}
			}
			
		}catch(Exception e) {
			a=false;
			e.printStackTrace();
		}
		return a;
	}
	
	public boolean insertuser(String name,String password,String sex) {
		String sql=" insert into user (name,password,sex) values ( " + "'" + name + "', " + "'" + password+ "', " + "'" +sex+ "' )";
		try {
			st=cn.createStatement();
			return st.execute(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	public Userbean getuser(String name,String password) throws SQLException {
		String sql = "select * from user where  name =? and password = ?";
		PreparedStatement pr=cn.prepareStatement(sql);
		pr.setString(1, name);
		pr.setString(2, password);
	    rs = pr.executeQuery();
	    Userbean user=new Userbean();
	    while(rs.next()) {
	    user.getid(rs.getInt(1));
	    user.getname(rs.getString(2));
	    user.getpassword(rs.getString(3));
	    user.getsex(rs.getString(4));
	    }
		return user;   
	}
	
	public void closeconnect() {
	try {
		if(cn!=null) {
			cn.close();
		}
		if(st!=null) {
			st.close();
		}
		if(rs!=null) {
			rs.close();
		}
		System.out.println("关闭成功！");
	}catch(Exception e) {
		e.printStackTrace();
	}
	
}
}
