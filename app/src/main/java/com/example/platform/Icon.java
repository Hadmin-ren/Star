package com.example.platform;

public class Icon {
    private String IconName;
    private int IconId;
    public Icon(String name, int Id){
        this.IconName=name;
        this.IconId=Id;
    }

    public String getIconName(){
        return IconName;
    }

    public int getIconImageId(){
        return IconId;
    }
}
