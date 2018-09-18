package com.jdssale;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.jdssale.Utilities.Preferences;

public class SplashActivity extends AppCompatActivity {
    int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setStatusBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (com.jdssale.Utilities.Preferences.getInstance().isLogIn()) {
                    if (Preferences.getInstance().getGroupId().equalsIgnoreCase("5")) {
                        startSale();
                    }
                    else if (Preferences.getInstance().getGroupId().equalsIgnoreCase("6"))
                    {
                        startDriver();
                    }

                } else {
                    startLogin();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void startLogin() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        this.finish();
    }

    private void startSale() {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        this.finish();
    }

    private void startDriver() {
        Intent intent = new Intent(getApplicationContext(), Driver1Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        this.finish();
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}