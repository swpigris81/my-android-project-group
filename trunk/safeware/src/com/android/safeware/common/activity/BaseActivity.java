package com.android.safeware.common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.safeware.activity.R;

public class BaseActivity extends Activity {
    /**
     * 用以标志跳转页面(密码设置页面)
     */
    public static final String SETTING_PASSWORD = "SETTING_PASSWORD";
    /**
     * 日志目标
     */
    protected static final String LOG_TAG = "SafewareApplication";
    /**
     * 创建的数据文件名
     */
    protected static final String LOG_FILE_NAME = "SafewareApplication";
    /**
     * 程序运行的次数
     */
    protected static final String KEY_FIRST_RUN = "FIRST_RUN";
    /**
     * 用户设置的安全密码
     */
    protected static final String SAFE_PASSWORD = "SAFE_PASSWORD";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    /**
     * 菜单默认响应方法
     */
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_setting:
                //isSetting = true;
                onMenuSettingClick(item);
                return true;
            case R.id.exit_safeware:
                onExitMenuClick(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * 退出按钮响应
     * @param item
     */
    public void onExitMenuClick(MenuItem item){
        finish();
        moveTaskToBack(true);
    }
    /**
     * 设置密码按钮响应
     * @param item
     */
    public void onMenuSettingClick(MenuItem item){
        setVisibilityForFirst(View.GONE);
        setVisibilityForSec(View.VISIBLE);
    }
    
    /**
     * 设置第一部分的控件的可视状态
     * @param visibility
     */
    public void setVisibilityForFirst(int visibility){
        TextView viewPwd0 = (TextView) findViewById(R.id.view_pwd0);
        EditText textPwd0 = (EditText) findViewById(R.id.pwd0);
        //TextView viewErrorPwd0 = (TextView) findViewById(R.id.view_error_pwd0);
        
        viewPwd0.setVisibility(visibility);
        textPwd0.setVisibility(visibility);
        //viewErrorPwd0.setVisibility(visibility);
    }
    
    /**
     * 设置第二部分的控件的可视状态
     * @param visibility
     */
    public void setVisibilityForSec(int visibility){
        TextView viewFirstPsw = (TextView) findViewById(R.id.view_first_psw);
        TextView viewSecPsw = (TextView) findViewById(R.id.view_sec_psw);
        EditText textPwd1 = (EditText) findViewById(R.id.pwd1);
        EditText testPwd2 = (EditText) findViewById(R.id.pwd2);
        //TextView viewPwdDiffrent = (TextView) findViewById(R.id.view_pwd_diffrent);
        //Button resetButton = (Button) findViewById(R.id.reset);
        
        viewFirstPsw.setVisibility(visibility);
        viewSecPsw.setVisibility(visibility);
        textPwd1.setVisibility(visibility);
        testPwd2.setVisibility(visibility);
        //viewPwdDiffrent.setVisibility(visibility);
        //resetButton.setVisibility(View.GONE);
    }
}
