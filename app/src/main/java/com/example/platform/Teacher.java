package com.example.platform;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Teacher{
    private static Connection connection=DBConnection.getDBCourseConnection();
    public static String getPwdById(String tid) {
        String sql="select pwd from teacher where tid=?";
        PreparedStatement preparedStatement= null;
        String pwd=null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,tid);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                pwd=resultSet.getString("pwd");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pwd;
    }

    public static String getNameById(String tid){
        String sql="select tname from teacher where tid=?";
        PreparedStatement preparedStatement= null;
        String name=null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,tid);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                name=resultSet.getString("tname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static boolean hasTid(String tid){
        String sql="select tid from teacher where tid=?";
        PreparedStatement preparedStatement= null;
        boolean result=false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,tid);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                result=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean insert(String tid,String tname,String pwd){
        String sql="insert into teacher value(?,?,?)";
        PreparedStatement preparedStatement= null;
        boolean result=false;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,tid);
            preparedStatement.setString(2,tname);
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
