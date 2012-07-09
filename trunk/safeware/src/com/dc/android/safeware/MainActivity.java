package com.dc.android.safeware;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements ActionBar.TabListener {

    private static final String KEY_FIRST_RUN = "FIRST_RUN";
    private static final String SAFE_PASSWORD = "SAFE_PASSWORD";

    /**
     * 使用PF来记录程序启动次数
     */
    SharedPreferences preferences;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        */
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        
    }
    /**
     * 提交按钮触发
     * @param view
     */
    public void setSafePassword(View view){
        if(isFirstStart()){
            //第一次运行的情况下才保存以后设置的安全密码
            Editor editor = preferences.edit();
            editor.putString(SAFE_PASSWORD, ((TextView)findViewById(R.id.pwd1)).getText().toString());
            editor.commit();
        }else{
            //第N次运行的情况下必须要求输入相同的安全密码
            String pwd = ((TextView)findViewById(R.id.pwd1)).getText().toString();
            String pwd0 = ((TextView)findViewById(R.id.pwd0)).getText().toString();
            if(pwd0 == null || !pwd0.equals(pwd)){
                System.out.println("安全密码错误");
            }
        }
    }
    /**
     * 重置按钮触发
     * @param view
     */
    public void resetPassword(View view){
        
    }
    
    /**
     * 初始化页面
     */
    public void init(){
        Editor editor = preferences.edit();
        int count = startCount();
        //第一次使用
        if(isFirstStart()){
            firstStart();
            //editor.putString(SAFE_PASSWORD, ((TextView)findViewById(R.id.pwd1)).getText().toString());
        }else{
            notFirstStart();
            String pwd = preferences.getString(SAFE_PASSWORD, "");
            System.out.println(pwd);
        }
        //存入启动次数
        editor.putInt(KEY_FIRST_RUN, ++count);
        //提交修改
        editor.commit();
    }
    /**
     * 第一次启动程序，将一些数据隐藏
     */
    public void firstStart(){
        TextView viewPwd0 = (TextView) findViewById(R.id.view_pwd0);
        EditText textPwd0 = (EditText) findViewById(R.id.pwd0);
        TextView viewErrorPwd0 = (TextView) findViewById(R.id.view_error_pwd0);
        //隐藏控件，并且不占用空间
        viewPwd0.setVisibility(View.GONE);
        textPwd0.setVisibility(View.GONE);
        viewErrorPwd0.setVisibility(View.GONE);
        
        
    }
    /**
     * 非第一次启动程序，将一些数据隐藏
     */
    public void notFirstStart(){
        TextView viewFirstPsw = (TextView) findViewById(R.id.view_first_psw);
        EditText textPwd1 = (EditText) findViewById(R.id.pwd1);
        EditText testPwd2 = (EditText) findViewById(R.id.pwd2);
        TextView viewPwdDiffrent = (TextView) findViewById(R.id.view_pwd_diffrent);
        Button resetButton = (Button) findViewById(R.id.reset);
        
        viewFirstPsw.setVisibility(View.GONE);
        textPwd1.setVisibility(View.GONE);
        testPwd2.setVisibility(View.GONE);
        viewPwdDiffrent.setVisibility(View.GONE);
        resetButton.setVisibility(View.GONE);
    }
    /**
     * 判断是否第一次启动程序
     * @return
     */
    public boolean isFirstStart(){
        boolean bool = false;
        int count = startCount();
        if(count == 0){
            bool = true;
        }
        return bool;
    }
    /**
     * 启动次数
     * @return
     */
    public int startCount(){
        //读取PF中需要的数据
        preferences = getSharedPreferences(KEY_FIRST_RUN, MODE_WORLD_READABLE);
        int count = preferences.getInt(KEY_FIRST_RUN, 0);
        return count;
    }
}
