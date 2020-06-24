package com.git.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Java & RDBMS(MySQL) ���� Ŭ����  
 * 
 * @author taehee.kwon
 * @since 2020.06.24
 */
public class RDBDao {
	final String NEWLINE = System.getProperty("line.separator");
	
	/**
	 * ������ �����ϰ� ����� �޾ƿ��� �޼���
	 * 
	 * @param query
	 * @param dbUrl (ex. jdbc:mysql://localhost/{DB NAME}?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC)
	 * @param user
	 * @param pwd
	 * @return
	 */
	public Object executeMySQLQuery(String query, String dbUrl, String user, String pwd) {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>> ();
		
		Connection conn = null;
		Statement stmt = null;
		
		System.out.println("[QUERY] " + query);
		
		try {
			conn = getMySQLConnection(dbUrl, user, pwd);
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				// �÷� Ÿ���� ����Ͽ� ������ ��´�
				int valInt = rs.getInt("{valInt's column name}");
				String valString = rs.getString("{valString's column name}");
				
				HashMap<String, Object> map = new HashMap<String, Object> ();
				map.put("{valInt's column name}", valInt);
				map.put("{valString's column name}", valString);
				result.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try { stmt.close(); } 
				catch (SQLException e) {}
			}

			if (conn != null) {
				try { conn.close(); } 
				catch (SQLException e) {}
			}
		}
		
		return result;
	}
	
	
	/**
	 * Connection�� �����ϴ� �޼���
	 * 
	 * @param dbUrl
	 * @param user
	 * @param pwd
	 * @return
	 */
	public Connection getMySQLConnection(String dbUrl, String user, String pwd) {
		Connection conn = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();		
		}
		
		try {
			conn = DriverManager.getConnection(dbUrl, user, pwd);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
