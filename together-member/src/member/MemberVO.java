package member;

import java.io.Serializable;

public class MemberVO implements Serializable{
	
	private String name;
	private int phone;
	private String password;
	private String email;

	private static final long serialVersionUID = 1L;
	public MemberVO(String name, int phone, String password, String email) {
		this.name = name;
		this.phone = phone;
		this.password = password;
		this.email = email;
	}
	
	public MemberVO() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}
	public int getPhone() {
		return phone;
	}
	public String getPassword() {
		return password;
	}
	public String getEmail() {
		return email;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * CRUD 
	 */
	
	
	// 회원가입 생성.
	public String joinUs(){
		String query = "insert into member "
				+ "(name, phone, password, email) values (?,?,?,?)";
		return query;
	}
	
	
	// 각회원에 대한 친구 생성.
	public String addFriends(){
		String query = "insert into friend (uphone, fphone) values (?,?)";
		return query;
	}
	
	
	
	
	// read, 회원 전화번호로 검색
	public String SearchByPhone(int phonenum){
		String query = "select * from member where phone = "+this.make(phonenum);
		return query;
	}
	

	public String selectOneBy(String email) { //email로 검색하기.
		String query = "select * from member where email = "+make(email);
		return query;
	}
	
	
	// Update
	
	
	// delete

	
	
	//
	public String make(Object any){
		return "'"+any+"'";
	}
	
	
	@Override
	public String toString() {
		return " [name=" + name + ", phone=" + phone + ", password=" + password + ", email=" + email + "]";
	}
}