package com.example.platform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.platform.db.User;
import com.mob.MobSDK;

import org.litepal.LitePal;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class StudentRegisterActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, TextWatcher {

    String APPKEY = "3133d5f814135";

    String APPSECRET = "5b01d726c5dbe8e64070c38ada4a2b3d";

    private LinearLayout registerLayout;

    private LinearLayout telephoneLayout;

    private LinearLayout passwordLayout;

    private LinearLayout smsLayout;

    private EditText telephoneEdit;

    private EditText passwordEdit;

    private EditText smsEdit;

    private ImageView telephoneClear;

    private ImageView passwordClear;

    private TextView smsGet;

    private Button btn_register;

    private CheckBox protocalCheck;

    private TextView protocalText;

    private EventHandler eventHandler;

    int i = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);
        MobSDK.submitPolicyGrantResult(true, null);
        initView();
        initEvents();
        MobSDK.init(this, APPKEY, APPSECRET);
        eventHandler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                // TODO 此处不可直接处理UI线程，处理后续操作需传到主线程中操作
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
                Log.e("AAAAAAAAAAAAAAAAAAAAA","smsactivity === > START");
            }
        };
        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
    }

    private void initView(){
        registerLayout = (LinearLayout)findViewById(R.id.register_register_layout);
        telephoneLayout = (LinearLayout)findViewById(R.id.register_telephone_layout);
        passwordLayout = (LinearLayout)findViewById(R.id.register_password_layout);
        smsLayout = (LinearLayout)findViewById(R.id.register_sms_layout);
        telephoneEdit = (EditText)findViewById(R.id.register_telephone_edit);
        passwordEdit = (EditText)findViewById(R.id.register_password_edit);
        smsEdit = (EditText)findViewById(R.id.register_sms_edit);
        telephoneClear = (ImageView)findViewById(R.id.register_telephone_clear);
        passwordClear = (ImageView)findViewById(R.id.register_password_clear);
        smsGet = (TextView)findViewById(R.id.register_sms_get_text);
        btn_register = (Button)findViewById(R.id.register_register_button);
        protocalCheck = (CheckBox)findViewById(R.id.register_protocal_check);
        protocalText = (TextView)findViewById(R.id.register_protocal_text);
    }

    private void initEvents(){
        registerLayout.setOnClickListener(this);
        telephoneLayout.setOnClickListener(this);
        passwordLayout.setOnClickListener(this);
        smsLayout.setOnClickListener(this);
        telephoneEdit.setOnClickListener(this);
        passwordEdit.setOnClickListener(this);
        smsEdit.setOnClickListener(this);
        telephoneClear.setOnClickListener(this);
        passwordClear.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        smsGet.setOnClickListener(this);
        protocalText.setOnClickListener(this);
        telephoneEdit.setOnFocusChangeListener(this);
        passwordEdit.setOnFocusChangeListener(this);
        smsEdit.setOnFocusChangeListener(this);
        telephoneEdit.addTextChangedListener(this);
        passwordEdit.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.register_telephone_edit:
                passwordEdit.clearFocus();
                telephoneEdit.setFocusableInTouchMode(true);
                telephoneEdit.requestFocus();
                break;
            case R.id.register_password_edit:
                telephoneEdit.clearFocus();
                passwordEdit.setFocusableInTouchMode(true);
                passwordEdit.requestFocus();
                break;
            case R.id.register_telephone_clear:
                telephoneEdit.setText(null);
                break;
            case R.id.register_password_clear:
                passwordEdit.setText(null);
                break;
            case R.id.register_sms_get_text:
                getSMS();
                break;
            case R.id.register_register_button:
                register();
                break;
            default:
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus){
        int id = v.getId();
        if (id == R.id.register_telephone_edit) {
            if (hasFocus) {
                telephoneLayout.setActivated(true);
                passwordLayout.setActivated(false);
            }else {
                String inputStr = telephoneEdit.getText().toString().trim();
                if (isTelephoneValid(inputStr)){
                    telephoneEdit.setError(null);
                }else {
                    telephoneEdit.setError("手机号格式不正确！");
                    btn_register.setClickable(false);
                }
            }
        } else if (id == R.id.register_password_edit) {
            if (hasFocus) {
                passwordLayout.setActivated(true);
                telephoneLayout.setActivated(false);
            }else {
                String inputStr = passwordEdit.getText().toString().trim();
                if (isPasswordValid(inputStr)){
                    passwordEdit.setError(null);
                }else {
                    passwordEdit.setError("密码不能少于6位！");
                    btn_register.setClickable(false);
                }
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String telephone = telephoneEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        String smsCode = smsEdit.getText().toString().trim();

        //是否显示清除按钮
        if (telephone.length() > 0) {
            telephoneClear.setVisibility(View.VISIBLE);
        } else {
            telephoneClear.setVisibility(View.INVISIBLE);
        }
        if (password.length() > 0) {
            passwordClear.setVisibility(View.VISIBLE);
        } else {
            passwordClear.setVisibility(View.INVISIBLE);
        }

        //注册按钮是否可用
        if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(telephone) && !TextUtils.isEmpty(smsCode)) {
            btn_register.setBackgroundResource(R.drawable.login_submit_back);
            btn_register.setTextColor(getResources().getColor(R.color.white, null));
            btn_register.setClickable(true);
        } else {
//            btn_register.setBackgroundResource(R.drawable.login_submit_lock_back);
//            btn_register.setTextColor(getResources().getColor(R.color.account_lock_font_color, null));
//            btn_register.setClickable(false);
        }
    }

    // 校验账号不能为空且必须是中国大陆手机号（宽松模式匹配）
    private boolean isTelephoneValid(String telephone) {
        if (telephone == null) {
            return false;
        }
        // 首位为1, 第二位为3-9, 剩下九位为 0-9, 共11位数字
        String pattern = "^[1]([3-9])[0-9]{9}$";
        Pattern pt = Pattern.compile(pattern);
        Matcher matcher = pt.matcher(telephone);
        return matcher.matches();
    }

    // 校验密码不少于6位
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private void getSMS(){
        String phoneNumber = telephoneEdit.getText().toString();
        // 1. 通过规则判断手机号
        if (!isTelephoneValid(phoneNumber)) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        } // 2. 通过sdk发送短信验证
        SMSSDK.getVerificationCode("86", phoneNumber);

        // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
        smsGet.setClickable(false);
        smsGet.setText("重新发送(" + i + ")");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; i > 0; i--) {
                    handler.sendEmptyMessage(-9);
                    if (i <= 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(-8);
            }
        }).start();
