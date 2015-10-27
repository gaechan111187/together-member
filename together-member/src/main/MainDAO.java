package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import global.Constants;
import global.DatabaseFactory;
import global.Vendor;
import member.MemberVO;

public class MainDAO {
	private Connection con; // Connection DB와 연결
	private Statement stmt; // Statement 무언가를 서술, getter의 느낌
	private PreparedStatement pstmt;   //setter의 느낌
	private ResultSet rs; // ResultSet return 받아서 DB로 던짐
	private List<MainVO> list = new ArrayList<MainVO>();
	private MainVO member = new MainVO();
	
	
	public MainDAO() {
		con = DatabaseFactory.getDatabase(Vendor.ORACLE, Constants.ORACLE_ID, Constants.ORACLE_PASSWORD).getConnection();
	}

	// 친구목록불러오기
	public List<MainVO> selectMyFriends() {
		list = new ArrayList<MainVO>();
		try {
			rs = con.createStatement().executeQuery(member.selectMyFriends());
			while (rs.next()) {
				MainVO temp = new MainVO();
				temp.setName(rs.getString("name"));
				temp.setEmail(rs.getString("email"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 친구찾기
		public MainVO searchMyFriend(int searchPhone) {
			MainVO temp = new MainVO();
			try {
				rs = con.createStatement().executeQuery(member.searchMyFriend(searchPhone));
				temp.setName(rs.getString("name"));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return temp;
		}
	
	
	
	
	// 회원가입
	public int insert(MemberVO joinUsVO){
		int result = 0;
		try {
			pstmt = con.prepareStatement(joinUsVO.joinUs());
			pstmt.setString(1, joinUsVO.getName());
			pstmt.setInt(2, joinUsVO.getPhone());
			pstmt.setString(3, joinUsVO.getPassword());
			pstmt.setString(4, joinUsVO.getEmail());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
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
					temp.setPhone(rs.getInt("phone"));
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
	// 친구 검색 (핸드폰 넘버로 검색) 
	public MemberVO searchByFriends(int phoneNum){
		MemberVO temp = new MemberVO();
		try {
			rs = con.createStatement().executeQuery(joinUsVO.SearchByPhone(phoneNum));
			while (rs.next()) {
				temp.setName(rs.getString("name"));
				temp.setEmail(rs.getString("email"));
				temp.setPhone(rs.getInt("phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}
	*/
}