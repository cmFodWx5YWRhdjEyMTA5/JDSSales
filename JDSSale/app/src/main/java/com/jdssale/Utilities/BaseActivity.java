package com.jdssale.Utilities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.jdssale.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by dikhong on 06-07-2018.
 */

public class BaseActivity extends AppCompatActivity {

    public ProgressDialog progressDialog;
    public AlertDialog alertDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        progressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        alertDialog = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle)
                .setTitle(getString(R.string.app_name))
                .setIcon(R.drawable.logo)
                .setMessage(getString(R.string.host_internet))

                .setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!isInternetAvailable()) {
                            showErrorDialog();
                        }
                    }
                })
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        setStatusBar();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow(); // in Activity's onCreate() for instance
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Preferences.getInstance().getGroupId()==null){}
            else {
                if (Preferences.getInstance().getGroupId().equalsIgnoreCase("5")) {
                    window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorStatus));
                } else {
                    window.setStatusBarColor(ContextCompat.getColor(this, R.color.navy));
                }
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showProgressbar(String title, String message) {

        if (!progressDialog.isShowing())
            progressDialog.setMessage(message);
        progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideProgressBar() {
        if (progressDialog != null) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isInternetAvailable()) {
            showErrorDialog();
        }
    }

    public void showErrorDialog() {
        if (!isFinishing()) {
            alertDialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}
