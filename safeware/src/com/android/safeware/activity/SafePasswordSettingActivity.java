package com.android.safeware.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.safeware.common.activity.BaseActivity;
import com.android.safeware.util.Utils;

public class SafePasswordSettingActivity extends BaseActivity {

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
    
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        //if(SETTING_PASSWORD.equals(r))
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
            if(safePassword == null || "".equals(safePassword)){
                tv.setText("安全密码不能为空");
                tv.setVisibility(View.VISIBLE);
                Log.i(LOG_TAG, "密码不能为空");
            }else if(!safePassword.equals(safePassword2)){
                tv.setText("两次输入的密码不一致");
                tv.setVisibility(View.VISIBLE);
                Log.i(LOG_TAG, "两次输入的密码不一致");
            }else{
                editor.putString(SAFE_PASSWORD, safePassword);
                editor.commit();
                tv.setVisibility(View.GONE);
                Log.i(LOG_TAG, "安全密码已经成功设置："+safePassword);
                //密码设置成功之后，应该将标志设置false
                isSetting = false;
                Utils.openOptionsDialog(SafePasswordSettingActivity.this, MainActivity.class, "提示", -1, "您的安全密码已经成功设置，请注意保存！", "确定", null);
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
                //密码设置成功之后，应该将标志设置false
                isSetting = false;
                Utils.openOptionsDialog(SafePasswordSettingActivity.this, MainActivity.class, "提示", -1, "安全密码正确！", "确定", null);
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
}
