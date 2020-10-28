package com.example.platform;



public class Register {
    public static boolean addStudent(String sid, String pwd){
        return Student.insert(sid,sid,pwd);  //如果插入成功，返回true;
    }
    public static boolean addTeacher(String tid,String pwd) {
        return Teacher.insert(tid,tid,pwd);
    }
}