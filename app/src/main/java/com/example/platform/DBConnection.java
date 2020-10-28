package com.example.platform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 此类采用了懒汉单例模式，只会产生出一个connection
 */
public class DBConnection {
    private static Connection connection=null;
    public static Connection getDBCourseConnection(){
        String url="jdbc:mysql://152.136.148.42:3306/db_course?useSSL=false";
//        String driver = "com.mysql.cj.jdbc.Driver";//com.mysql.jdbc.Driver
        String driver = "com.mysql.jdbc.Driver";//com.mysql.jdbc.Driver
        while (connection==null){
            try {
                System.out.println("开始获取和数据库的连接...");
                Class.forName(driver);
                System.out.println("加载驱动成功");
                connection= DriverManager.getConnection(url,"root","Asdf123***");
                System.out.println("数据库连接成功");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.out.println("数据库连接失败");
            }
        }
        return connection;
    }
}
