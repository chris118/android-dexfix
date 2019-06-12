package com.example.android_dexfix;

import android.content.Context;
import android.widget.Toast;

//
// class文件 ./android-dexfix/app/build/intermediates/javac/debug/compileDebugJavaWithJavac/classes/com/example/android_dexfix/DexFixTest.class
// dx工具路径: /Users/wangxiaopeng/Library/Android/sdk/build-tools/28.0.3
// 生成 dex文件:
// 1. 在桌面随意建一个文件夹放置该文件test
// 2. class 文件放在 /Users/wangxiaopeng/Desktop/test/com/example/android_dexfix/DexFixTest.class (文件路径是包名)
// 3. 执行 dx --dex --output=/Users/wangxiaopeng/Desktop/test/classes2.dex /Users/wangxiaopeng/Desktop/test/

public class DexFixTest {
    public  void testFix(Context context){
        int dividend = 10;
        //bug：除数不可为0
        int divisor = 0;
        Toast.makeText(context, "shit:"+dividend/divisor, Toast.LENGTH_SHORT).show();
    }
}
