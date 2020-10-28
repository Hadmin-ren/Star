package com.example.platform;

public class Course {

    private String name;

    private String teacher;

    private String introduction;

    private int imageId;

    public Course(String name, String teacher, String introduction, int imageId){
        this.name = name;
        this.teacher = teacher;
        this.introduction = introduction;
        this.imageId = imageId;
    }

    public String getName(){
        return name;
    }

    public String getTeacher(){
        return teacher;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getImageId(){
        return imageId;
    }
}
