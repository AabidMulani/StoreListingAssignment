package com.coupondunia.assignment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Aabid Mulani
 * {04-10-2015}
 */
public class BaseActivity extends AppCompatActivity {

    public Activity activity;
    private BaseApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        application = ((BaseApplication) getApplication());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public BaseApplication getBaseApplication() {
        return application;
    }

}
