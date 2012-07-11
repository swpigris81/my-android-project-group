package com.android.safeware.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.safeware.common.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * 设置密码按钮响应
     * @param item
     */
    public void onMenuSettingClick(MenuItem item){
        startActivity(new Intent (this, SafePasswordSettingActivity.class) );
        super.onMenuSettingClick(item);
    }
}
