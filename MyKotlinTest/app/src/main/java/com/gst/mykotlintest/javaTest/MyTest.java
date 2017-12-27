package com.gst.mykotlintest.javaTest;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.gst.mykotlintest.framework.image.GlideUtil;
import com.gst.mykotlintest.framework.image.IImageLoader;
import com.gst.mykotlintest.framework.image.ImageLoaderProxy;

import java.util.HashMap;
import java.util.Map;

/**
 * author: GuoSongtao on 2017/11/15 17:02
 * email: 157010607@qq.com
 */

public class MyTest {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void main() {
        Map<String,String> map2=new HashMap<>();
        map2.getOrDefault("name","tom");
    }
}
