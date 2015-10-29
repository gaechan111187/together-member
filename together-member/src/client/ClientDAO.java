package client;

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
public class ClientDAO {
	private Connection con; // Connection DB와 연결
	private Statement stmt; // Statement 무언가를 서술, getter의 느낌
	private PreparedStatement pstmt;   //setter의 느낌
	private ResultSet rs; // ResultSet return 받아서 DB로 던짐
	
	public ClientDAO() {
		con = DatabaseFactory.getDatabase(Vendor.ORACLE, Constants.ORACLE_ID, Constants.ORACLE_PASSWORD).getConnection();
	}


	public void requestQuitRoom() {
		// TODO Auto-generated method stub
		
	}


	public void requestSendWordTo(String data, String idTo) {
		// TODO Auto-generated method stub
		
	}


	public void requestSendWord(String words) {
		// TODO Auto-generated method stub
		
	}

	
}
