package com.example.platform;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.platform.db.User;

import org.litepal.LitePal;

import java.util.List;

public class  RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText nameEdit;
    private EditText passwordEdit;
    private EditText confirmText;
    private Button btn_register;

//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            if(msg.what==1){
//                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
//            }else {
//                Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
//            }
//        }
//    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        initEvents();
    }

    private void initViews(){
        nameEdit = (EditText)findViewById(R.id.nameEdit);
        passwordEdit = (EditText)findViewById(R.id.passwordEdit);
        confirmText = (EditText)findViewById(R.id.confirmEdit);
        btn_register = (Button)findViewById(R.id.btn_register);
    }

    private void initEvents(){
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_register:
                register();
                break;
            default:
                break;
        }
    }

    private void register(){
        int flag = 0;
        String username = nameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String confirm = confirmText.getText().toString();
        List<User> users = LitePal.findAll(User.class);
        for (User user : users){
            if (username.equals(user.getUsername())){
                //已注册
                flag = 1;
                break;
            }
        }
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(username) || TextUtils.isEmpty(confirm)){
            //帐号或密码为空
            flag = 2;
        }
        if (!password.equals(confirm)){
            //两次密码输入不一致
            flag = 3;
        }
        if (flag == 0){
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.save();
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else if (flag == 1){
            Toast.makeText(this, "您的号码已注册，请直接登录", Toast.LENGTH_SHORT).show();
            finish();
        }else if (flag == 2){
            Toast.makeText(this, "请把信息填写完整哦", Toast.LENGTH_SHORT).show();
        }else if (flag == 3){
            Toast.makeText(this, "两次密码输入不一致，请再次确认密码", Toast.LENGTH_SHORT).show();
        }
    }
}
