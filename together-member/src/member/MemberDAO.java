package member;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import global.Constants;
import global.DatabaseFactory;
import global.Vendor;


public class MemberDAO {

	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	MemberVO joinUsVO = new MemberVO();
	private PreparedStatement pstmt;
	public MemberDAO() {
		con = DatabaseFactory.getDatabase(Vendor.ORACLE, Constants.ORACLE_ID, Constants.ORACLE_PASSWORD)
				.getConnection();
	}

	
	
	// 회원가입
	public int insert(MemberVO joinUsVO){
		int result = 0;
		try {
			pstmt = con.prepareStatement(joinUsVO.joinUs());
			pstmt.setString(1, joinUsVO.getName());
			pstmt.setString(2, joinUsVO.getPhone());
			pstmt.setString(3, joinUsVO.getPassword());
			pstmt.setString(4, joinUsVO.getEmail());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
/*	// email로 검색 - 로그인에 사용
	public JoinUsVO selectOneBy(String email) {
		JoinUsVO temp = new JoinUsVO();
		try {
			rs = con.createStatement().executeQuery(joinUsVO.selectOneBy(email));
			while (rs.next()) {
				temp.setName(rs.getString("name"));
				temp.setPhone(rs.getInt("phone"));
				temp.setPassword(rs.getString("password"));
				temp.setEmail(rs.getString("email"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp; // email로 검색한 유저 가져옴.
	}
	*/
	
	
	
	//로그인 프로그램 (이메일과 비밀번호로)
	public Object login(String email, String password){
		MemberVO temp = new MemberVO();
		Object result = null;
		try {
			rs = con.createStatement().executeQuery(temp.selectOneBy(email));
			while (rs.next()) {
				if (rs.getString("email") == null) {
					result = "아이디가 존재하지 않습니다.";
				}
				if (rs.getString("password").equals(password)) {
					temp.setName(rs.getString("name"));
					temp.setPhone(rs.getString("phone"));
					temp.setPassword(rs.getString("password"));
					temp.setEmail(email);
					result = temp;
				} else {
					result = "비밀번호가 일치하지 않습니다, 로그인을 다시 해주세요.";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	
	/*
	//로그인 
	public Object login(String email, String password) { // 로그인 프로그램
		JoinUsVO loginMember = new JoinUsVO();
		loginMember = this.selectOneBy(email); // id 담김.

		if (loginMember.getEmail() == null) {
			return "아이디가 존재하지 않습니다.";
		}
		if (loginMember.getPassword().equals(password)) {
			return loginMember;
		} else {
			return "비밀번호가 일치하지 않습니다, 로그인을 다시 해주세요.";
		}
	}
	*/

	
	
	// 친구 검색 (핸드폰 넘버로 검색) 
	public MemberVO searchByFriends(int phoneNum){
		MemberVO temp = new MemberVO();
		try {
			rs = con.createStatement().executeQuery(joinUsVO.SearchByPhone(phoneNum));
			while (rs.next()) {
				temp.setName(rs.getString("name"));
				temp.setEmail(rs.getString("email"));
				temp.setPhone(rs.getString("phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}
	
}
/*	// 로그인 한 사용자의 폰넘버와 친구의 폰넘버를 매칭시켜 테이블에 넣어줘는 함수.
	public void addFrieds() {
		
		this.searchByFriends()// 친구의 핸드폰 번호
	}
*/	


