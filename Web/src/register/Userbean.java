package register;

import java.io.Serializable;

public class Userbean  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String password;
	private String sex;
	public int setid() {
		return id;
	}
	public void getid(int id) {
		this.id=id;
	}
	public void getname(String name) {
		this.name=name;
	}
	public String setname() {
		return name;
	}
	public void getpassword(String password) {
		this.password=password;
	}
	public String setpassword() {
		return password;
	}
	public void getsex(String sex) {
		this.sex=sex;
	}
	public String setsex() {
		return sex;
	}
	

}
