package com.example.platform.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;

import com.example.platform.LoginActivity;
import com.example.platform.R;
import com.example.platform.constants.SPkey;
import com.example.platform.utils.SPUtils;


/**
 * @ 创建时间: 2017/6/13 on 15:43.
 * @ 描述：闪屏启动页面
 * @ 作者: 郑卫超 QQ: 2318723605
 */

public class SplashActivity extends BaseActivity {

    @Override
    int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    void initData() {
        //延时2s，跳转。
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String username = (String) SPUtils.get(SplashActivity.this, SPkey.UserName, "");
                if (!TextUtils.isEmpty(username)) {
                    //直接跳转主页面
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //跳转到登录页面
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }

}
