package com.gst.mykotlintest.util;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.BuildConfig;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * author: GuoSongtao on 2017/12/21 14:01
 * email: 157010607@qq.com
 */

public class PermissionUtils {
    public static final int CHECK_PERMISSION_HAS_DIALOG = 100;
    public static final int CHECK_PERMISSION_NO_DIALOG = 101;


    @Target(ElementType.METHOD)//注解的作用域
    @Retention(RetentionPolicy.RUNTIME)//注解的有效时
    public @interface PermissionHelper {
        boolean permissionResult();

        int requestCode();
    }


    /**
     * @param activity
     * @param permissions
     * @param permissionResult
     * @param requestCode
     */
    public static void injectActivity(Activity activity, List<String> permissions, boolean permissionResult, int requestCode) {
        Class clazz = activity.getClass();
        LogUtil.Companion.i("permissions.size()=" + permissions.size() + ";clazz.getName()=" + clazz.getName());
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            // 注释
            PermissionHelper annotation = method.getAnnotation(PermissionHelper.class);
            if (annotation == null) continue;
            LogUtil.Companion.i("annotation!=null;permissionResult="+permissionResult+";requestCode="+requestCode);
            if (permissionResult == annotation.permissionResult() && requestCode == annotation.requestCode()) {
                try {
                    method.setAccessible(true);
                    //invoke,第一个参数是执行该方法的对象，之后的参数才是方法对应的参数或参数群
                    method.invoke(activity, permissions);
                    LogUtil.Companion.i("annotation!=null");
//                    method.invoke(null, permissions);//第一个参数是null,表示执行的是静态方法
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * android 6.0 权限申请
     **/
//    public static boolean checkCameraPermission(Activity activity) {
//        //不需要6.0以前的旧版本不需要检查权限
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
//
//        boolean hasCameraPermission = (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
//        //存在没授权的权限
//        if (!hasCameraPermission) {
//            // 申请权限。
//            String[] permissions = {Manifest.permission.CAMERA};
//            //shouldShowRequestPermissionRationale 表明用户没有完全禁止相机权限
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
//
//                ActivityCompat.requestPermissions(activity, permissions, 100);
//            } else {
//
//            }
////            Permission permission = AndPermission.with(activity).requestCode(100);
////            permission.permission(Manifest.permission.CAMERA);
////            permission.send();
//            return false;
//        } else {
//            //本来所有的权限就存在
//            return true;
//        }
//    }
//
//    /**
//     * miui8 设施权限回来 有4-5秒 查询已授权的状态是 未授权
//     *
//     * @param activity
//     * @param curentActPermissions
//     * @return
//     */
//    public static boolean checkCurrPermission(Activity activity, List<String> curentActPermissions) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
//        boolean hasPermission = true;
//        for (String permission : curentActPermissions) {
//            boolean hasCurrPermission = (ContextCompat.checkSelfPermission(activity, permission) == PackageManager
//                    .PERMISSION_GRANTED);
//            if (!hasCurrPermission) {
//                hasPermission = false;
//            }
//        }
//        return hasPermission;
//    }
//
//    public static void checkPermission(Activity activity, List<String> curentActPermissions) {
//        checkPermission(activity, curentActPermissions, CHECK_PERMISSION_HAS_DIALOG);
//    }
//
//    public static void checkPermission(Activity activity, List<String> curentActPermissions, int requestCode) {
//        //不需要权限
//        //不需要6.0以前的旧版本不需要检查权限
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
//
//        boolean hasPermission = true;
//        List<String> curentNoPermissions = new ArrayList<>();
//        for (String permission : curentActPermissions) {
//            boolean hasCurrPermission = (ContextCompat.checkSelfPermission(activity, permission) == PackageManager
//                    .PERMISSION_GRANTED);
//            if (!hasCurrPermission) {
//                curentNoPermissions.add(permission);
//                hasPermission = false;
//            }
//        }
//
//        //存在没授权的权限
//        if (!hasPermission) {
//            // 申请权限。
//            String[] permissions = curentNoPermissions.toArray(new String[curentNoPermissions.size()]);
//            ActivityCompat.requestPermissions(activity, permissions, 100);
////            Permission permission = AndPermission.with(activity).requestCode(requestCode);
////            //后面的 requestCode(100);  和 @PermissionYes(100) 对应
////            permission.permission(curentNoPermissions.toArray(new String[curentNoPermissions.size()]));
////            permission.send();
//        } else {
//            //本来所有的权限就存在
//
//        }
//    }


    /**
     * gotoMeizuPermission
     *
     * @param mCtx
     */
    public static void gotoPermissionSetting(Context mCtx) {
        //gotoMeizuPermission 这里是魅族
        Intent meizuIntent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        meizuIntent.addCategory(Intent.CATEGORY_DEFAULT);
        meizuIntent.putExtra("packageName", mCtx.getPackageName());
        try {
            mCtx.startActivity(meizuIntent);
            return;
        } catch (Exception e) {
            Log.i("PermissionSet", " gotoMeizuPermission  fialure!");
        }

        //小米
        Intent miuiIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        miuiIntent.setComponent(componentName);
        miuiIntent.putExtra("extra_pkgname", mCtx.getPackageName());
        try {
            mCtx.startActivity(miuiIntent);
            return;
        } catch (Exception e) {
            Log.i("PermissionSet", " gotoMiuiPermission  fialure!");
        }

        // MIUI 8
        miuiIntent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        miuiIntent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        miuiIntent.putExtra("extra_pkgname", mCtx.getPackageName());
        try {
            mCtx.startActivity(miuiIntent);
            return;
        } catch (Exception e) {
            Log.i("PermissionSet", " gotoMiuiPermission  fialure!");
        }

        //华为
        Intent huaweiIntent = new Intent();
        huaweiIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
        huaweiIntent.setComponent(comp);
        try {
            mCtx.startActivity(huaweiIntent);
        } catch (Exception e) {
            Log.i("PermissionSet", " gotoHuaweiPermission  fialure!");
        }

        // vivo 点击设置图标>加速白名单>我的app    点击软件管理>软件管理权限>软件>我的app>信任该软件
        Intent vivoIntent = mCtx.getPackageManager().getLaunchIntentForPackage("com.iqoo.secure");
        try {
            mCtx.startActivity(vivoIntent);
        } catch (Exception e) {
            Log.i("PermissionSet", " gotoVivoPermission  fialure!");
        }

        // oppo 点击设置图标>应用权限管理>按应用程序管理>我的app>我信任该应用      点击权限隐私>自启动管理>我的app
        Intent oppoIntent = mCtx.getPackageManager().getLaunchIntentForPackage("com.oppo.safe");
        try {
            mCtx.startActivity(oppoIntent);
        } catch (Exception e) {
            Log.i("PermissionSet", " gotoOppoPermission  fialure!");
        }

        //索尼
        Intent sonyIntent = new Intent();
        sonyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sonyIntent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        sonyIntent.setComponent(new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity"));
        try {
            mCtx.startActivity(sonyIntent);
        } catch (Exception e) {
            Log.i("PermissionSet", " gotoSonyPermission  fialure!");
        }


        //LG
        Intent lgIntent = new Intent("android.intent.action.MAIN");
        lgIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        lgIntent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        lgIntent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity"));
        try {
            mCtx.startActivity(lgIntent);
        } catch (Exception e) {
            Log.i("PermissionSet", " gotoSonyPermission  fialure!");
        }

        //乐视
        Intent letvIntent = new Intent();
        letvIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        letvIntent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        letvIntent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps"));
        try {
            mCtx.startActivity(letvIntent);
        } catch (Exception e) {
            Log.i("PermissionSet", " gotoSonyPermission  fialure!");
        }

        //360手机
        Intent intent360 = new Intent("android.intent.action.MAIN");
        intent360.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent360.putExtra("packageName", BuildConfig.APPLICATION_ID);
        intent360.setComponent(new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity"));
        try {
            mCtx.startActivity(sonyIntent);
        } catch (Exception e) {
            Log.i("PermissionSet", " gotoSonyPermission  fialure!");
        }

        // ToastUtil.show("其他机型，需要用户手动前去授权！");

        //其他机型
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", mCtx.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", mCtx.getPackageName());
        }
        mCtx.startActivity(localIntent);

//        if (mCtx instanceof Activity) {
//            ((Activity) mCtx).finish();
//        }
    }

}
