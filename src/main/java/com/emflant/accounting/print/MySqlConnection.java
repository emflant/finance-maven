package com.emflant.accounting.print;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.emflant.common.EntLogger;

public class MySqlConnection {

	private static Connection conn;
	
	private MySqlConnection(){

	}
	
	public static Connection getConnection(String ip) {
		
		if(conn == null){
			synchronized (MySqlConnection.class) {
				if(conn == null){
					
					try {
						conn = DriverManager.getConnection("jdbc:mysql://"+ip+"/world?user=root&password=adminadmin");
						conn.setAutoCommit(false);
					} catch (SQLException e) {
						e.printStackTrace();
						EntLogger.info("DB 연결에 실패했습니다.");
					}
					
				}
				
			}
		}
		
		return conn;
	}
	
	
}
