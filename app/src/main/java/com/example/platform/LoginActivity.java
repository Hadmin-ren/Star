package com.example.platform;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.platform.db.User;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.List;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText nameEdit;
    private EditText passwordEdit;
    private MyView loginView;
    private TextView registerText;
    private TextView teacherRegister;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==1){
                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(LoginActivity.this,MainActivity.class);
                startActivity(intent);

            }else {
                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nameEdit = (EditText)findViewById(R.id.nameEdit);
        passwordEdit = (EditText)findViewById(R.id.pwdEdit);
        loginView = (MyView) findViewById(R.id.btn_login);
        registerText = (TextView)findViewById(R.id.edit_register);
        teacherRegister = (TextView)findViewById(R.id.teacher_register);
        loginView.setOnClickListener(this);
        registerText.setOnClickListener(this);
        teacherRegister.setOnClickListener(this);
        loginView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.login);
                    loginView.replacePic(bitmap);
                }else {
                    Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.login_highlight);
                    loginView.replacePic(bitmap);
                }
                return false;
            }
        });
        View.OnFocusChangeListener focusChangeListener=new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText=(EditText)v;
                if(editText.hasFocus()){
                    String hint=editText.getHint().toString();
                    editText.setTag(hint);
                    editText.setHint("");
                }else{
                    editText.setHint(editText.getTag().toString());
                }
            }
        };
        nameEdit.setOnFocusChangeListener(focusChangeListener);
        passwordEdit.setOnFocusChangeListener(focusChangeListener);
        Connector.getDatabase();
    }

    @Override
    public  void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.edit_register:
                register();
                break;
            case R.id.teacher_register:
                Intent intent = new Intent(LoginActivity.this, TeacherRegisterActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }

    private void login(){
        int flag = 0;
        String username = nameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        List<User> users = LitePal.findAll(User.class);
        for (User user : users){
            if (username.equals(user.getUsername())){
                if (password.equals(user.getPassword())){
                    //帐号密码均正确
                    flag = 1;
                    break;
                }else {
                    //密码错误
                    flag = 2;
                    break;
                }
            }
        }
        if (flag == 0) {
            Toast.makeText(this, "您还没有注册哦，请先注册", Toast.LENGTH_SHORT).show();
            register();
        }else if (flag == 1){
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else if(flag == 2){
            Toast.makeText(this, "密码错误，请重新输入哦", Toast.LENGTH_SHORT).show();
            passwordEdit.setText("");
        }
    }

    public void register(){
        Intent intent = new Intent(LoginActivity.this, StudentRegisterActivity.class);
        startActivity(intent);
    }
}
