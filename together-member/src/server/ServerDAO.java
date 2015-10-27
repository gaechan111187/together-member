package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import global.Command;
import global.Constants;
import global.DAO;
import global.DatabaseFactory;
import global.Vendor;
import member.MemberVO;

public class ServerDAO {
	private Connection con; // Connection DB와 연결
	private Statement stmt; // Statement 무언가를 서술, getter의 느낌
	private PreparedStatement pstmt;   //setter의 느낌
	private ResultSet rs; // ResultSet return 받아서 DB로 던짐
	private List<ServerVO> list = new ArrayList<ServerVO>();
	private ServerVO member = new ServerVO();
	
	
	public ServerDAO() {
		con = DatabaseFactory.getDatabase(Vendor.ORACLE, Constants.ORACLE_ID, Constants.ORACLE_PASSWORD).getConnection();
	}
	
	public MemberVO confirmLogin(String email, String password) {
		MemberVO temp = null;
		try {
			rs = con.createStatement().executeQuery("select * from member m inner join friend f on m.phone = F.UPHONE where email = " + makeQuery(email) + " and password = " + makeQuery(password));
			while (rs.next()) { // 첫번째는 내정보 나머지는 친구정보
				temp = new MemberVO();
				temp.setEmail(rs.getString("email"));
				temp.setName(rs.getString("name"));
				temp.setPassword(rs.getString("password"));
				//temp.setPhone(rs.getString("phone"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	public String makeQuery(String str) {
		return "'" + str + "'";
	}
}
