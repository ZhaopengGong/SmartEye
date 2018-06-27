package cn.edu.bjut.sipl.smarteye.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.bjut.sipl.smarteye.R;

/**
 * 闪屏页
 * Created by SIPL-gzp on 2018/5/24.
 */

public class SplashActivity extends Activity {


    @BindView(R.id.eye_logo_iv)
    ImageView eyeLogoImageView;               //logo图标
    @BindView(R.id.group_info_iv)
    ImageView groupInfoImageView;             //小组信息

    private static final int GO_HOME = 1000;

    // Handler:跳转到不同界面
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI(){
        // 显示加载也logo
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        eyeLogoImageView.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.SlideInUp)
                                .duration(700)
                                .playOn(eyeLogoImageView);
                    }
                });
            }
        }, 500);
        // 显示小组信息
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        groupInfoImageView.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.FadeInUp)
                                .duration(1000)
                                .playOn(groupInfoImageView);
                    }
                });
            }
        }, 1000);
        // 闪屏页停留3.5秒
        mHandler.sendEmptyMessageDelayed(GO_HOME, 3500);
    }
}
