package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

public class DBUtil {
	// SqlServer桥连方式
	// private static final String dbDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
	// private static final String dbUrl = "jdbc:odbc:restrant";
	// private static final String dbUser = "sa";
	// private static final String dbPwd = "";

	// private static final String dbDriver =
	// "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	// private static final String dbUrl =
	// "jdbc:sqlserver://localhost:1433;databaseName=restrant";
	// private static final String dbUser = "sa";
	// private static final String dbPwd = "";

	// MYSQL
	// private static final String dbDriver = "com.mysql.jdbc.Driver";
	// private static final String dbUrl = "jdbc:mysql://localhost:3306/jxc";
	// private static final String dbUser = "root";
	// private static final String dbPwd = "sa";
	// private static final Connection conn = null;

	// sqlite
	private static final String dbDriver = "org.sqlite.JDBC";
	private static final String dbUrl = "jdbc:sqlite:test.db";
	private static final String dbUser = "";
	private static final String dbPwd = "";

	/*
	 * 连接字符串
	 */
	public static Connection getConn() {
		Connection Conn = null;
		try {
			// 1.加载驱动程序
			Class.forName(dbDriver);
			// 2.建立数据库连接
			Conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Conn;
	}

	/*
	 * 关闭连接
	 */

	public static void CloseAll(Connection conn, PreparedStatement pStmt, ResultSet rs) {
		try {
			// 5.关闭连接
			if (rs != null) {
				rs.close();
			}
			if (pStmt != null) {
				pStmt.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 
	 * 执行带参数的查询的方法
	 */
	public static ResultSet ExecuteQuery(String sql, Object[] param) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try {
			conn = getConn();
			// 3.建立Statement语句执行对象
			pStmt = conn.prepareStatement(sql);
			// pStmt = conn.prepareStatement(sql,
			// ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (param != null && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					pStmt.setObject(i + 1, param[i]);
				}
			}
			// 4.建立ResultSet结果集,执行SQL命令
			rs = pStmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	/*
	 * 
	 * 执行无参的查询的方法
	 */
	public static ResultSet ExecuteQuery(String sql) {
		return ExecuteQuery(sql, null);
	}

	/** 把结果集转成Object[][] */
	public static Object[][] ResultSetToObjectArray(ResultSet rs) {
		Object[][] d2Array = null;
		if (rs == null) {
			return d2Array;
		}
		try {
			rs.last();
			int rowCount = rs.getRow();
			d2Array = new Object[rowCount][];
			ResultSetMetaData md = rs.getMetaData();// 获取记录集的元数据
			int columnCount = md.getColumnCount();// 列数
			rs.first();
			int k = 0;
			while (rs.next()) {
				// System.out.println("i" + k);
				Object[] row = new Object[columnCount];
				for (int i = 0; i < columnCount; i++) {
					row[i] = rs.getObject(i + 1).toString();
				}
				d2Array[k] = row;
				k++;
			}
		} catch (Exception e) {
		}
		return d2Array;
	}

	/** 把结果集转成Vector<Object> */
	public static Vector ResultSetToObjectVector(ResultSet result) {
		Vector retVector = new Vector();  
		Vector<Object> vector = new Vector();  
		try {  
			while(result.next()) {  
				vector.clear();  
				vector.add(result.getObject(1));  
				vector.add(result.getObject(2));  
				vector.add(result.getObject(3));  
				vector.add(result.getObject(4));  
				retVector.add(vector.clone());    //注意此处不能用 retV.add(v);      
			}
		} catch (Exception e) {
		}
		  
		return retVector;  
	}

	/*
	 * 
	 * 执行带参数的增、删、改的方法
	 */
	public static int ExecuteUpdate(String sql, Object[] param) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pStmt = null;
		try {
			conn = getConn();
			pStmt = conn.prepareStatement(sql);
			if (param != null && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					pStmt.setObject(i + 1, param);
				}
			}
			count = pStmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseAll(conn, pStmt, null);
		}
		return count;
	}

	/*
	 * 
	 * 执行无参的增、删、改的方法
	 */
	public static int ExecuteUpdate(String sql) {
		return ExecuteUpdate(sql, null);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}
}
