package server;

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

	
}
