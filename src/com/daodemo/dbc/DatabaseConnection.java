package com.daodemo.dbc;

import java.sql.*;

public class DatabaseConnection {
    private Connection con = null;

    // mysql
    // private static final String DRIVER = "com.mysql.jdbc.Driver";
    // private static final String USER = "root";
    // private static final String URL = "jdbc:mysql://localhost:3306/mldn";
    // private static final String PASS = "12345";
    // sqlite
    private static final String dbDriver = "org.sqlite.JDBC";
    private static final String dbUrl = "jdbc:sqlite:";
    private static final String DB_NAME = "daoDemoDB.db3"; // "c:/testDB.db3";
                                                           // //数据库名称
    private static final String dbUser = "";
    private static final String dbPwd = "";

    public DatabaseConnection() throws Exception {
        // Class.forName(DRIVER);
        // con = DriverManager.getConnection(URL, USER, PASS);
        Class.forName(dbDriver);
        con = DriverManager.getConnection(dbUrl + DB_NAME, dbUser, dbPwd);
    }

    public Connection getConnection() throws Exception {
        return con;
    }

    public void close() throws Exception {
        if (con != null) {
            con.close();
        }
    }
}