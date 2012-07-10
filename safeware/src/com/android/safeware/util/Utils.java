package com.android.safeware.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class Utils {
    /**
     * 显示对话框
     * @param context
     * @param title 标题
     * @param titleId
     * @param message 消息
     * @param yesBtn YES按钮
     * @param noBtn NO按钮
     */
    public static void openOptionsDialog(final Context context, final Class clazz, String title, int titleId, String message, String yesBtn, String noBtn){
        AlertDialog.Builder adb=new AlertDialog.Builder(context);
        AlertDialog ad = adb.create();
        //按对话框以外的地方不起作用。按返回键还起作用
        ad.setCanceledOnTouchOutside(false);
        //按对话框以外的地方不起作用。按返回键也不起作用
        //setCanceleable(false);
        //设置对话框标题
        if(title != null && !"".equals(title)){
        	ad.setTitle(title);
        }else{
        	ad.setTitle(titleId);
        }
        //给对话框设置图标
        //ad.setIcon(null);
        //设置对话框的提示信息
        if(message != null && !"".equals(message)){
        	ad.setMessage(message);
        }else{
        	//ad.setMessage(msgId);
        }
        //添加YES按钮
        if(yesBtn != null && !"".equals(yesBtn)){
        	ad.setButton(DialogInterface.BUTTON_POSITIVE, yesBtn, new DialogInterface.OnClickListener(){
    
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	context.startActivity(new Intent (context, clazz) );
                }
            });
        	/*
            adb.setPositiveButton(yesBtn, new DialogInterface.OnClickListener(){
    
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	context.startActivity(new Intent (context, clazz) );
                }
            });
            */
        }
        //添加NO按钮
        if(noBtn != null && !"".equals(noBtn)){
        	
        	ad.setButton(DialogInterface.BUTTON_NEGATIVE, noBtn, new DialogInterface.OnClickListener(){
        	    
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	//context.startActivity(new Intent (context, clazz) );
                }
            });
        	/*
            adb.setNegativeButton(noBtn, new DialogInterface.OnClickListener(){
    
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    
                }
                
            });
            */
        }
        //显示对话框
        //adb.show();
        ad.show();
    }
}
