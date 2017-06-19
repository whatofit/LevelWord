package com.genericsdao.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//
public class DBHelper {

    // sqlite
    private static final String mdbDriver = "org.sqlite.JDBC";
    private static final String mdbUrl = "jdbc:sqlite:";
    // 数据库名称// "c:/testDB.db3";
    private static final String mdbName = "GenericsDaoDB.db3";
    private static final String mdbUser = "";
    private static final String mdbPwd = "";

    // mysql
    // private static final String USER = "root";
    // private static final String PASSWORD = "";
    // private static final String dbDriver = "com.mysql.jdbc.Driver";
    // private static final String URL =
    // "jdbc:mysql://localhost:3306/usermanager";

    private static Connection mConn;

    // 获取数据库连接对象
    public static Connection getConnection(String dbName) {
        if (mConn == null) {
            try {
                Class.forName(mdbDriver);
                String connDbName = mdbName;
                if (dbName != null) {
                    connDbName = dbName;
                }
                mConn = DriverManager.getConnection(mdbUrl + connDbName,
                        mdbUser, mdbPwd);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return mConn;
        }
        return mConn;
    }

    public static Connection getConnection() {
        return getConnection(null);
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
            if (mConn != null) {
                mConn.close();
                mConn = null;
            }
        } catch (Exception e) {

            // TODO: handle exception
        }
    }

}
