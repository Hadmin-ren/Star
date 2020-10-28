package com.example.platform;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student{
    private static Connection connection=DBConnection.getDBCourseConnection();
    public static String getPwdById(String sid) {
        String sql="select pwd from student where sid=?";
        PreparedStatement preparedStatement= null;
        String pwd=null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,sid);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                pwd=resultSet.getString("pwd");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pwd;
    }

    public static String getNameById(String sid){
        String sql="select sname from student where sid=?";
        PreparedStatement preparedStatement= null;
        String name=null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,sid);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                name=resultSet.getString("sname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static boolean hasSid(String sid){
        String sql="select sid from student where sid=?";
        PreparedStatement preparedStatement= null;
        boolean result=false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,sid);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                result=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean insert(String sid, String sname, String pwd){
        String sql="insert into student value(?,?,?)";
        PreparedStatement preparedStatement= null;
        boolean result=false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,sid);
            preparedStatement.setString(2,sname);
            preparedStatement.setString(3,pwd);
            int i=preparedStatement.executeUpdate();
            if(i==1){
                result=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
