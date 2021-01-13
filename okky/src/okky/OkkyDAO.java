package okky;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class OkkyDAO {
	
	private Connection conn;
	private ResultSet rs;
	
	public OkkyDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/okky?serverTimezone=UTC";
			String dbID = "root";
			String dbPassword = "mysql";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ""; //데이터베이스 오류
	}
	
	public int getNext() {
		String SQL = "SELECT okkyid FROM okky ORDER BY okkyid DESC";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;
			}
			return 1; //첫 번째 게시물인 경우
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public int write(String okkyTitle, String userID, String okkyContent) {
		String SQL = "INSERT INTO OKKY VALUES (?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext());
			pstmt.setString(2, okkyTitle);
			pstmt.setString(3, userID);
			pstmt.setString(4, getDate());
			pstmt.setString(5, okkyContent);
			pstmt.setInt(6, 1);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public ArrayList<Okky> getList(int pageNumber){
		String SQL = "SELECT * FROM OKKY WHERE okkyID < ? AND okkyAvailable = 1 ORDER BY okkyID DESC LIMIT 10";
		ArrayList<Okky> list = new ArrayList<Okky>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Okky okky = new Okky();
				okky.setOkkyId(rs.getInt(1));
				okky.setOkkyTitle(rs.getString(2));
				okky.setUserID(rs.getString(3));
				okky.setOkkyDate(rs.getString(4));
				okky.setOkkyContent(rs.getString(5));
				okky.setOkkyAvailable(rs.getInt(6));
				list.add(okky);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean nextPage(int pageNumber) {
		String SQL = "SELECT * FROM OKKY WHERE okkyID < ? AND okkyAvailable = 1";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Okky getOkky(int okkyID) {
		String SQL = "SELECT * FROM OKKY WHERE okkyID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, okkyID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Okky okky = new Okky();
				okky.setOkkyId(rs.getInt(1));
				okky.setOkkyTitle(rs.getString(2));
				okky.setUserID(rs.getString(3));
				okky.setOkkyDate(rs.getString(4));
				okky.setOkkyContent(rs.getString(5));
				okky.setOkkyAvailable(rs.getInt(6));
				return okky;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update(int okkyID, String okkyTitle, String okkyContent) {
		String SQL = "UPDATE OKKY SET okkyTitle = ?, okkyContent= ? WHERE okkyID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, okkyTitle);
			pstmt.setString(2, okkyContent);
			pstmt.setInt(3, okkyID);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	public int delete(int okkyID) {
		String SQL = "UPDATE OKKY SET okkyAvailable = 0 WHERE okkyID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, okkyID);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //데이터베이스 오류
	}
	
	
}
