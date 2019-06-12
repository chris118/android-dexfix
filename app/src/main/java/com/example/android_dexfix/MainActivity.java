package com.example.android_dexfix;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                })
                .start();

        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG,"onDestroy");
        super.onDestroy();
    }

    @OnClick(R.id.btn_test) void test() {
        DexFixTest dexFixTest = new DexFixTest();
        dexFixTest.testFix(this);
    }

    @OnClick(R.id.btn_fix) void fix() {
        fixBug();
    }

    private void fixBug() {
        String dexName = "classes2.dex";
        File fileDir = getDir(HotfixConstants.DEX_DIR, Context.MODE_PRIVATE);
        //⭐️⭐️⭐️⭐️⭐️DexClassLoader指定的应用程序目录
        String filePath = fileDir.getPath()+File.separator+dexName;
        File dexFile = new File(filePath);
        if(dexFile.exists()){
            dexFile.delete();
        }

        //移置dex文件位置（从sd卡中移置到应用目录）
        InputStream is = null;
        FileOutputStream os = null;
        // ⭐️⭐️⭐️⭐️⭐️sd卡路径应为"/storage/emulated/0"，此处直接将文件拖入Genymotion，因此路径还需加上"/Download"
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download";

        try {
            is = new FileInputStream(sdPath+File.separator+dexName);
            os = new FileOutputStream(filePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            while((len = is.read(buffer)) != -1){
                os.write(buffer, 0, len);
            }

            //测试是否成功写入
            File fileTest = new File(filePath);
            if(fileTest.exists()){
                Toast.makeText(this, "dex重写成功", Toast.LENGTH_SHORT).show();
            }

            //获取到已修复的dex文件，进行热修复操作
            DexFixUtils.loadFixedDex(this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
