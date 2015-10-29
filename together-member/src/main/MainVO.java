package main;

public class MainVO{
	private static final long serialVersionUID = 1L;
	private String name; //회원이름
	private String password; //비번
	private String phone; //전화번호
	private String email; //이메일
	public MainVO(String name, String phone, String password, String email) {
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.email = email;
	}
	
	public MainVO() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}
	public String getPhone() {
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
	public void setPhone(String phone) {
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
	// 친구목록 불러오기
	public String selectMyFriends(){								//매개변수 String phone 
		String query = "select name, email "
				+ "from member where phone "
				+ "in (select fphone "
				+ "from member m inner join friend f "
				+ "on m.phone = f.uphone where m.phone = 012)";		//m.phone 변경
		return query;
	}
	
	// 친구찾기
	public String selectMyFriend(String searchPhone){					// 매개변수 전화번호
		String query = "select name, phone, email from member where phone = "+make(searchPhone);		
		return query;
	}
	
	// 친구추가
	public String insertFriend(){
		String query = "insert into friend (f_seq, uphone, fphone) values (f_seq.nextval,?,?)";
		return query;
	}
		
	
	
	
	
	
/*	
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
*/
	public String make(Object any){
		return "'"+any+"'";
	}
		
	@Override
	public String toString() {
		return "JoinUsVO [name=" + name + ", phone=" + phone + ", password=" + password + ", email=" + email + "]";
	}
}