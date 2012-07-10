package com.android.safeware.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SafePasswordSettingActivity extends Activity {

    /**
     * 日志目标
     */
    private static final String LOG_TAG = "SafePasswordSettingActivity";
    /**
     * 创建的数据文件名
     */
    private static final String LOG_FILE_NAME = "SafewareApplication";
    /**
     * 程序运行的次数
     */
    private static final String KEY_FIRST_RUN = "FIRST_RUN";
    /**
     * 用户设置的安全密码
     */
    private static final String SAFE_PASSWORD = "SAFE_PASSWORD";
    /**
     * 是否第一次启动
     */
    private static boolean firstStart = false;
    /**
     * 当前是否正在设置安全密码
     */
    private static boolean isSetting = false;
    /**
     * 使用PF来记录程序启动次数
     */
    SharedPreferences preferences;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_setting);
        /*
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        */
        init();
    }

    @Override
    /**
     * 加载菜单
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.safe_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    
    /**
     * 提交按钮触发
     * @param view
     */
    public void setSafePassword(View view){
        if(firstStart || isSetting){
            //第一次运行的情况下才保存以后设置的安全密码
            Editor editor = preferences.edit();
            String safePassword = ((EditText)findViewById(R.id.pwd1)).getText().toString();
            String safePassword2 = ((EditText)findViewById(R.id.pwd2)).getText().toString();
            TextView tv = (TextView) findViewById(R.id.view_pwd_diffrent);
            if(safePassword == null || !safePassword.equals(safePassword2)){
                tv.setVisibility(View.VISIBLE);
                Log.i(LOG_TAG, "两次输入的密码不一致");
            }else{
                editor.putString(SAFE_PASSWORD, safePassword);
                editor.commit();
                tv.setVisibility(View.GONE);
                Log.i(LOG_TAG, "安全密码已经成功设置："+safePassword);
                //密码设置成功之后，应该将标志设置false
                isSetting = false;
            }
        }else{
            //第N次运行的情况下必须要求输入相同的安全密码
            String pwd = preferences.getString(SAFE_PASSWORD, "");
            String pwd0 = ((TextView)findViewById(R.id.pwd0)).getText().toString();
            TextView tv = (TextView) findViewById(R.id.view_error_pwd0);
            if(pwd0 == null || !pwd0.equals(pwd)){
                Log.i(LOG_TAG, "安全密码错误");
                tv.setVisibility(View.VISIBLE);
            }else{
                tv.setVisibility(View.GONE);
                Log.i(LOG_TAG, "安全密码正确");
            }
        }
    }
    /**
     * 重置按钮触发
     * @param view
     */
    public void resetPassword(View view){
        if(firstStart || isSetting){
            EditText textPwd1 = (EditText) findViewById(R.id.pwd1);
            EditText testPwd2 = (EditText) findViewById(R.id.pwd2);
            textPwd1.setText("");
            testPwd2.setText("");
        }else{
            EditText textPwd0 = (EditText) findViewById(R.id.pwd0);
            textPwd0.setText("");
        }
    }
    
    /**
     * 初始化页面
     */
    public void init(){
        //读取PF中需要的数据
        preferences = getSharedPreferences(LOG_FILE_NAME, MODE_WORLD_READABLE);
        Editor editor = preferences.edit();
        int count = startCount();
        isFirstStart();
        //第一次使用
        if(firstStart){
            firstStart();
            //editor.putString(SAFE_PASSWORD, ((TextView)findViewById(R.id.pwd1)).getText().toString());
        }else{
            notFirstStart();
            String pwd = preferences.getString(SAFE_PASSWORD, "");
            Log.i(LOG_TAG, "读取存储的密码是："+pwd);
        }
        
        if(count > 10000){
            //避免计数器过大
            //count = 1;
        }
        //存入启动次数
        editor.putInt(KEY_FIRST_RUN, ++count);
        //提交修改
        editor.commit();
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
    
    /**
     * 第一次启动程序，将一些数据隐藏
     */
    public void firstStart(){
        setVisibilityForFirst(View.GONE);
    }
    /**
     * 非第一次启动程序，将一些数据隐藏
     */
    public void notFirstStart(){
        setVisibilityForSec(View.GONE);
    }
    /**
     * 判断是否第一次启动程序
     * @return
     */
    public int isFirstStart(){
        int count = startCount();
        if(count == 0){
            firstStart = true;
        }else{
            firstStart = false;
        }
        return count;
    }
    /**
     * 启动次数
     * @return
     */
    public int startCount(){
        int count = preferences.getInt(KEY_FIRST_RUN, 0);
        return count;
    }
    /**
     * 退出按钮响应
     * @param item
     */
    public void onExitMenuClick(MenuItem item){
        finish();
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
     * 菜单默认响应方法
     */
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_setting:
                isSetting = true;
                onMenuSettingClick(item);
                return true;
            case R.id.exit_safeware:
                onExitMenuClick(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
}