//        sendCode(getApplicationContext());
    }

    private void register(){
        int flag = 0;
        String phoneNumber = telephoneEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String smsCode = smsEdit.getText().toString();
        List<User> users = LitePal.findAll(User.class);
        for (User user : users){
            if (phoneNumber.equals(user.getUsername())){
                //已注册
                flag = 1;
                break;
            }
        }
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(smsCode)){
            //帐号或密码为空
            flag = 2;
        }
        if (flag == 0){
            User user = new User();
            user.setUsername(phoneNumber);
            user.setPassword(password);
            user.save();
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(StudentRegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else if (flag == 1){
            Toast.makeText(this, "您的号码已注册，请直接登录", Toast.LENGTH_SHORT).show();
            finish();
        }else if (flag == 2){
            Toast.makeText(this, "请把信息填写完整哦", Toast.LENGTH_SHORT).show();
        }
    }

//    Handler handler = new Handler();

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                smsGet.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                smsGet.setText("获取验证码");
                smsGet.setClickable(true);
                i = 30;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        Toast.makeText(getApplicationContext(), "提交验证码成功",
                                Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(SmsActivity.this,
//                                LoginActivity.class);
//                        startActivity(intent);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "正在获取验证码",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }
            }
        }
    };

//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            int event = msg.arg1;
//            int result = msg.arg2;
//            Object data = msg.obj;
//            if (result == SMSSDK.RESULT_COMPLETE) {
//                //回调完成
//                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//                    //提交验证码成功
//                    Toast.makeText(getApplicationContext(),"验证成功",Toast.LENGTH_SHORT).show();
//                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
//                    //获取验证码成功
//                    Toast.makeText(getApplicationContext(),"验证码发送成功",Toast.LENGTH_SHORT).show();
//                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
//                    //返回支持发送验证码的国家列表
//
//                }
//            }else{
//                Log.d("TAG", "123456123456");
//                ((Throwable)data).printStackTrace();
//            }
//        }
//
//    };

    public void sendCode(Context context) {
        RegisterPage page = new RegisterPage();
        //如果使用我们的ui，没有申请模板编号的情况下需传null
        page.setTempCode(null);
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理成功的结果
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    // 国家代码，如“86”
                    String country = (String) phoneMap.get("country");
                    // 手机号码，如“13800138000”
                    String phone = (String) phoneMap.get("phone");
                    // TODO 利用国家代码和手机号码进行后续的操作
                } else{
                    // TODO 处理错误的结果
                }
            }
        });
        page.show(context);
    }

    // 使用完EventHandler需注销，否则可能出现内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
