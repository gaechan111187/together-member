package member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import global.Constants;
import global.DAO;
import global.DatabaseFactory;
import global.Vendor;

public class MemberDAO extends DAO {
	private Connection con; // Connection DB와 연결
	private Statement stmt; // Statement 무언가를 서술, getter의 느낌
	private PreparedStatement pstmt;   //setter의 느낌
	private ResultSet rs; // ResultSet return 받아서 DB로 던짐
	private List<MemberVO> list = new ArrayList<MemberVO>();
	private MemberVO member = new MemberVO();
	

	private static MemberDAO instance = new MemberDAO(); // Singletone으로 바꿈

	public static MemberDAO getInstance() { // 싱글톤
		return instance;
	}

	public MemberDAO() {
		con = DatabaseFactory.getDatabase(Vendor.ORACLE, Constants.ORACLE_ID, Constants.ORACLE_PASSWORD).getConnection();
	}

	public int insert(MemberVO o) {
		int result = 0 ;
		try {
			pstmt = con.prepareStatement(o.insert());//커넥터에게 회원가입 양식지를 받는다.
			pstmt.setString(1, o.getUserid());  // ? 중에서 첫번째를 뜻함
			pstmt.setString(2, o.getPassword());
			pstmt.setString(3, o.getName());
			pstmt.setString(4, o.getBirth());
			pstmt.setString(5, o.getPhone());
			pstmt.setString(6, o.getEmail());
			pstmt.setString(7, o.getGender());
			pstmt.setString(8, o.getAddr());
			pstmt.setString(9, o.getProfile());
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int update(MemberVO o) {
		int result = 0 ;
		try {
			pstmt =con.prepareStatement(o.update());
			pstmt.setString(1, o.getPassword());
			pstmt.setString(2, o.getEmail());
			pstmt.setString(3, o.getPhone());
			pstmt.setString(4, o.getAddr());
			pstmt.setString(5, o.getProfile());
			pstmt.setString(6, o.getUserid());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	/**
	 * 객체가 생성되는 것
	 * Account a = new Account();  =>생성  baby 출산
	 * List<>list = new ArrayList<>();  => 다형성 입양 =>Deep Copy 깊은 복사
	 * stmt = con.createStatement(); => Shallow Copy 얕은복사
	 */
	public List<MemberVO> selectAll() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		try {
			stmt = con.createStatement(); // 쿼리를 실행하는 객체
			rs = stmt.executeQuery(member.selectAll());
			while (rs.next()) {
				MemberVO temp = new MemberVO();
				temp.setUserid(rs.getString("userid"));
				temp.setPassword(rs.getString("password"));
				temp.setName(rs.getString("name"));
				temp.setBirth(rs.getString("birth"));
				temp.setPhone(rs.getString("phone"));
				temp.setEmail(rs.getString("email"));
				temp.setGender(rs.getString("gender"));
				temp.setAddr(rs.getString("addr"));
				temp.setRegdate(rs.getString("regdate"));
				temp.setProfile(rs.getString("profile"));
				list.add(temp);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<MemberVO> selectByName(String name) {
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(member.selectByName(name));
			list.removeAll(list);
			while (rs.next()) {
				MemberVO temp = new MemberVO();	
				temp.setUserid(rs.getString("userid"));
				temp.setPassword(rs.getString("password"));
				temp.setName(rs.getString("name"));
				temp.setBirth(rs.getString("birth"));
				temp.setPhone(rs.getString("phone"));
				temp.setEmail(rs.getString("email"));
				temp.setGender(rs.getString("gender"));
				temp.setAddr(rs.getString("addr"));
				temp.setRegdate(rs.getString("regdata"));
				temp.setProfile(rs.getString("profile"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return list;
	}

	public MemberVO selectOneBy(String key) {
		MemberVO temp = new MemberVO();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(member.selectOneBy(key));
			while (rs.next()) {
				
				temp.setUserid(rs.getString("userid"));
				temp.setPassword(rs.getString("password"));
				temp.setName(rs.getString("name"));
				temp.setBirth(rs.getString("birth"));
				temp.setPhone(rs.getString("phone"));
				temp.setEmail(rs.getString("email"));
				temp.setGender(rs.getString("gender"));
				temp.setAddr(rs.getString("addr"));
				temp.setRegdate(rs.getString("regdate"));
				temp.setProfile(rs.getString("profile"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	public int count() {
		int temp = 0;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(member.count());

			while (rs.next()) {
				temp = rs.getInt("count");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	public int delete(String id) {
		int result = 0;
		try {
			pstmt=con.prepareStatement(member.delete(id));
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	public List<MemberVO> selectSomeBy(String s1, String s2){
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(member.selectSomeBy(s1, s2));
			list.removeAll(list);
			while (rs.next()) {
				MemberVO temp = new MemberVO();	
				temp.setUserid(rs.getString("userid"));
				temp.setPassword(rs.getString("password"));
				temp.setName(rs.getString("name"));
				temp.setBirth(rs.getString("birth"));
				temp.setPhone(rs.getString("phone"));
				temp.setEmail(rs.getString("email"));
				temp.setGender(rs.getString("gender"));
				temp.setAddr(rs.getString("addr"));
				temp.setRegdate(rs.getString("regdate"));
				temp.setProfile(rs.getString("profile"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	public MemberVO login(String id, String pass){
		MemberVO loginMember = new MemberVO();
		loginMember = this.selectOneBy(id);
		if (loginMember.getUserid() == null) {
			return null;
		}
		if (loginMember.getPassword().equals(pass)) {
			return loginMember;
		} 
		else {
			return null;
		}
	}
}
