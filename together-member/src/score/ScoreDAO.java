package score;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import global.Constants;
import global.DAO;
import global.DatabaseFactory;
import global.Vendor;


public class ScoreDAO extends DAO {
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ScoreVO score = new ScoreVO();
	private static ScoreDAO instance = new ScoreDAO();
	
	public static ScoreDAO getInstance() {
		return instance;
	}
	
	public ScoreDAO() {
		con = DatabaseFactory
				.getDatabase(Vendor.ORACLE, Constants.ORACLE_ID, Constants.ORACLE_PASSWORD)
				.getConnection();
	}
	
	// DML
	public int insert(ScoreVO o) {
		int result = 0;
		try {
			pstmt = con.prepareStatement(o.insert());
			pstmt.setInt(1, o.getJava());
			pstmt.setInt(2, o.getJsp());
			pstmt.setInt(3, o.getHtml());
			pstmt.setInt(4, o.getJavascript());
			pstmt.setInt(5, o.getOracle());
			pstmt.setInt(6, o.getSpring());
			pstmt.setString(7, o.getUserid());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;		
	}
	
	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	// DQL
	@Override
	public List<ScoreVO> selectAll() {
		List<ScoreVO> list = new ArrayList<ScoreVO>();
		try {
			rs = con.createStatement().executeQuery(score.selectAll());
			while (rs.next()) {
				ScoreVO temp = new ScoreVO();
				temp.setScore_seq(rs.getInt("score_seq"));
				temp.setJava(rs.getInt("java"));
				temp.setJsp(rs.getInt("jsp"));
				temp.setHtml(rs.getInt("html"));
				temp.setJavascript(rs.getInt("javascript"));
				temp.setOracle(rs.getInt("oracle"));
				temp.setSpring(rs.getInt("spring"));
				temp.setUserid(rs.getString("userid"));
				list.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return list;
	}
	
	@Override
	public Object selectOneBy(String key) {
		ScoreVO temp = new ScoreVO();
		try {
			rs = con.createStatement().executeQuery(score.selectOneBy(key));
			while (rs.next()) {
				temp.setScore_seq(rs.getInt("score_seq"));
				temp.setJava(rs.getInt("java"));
				temp.setJsp(rs.getInt("jsp"));
				temp.setHtml(rs.getInt("html"));
				temp.setJavascript(rs.getInt("javascript"));
				temp.setOracle(rs.getInt("oracle"));
				temp.setSpring(rs.getInt("spring"));
				temp.setUserid(rs.getString("userid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}
	public List selectSomeBy(String subject, int grade){
		List<ScoreVO> tempList = new ArrayList<ScoreVO>();
		try {
			rs = con.createStatement().executeQuery(score.selectSomeBy(subject,grade));
			while (rs.next()) {
				ScoreVO temp = new ScoreVO();
				temp.setScore_seq(rs.getInt("score_seq"));
				temp.setJava(rs.getInt("java"));
				temp.setJsp(rs.getInt("jsp"));
				temp.setHtml(rs.getInt("html"));
				temp.setJavascript(rs.getInt("javascript"));
				temp.setOracle(rs.getInt("oracle"));
				temp.setSpring(rs.getInt("spring"));
				temp.setUserid(rs.getString("userid"));
				tempList.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tempList;
	}
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}
}
