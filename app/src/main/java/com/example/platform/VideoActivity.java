package com.example.platform;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class  VideoActivity extends AppCompatActivity {

    private int videoName;

    private SeekBar progressBar;
    private VideoView videoPlayer;
    private Button playButton;
    //    private TextView tip;
    private PlayButtonView playButtonView;
    private EnlargeButtonView enlargeButtonView;
    private LinearLayout videoController;

    private Thread videoThread=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Intent intent=getIntent();
        videoName=intent.getIntExtra("videoName",0);
        System.out.println(videoName);

        progressBar=findViewById(R.id.progressBar);
        videoPlayer=findViewById(R.id.videoPlayer);
        playButton=findViewById(R.id.playButton);
        playButtonView=findViewById(R.id.playButtonView);
        enlargeButtonView=findViewById(R.id.enLargeButton);
        videoController=findViewById(R.id.videoController);
        String uri=null;
        switch (videoName){
            case 0:uri=("android.resource://" + getPackageName() + "/" + R.raw.video0);break;
            case 1:uri=("android.resource://" + getPackageName() + "/" + R.raw.video1);break;
            default:uri=("android.resource://" + getPackageName() + "/" + R.raw.video0);
        }
        System.out.println(uri);
        videoPlayer.setVideoURI(Uri.parse(uri));
        videoPlayer.requestFocus();
        SeekBar.OnSeekBarChangeListener seekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //开始拖动进度条时触发

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //手指抬起时触发
                videoPlayer.seekTo(seekBar.getProgress());
            }
        };
        progressBar.setOnSeekBarChangeListener(seekBarChangeListener);
        videoPlayer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(videoController.getVisibility()==View.VISIBLE){
                    videoController.setVisibility(View.INVISIBLE);
                }else {
                    videoController.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void playClick(View view){
//        tip.setVisibility(View.GONE);
        final int duration=videoPlayer.getDuration();
        progressBar.setMax(duration);
        progressBar.setVisibility(View.VISIBLE);
        enlargeButtonView.setVisibility(View.VISIBLE);

        if(videoPlayer.isPlaying()){
            videoPlayer.pause();
        }else {
            videoPlayer.start();
        }
        if(videoThread==null){
            videoThread=new Thread(){
                @Override
                public void run() {
                    int progress=0;
                    while (true){
                        progress=videoPlayer.getCurrentPosition();
                        progressBar.setProgress(progress);
                        if(progress>duration-200){
                            videoThread=null;
                            break;
                        }
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            videoThread.start();
        }
    }

    public void enLargeClick(View view){
        if(this.getRequestedOrientation()==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            enlargeButtonView.enlarged=!enlargeButtonView.enlarged;
        }else{
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            tip.setVisibility(View.GONE);
            enlargeButtonView.enlarged=!enlargeButtonView.enlarged;
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //如果是竖屏

            //恢复标题栏
            getSupportActionBar().show();

            //恢复顶部状态栏
            //获取WindowManager.LayoutParams属性对象
            WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
            //非全屏
            layoutParams.flags&=(~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //设置window属性为非全屏
            getWindow().setAttributes(layoutParams);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {  //如果是横屏模式
            //如果是横屏

            //隐藏标题栏
            getSupportActionBar().hide();

            //隐藏顶部状态栏
            //获取WindowManager.LayoutParams属性对象
            WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
            //LayoutParams.FLAG_FULLSCREEN表示 全屏
            layoutParams.flags|=WindowManager.LayoutParams.FLAG_FULLSCREEN;
            //设置window的属性
            getWindow().setAttributes(layoutParams);

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                enlargeButtonView.enlarged=!enlargeButtonView.enlarged;
            }else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
