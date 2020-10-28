package com.example.platform;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends AppCompatActivity {

    private MainFragment mainFragment=new MainFragment();
    private StudyFragment studyFragment = new StudyFragment();
    private SelfFragment selfFragment = new SelfFragment();
    private long exitTime;  //按下返回键的时间

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.display_fragment, mainFragment).commit();
                    return true;
                case R.id.navigation_dashboard:
                    transaction.replace(R.id.display_fragment, studyFragment).commit();
                    return true;
                case R.id.navigation_notifications:
                    transaction.replace(R.id.display_fragment, selfFragment).commit();
                    return true;
            }
            return false;
        }
    };

    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.display_fragment, mainFragment).commit();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event){
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0){
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast toast = Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                LinearLayout linearLayout = (LinearLayout)toast.getView();
                TextView textView = (TextView)linearLayout.getChildAt(0);
//                textView.setTextSize(20);
                toast.show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}
