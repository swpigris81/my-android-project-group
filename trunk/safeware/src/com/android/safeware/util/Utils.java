package com.android.safeware.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Utils {
    /**
     * 显示对话框
     * @param context
     * @param title 标题
     * @param titleId
     * @param message 消息
     * @param msgId
     * @param yesBtn YES按钮
     * @param yesBtnId
     * @param noBtn NO按钮
     * @param noBtnId
     */
    public static void openOptionsDialog(Context context, final String title, int titleId, String message, int msgId, String yesBtn, int yesBtnId, String noBtn, int noBtnId){
        AlertDialog.Builder adb=new AlertDialog.Builder(context);
        AlertDialog ad = adb.create();
        //按对话框以外的地方不起作用。按返回键还起作用
        ad.setCanceledOnTouchOutside(false);
        
        //按对话框以外的地方不起作用。按返回键也不起作用
        //setCanceleable(false);
        //设置对话框标题
        if(title != null && !"".equals(title)){
            adb.setTitle(title);
        }else{
            adb.setTitle(titleId);
        }
        //给对话框设置图标
        //ad.setIcon(null);
        //设置对话框的提示信息
        if(message != null && !"".equals(message)){
            adb.setMessage(message);
        }else{
            adb.setMessage(msgId);
        }
        //添加YES按钮
        if(yesBtn != null && !"".equals(yesBtn)){
            adb.setPositiveButton(yesBtn, new DialogInterface.OnClickListener(){
    
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    
                }
                
            });
        }else if(yesBtnId != -1){
            adb.setPositiveButton(yesBtnId, new DialogInterface.OnClickListener(){
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.out.println(title);
                }
                
            });
        }
        //添加NO按钮
        if(noBtn != null && !"".equals(noBtn)){
            adb.setNegativeButton(noBtn, new DialogInterface.OnClickListener(){
    
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    
                }
                
            });
        }else if(noBtnId != -1){
            adb.setNegativeButton(noBtnId, new DialogInterface.OnClickListener(){
                
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    
                }
                
            });
        }
        //显示对话框
        adb.show();
    }
}
