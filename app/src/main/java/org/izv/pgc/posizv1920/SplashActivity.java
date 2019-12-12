package org.izv.pgc.posizv1920;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    public static final String TAG = "xyz";

    public static SplashActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initEvents();
        mContext = this;
    }

    private void initEvents() {
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },4000);

    }

    private static SplashActivity getmContext(){
        return mContext;
    }
}
