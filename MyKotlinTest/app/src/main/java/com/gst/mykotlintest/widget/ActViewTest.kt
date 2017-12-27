package com.gst.mykotlintest.widget

import android.Manifest
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import com.gst.mykotlintest.R
import kotlinx.android.synthetic.main.act_view_test.*
import java.security.Permission

/**
 * author: GuoSongtao on 2017/12/15 15:10
 * email: 157010607@qq.com
 */
class ActViewTest : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_view_test)

//        load_show.setOnClickListener { v ->
//            (v as LoadingView2).startAnimator()
//        }

    }
//
//    override fun onResume() {
//        super.onResume()
//        load_show.startAnimator()
//    }

    /***** 权限申请部分 start******/
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>?, grantResults: IntArray?) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (index in 0..permissions!!.size - 1) {
            var permission=permissions[index]
            var shouldRequest=shouldShowRequestPermissionRationale(permission)
            if (Manifest.permission.CAMERA.equals(permissions.get(index))) {

            }
        }
    }
    /***** 权限申请部分 end******/

}