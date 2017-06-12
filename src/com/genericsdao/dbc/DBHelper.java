package com.genericsdao.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//
public class DBHelper {

	// sqlite
	private static final String dbDriver = "org.sqlite.JDBC";
	private static final String dbUrl = "jdbc:sqlite:";
	private static final String DB_NAME = "GenericsDaoDB.db3"; // "c:/testDB.db3";
															// //数据库名称
	private static final String dbUser = "";
	private static final String dbPwd = "";

	//mysql
//	private static final String USER = "root";
//	private static final String PASSWORD = "";
//	private static final String dbDriver = "com.mysql.jdbc.Driver";
//	private static final String URL = "jdbc:mysql://localhost:3306/usermanager";
	
	private static Connection con;

	// 获取数据库连接对象
	public static Connection getConnection() {
		if (con == null) {
			try {
				Class.forName(dbDriver);
				con = DriverManager.getConnection(dbUrl+DB_NAME, dbUser, dbPwd);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			return con;
		}
		return con;
	}

	public static PreparedStatement getPreparedStatement(String sql)
			throws SQLException {
		return getConnection().prepareStatement(sql);
	}

	public static PreparedStatement setPreparedStatementParam(
			PreparedStatement statement, Object obj[]) throws SQLException {
		for (int i = 0; i < obj.length; i++) {
			statement.setObject(i + 1, obj[i]);
		}
		return statement;
	}

	// 释放资源
	public static void release(PreparedStatement ps, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e) {
			
			// TODO: handle exception
		}
	}

}
