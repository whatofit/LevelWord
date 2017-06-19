package com.annotationdao.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 提供获取数据库连接、释放资源的接口
 */
public class JdbcDaoHelper {

    private static final String dbDriver = "org.sqlite.JDBC";

    /**
     * 数据库用户名
     */
    private static final String USER = "test";

    /**
     * 数据库密码
     */
    private static final String PASSWORD = "";

    /**
     * 连接数据库的地址
     */
    private static final String URL = "jdbc:sqlite:daotest.db";

    private static Connection conn;

    /**
     * 获得一个数据库连接对象
     * 
     * @return java.sql.Connection实例
     */
    public static Connection getConnection() {
        try {
            if (conn == null) {
                Class.forName(dbDriver);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
            } else {
                return conn;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 释放数据库资源
     */
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
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}