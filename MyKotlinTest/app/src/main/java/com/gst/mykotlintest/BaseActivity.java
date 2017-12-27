package com.gst.mykotlintest;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.gst.mykotlintest.util.AppHelper;
import com.gst.mykotlintest.util.DoubleClickedUtils;
import com.gst.mykotlintest.util.LogUtil;
import com.gst.mykotlintest.util.PermissionUtils;
import com.gst.mykotlintest.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author: GuoSongtao on 2017/12/21 14:20
 * email: 157010607@qq.com
 * TODO: 2017/12/21 添加权限申请
 */

public class BaseActivity extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback {


    private final String TAG = BaseActivity.class.getSimpleName();
    private int permissionRequestCode = 0;
    private List<String> permissions = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPermission(permissions);
        checkPermission(permissions, PERMISSION_REQUEST);
        LogUtil.Companion.i("AppHelper.isWifiEnabled=" + AppHelper.isWifiEnabled(this));
    }






    public boolean checkPermission(List<String> permissions, int requestCode) {
        if (permissions == null || permissions.isEmpty()) return true;
        boolean hasAllPermission = true;
        List<String> permissionsNo = new ArrayList<>();
        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, permission)) {
                LogUtil.Companion.i("hasPermission=" + permission);
            } else {
                hasAllPermission = false;
                permissionsNo.add(permission);
            }
        }

        if (hasAllPermission) {
            return true;
        } else {
            String permission = "";
            for (String per : permissionsNo) {
                permission = permission + per + " ; ";
            }

            LogUtil.Companion.i("permissionNOs=" + permission);
            permissionRequestCode = requestCode;
            ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissionsNo.size()]), requestCode);
            return false;
        }

    }

    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LogUtil.Companion.i(TAG + " == onRequestPermissionsResult == ");
        boolean permissionResult = true;
        List<String> permissionNos = new ArrayList<>();
        List<String> permissionHad = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            //权限申请失败
            int permisssionStatus = grantResults[i];
            if (permisssionStatus == PackageManager.PERMISSION_DENIED) {
                permissionResult = false;
                permissionNos.add(permissions[i]);
            } else {
                permissionHad.add(permissions[i]);
            }
        }

        if (!permissionResult) {
            String permission = "";
            for (String per : permissionNos) {
                permission = permission + per + " ; ";
            }
            LogUtil.Companion.i("permissions=" + permission);
            PermissionUtils.injectActivity(this, permissionNos, permissionResult, permissionRequestCode);
        } else {
            PermissionUtils.injectActivity(this, permissionHad, permissionResult, permissionRequestCode);
        }
        LogUtil.Companion.i("permissionResult=" + permissionResult);
    }

    /******** subActivity 权限申请 start*****/
    private final int PERMISSION_REQUEST = 100;


    @PermissionUtils.PermissionHelper(permissionResult = false, requestCode = PERMISSION_REQUEST)
    public void onNoPermissionsBack(List<String> permissionNos) {
        LogUtil.Companion.i("permissionNos.size()=" + permissionNos.size());
        if (permissionNos != null && !permissionNos.isEmpty()
                && permissionNos.contains(Manifest.permission.CAMERA)) {
            ToastUtil.Companion.longShow("你尚未打开拍照权限！");
            PermissionUtils.gotoPermissionSetting(this);
        }
    }

    @PermissionUtils.PermissionHelper(permissionResult = true, requestCode = PERMISSION_REQUEST)
    public void onPermissionsBack(List<String> permissionHad) {
        LogUtil.Companion.i("permissionNos.size()=" + permissionHad.size());
        ToastUtil.Companion.longShow("设置权限成功");
    }

    /************extends method impelement start***************/
    protected void initPermission(List<String> permissions) {
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.CAMERA);
//        permissions.add(Manifest.permission.CALL_PHONE); //小米打电话权限，不能设置为允许，必须实时申请
        permissions.add(Manifest.permission.INTERNET);
//        permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
    }
    /************extends method impelement end*****************/
    /******** subActivity  权限申请 end*****/

//    protected  void initPermission(List<String> permissions){
//
//    }
    @RequiresApi()
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (isInPictureInPictureMode() || isInMultiWindowMode()) {
                ToastUtil.Companion.show("分屏模式或画中画模式 onResume()");
                return;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (isInPictureInPictureMode() || isInMultiWindowMode()) {
                ToastUtil.Companion.show("分屏模式或画中画模式 onPause()");
                return;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DoubleClickedUtils.clearViewOnClickedTimes();
    }
}
