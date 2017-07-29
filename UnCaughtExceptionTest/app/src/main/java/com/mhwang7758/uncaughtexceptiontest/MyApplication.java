package com.mhwang7758.uncaughtexceptiontest;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;
import android.widget.Toast;

import com.osama.firecrasher.CrashListener;
import com.osama.firecrasher.FireCrasher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Description :
 * Author :mhwang
 * Date : 2017/7/29
 * Version : V1.0
 */

public class MyApplication extends Application {
    private static final String ERROR_DIR_PATH = Environment
            .getExternalStorageDirectory() + File.separator + "AppErrorNotes";

    private void showToast(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 创建存放日志的文件夹
        createAppErrorDirectory();

        // FireCrasher使用
        FireCrasher.install(this, new CrashListener() {
            @Override
            public void onCrash(Throwable throwable, Activity activity) {
                // 这里可以选择是否恢复发生异常的Activity
//                recover(activity);

                Toast.makeText(getBaseContext(),"发生错误啦",Toast.LENGTH_SHORT).show();
                // 保存异常信息的路径
                String path = ERROR_DIR_PATH + File.separator + "11.txt";
                File file = new File(path);
                if (!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Writer writer = new FileWriter(file,true);
                    PrintWriter printWriter = new PrintWriter(writer);
                    printWriter.write("app error :");
                    throwable.printStackTrace(printWriter);
                    printWriter.flush();
                    printWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                throwable.printStackTrace();
            }
        });

    }

    private void createAppErrorDirectory(){
        File file = new File(ERROR_DIR_PATH);
        if (!file.exists()){
            file.mkdir();
        }
    }

}
