package com.jdssale.Utilities;

import android.support.multidex.MultiDexApplication;

import com.jdssale.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by dikhong on 06-07-2018.
 */

public class JDSSale extends MultiDexApplication {

    private static JDSSale instance;
    public String SERVER_ERROR = "Unable to connect to server, please try again after sometime";


    @Override

    public void onCreate() {
        super.onCreate();
        instance = this;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public static JDSSale getInstance() {
        return instance;
    }
}