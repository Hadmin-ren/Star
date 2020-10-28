package com.example.platform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.smssdk.ui.companent.CircleImageView;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{

    private CircleImageView plusImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        initEvents();

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.plus_image:
                Toast.makeText(this, "添加成功！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void initView(){
        plusImage = (CircleImageView)findViewById(R.id.plus_image);
    }

    private void initEvents(){
        plusImage.setOnClickListener(this);
    }
}
