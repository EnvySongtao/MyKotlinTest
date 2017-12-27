package com.gst.mykotlintest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: GuoSongtao on 2017/11/16 17:16
 * email: 157010607@qq.com
 */

public class ActButterknife extends Activity {
    @BindView(R.id.iv_show)
    ImageView ivShow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image_test);
        ButterKnife.bind(this);
    }

}
