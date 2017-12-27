package com.gst.mykotlintest.util;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ApplicationErrorReport;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.LoginFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Filter;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.regex.Pattern;

import dagger.internal.Preconditions;

/**
 * author: GuoSongtao on 2017/12/21 11:48
 * email: 157010607@qq.com
 */

public class AppHelper {


    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        if (!fragment.isAdded() && !fragment.isVisible()) {
            Preconditions.checkNotNull(fragmentManager);
            Preconditions.checkNotNull(fragment);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(frameId, fragment);
            //transaction.commit();
            transaction.commitAllowingStateLoss();
        }
    }

    public static void setEtHintWithImage(String hintText, Drawable d, EditText editText) {
        SpannableString text = new SpannableString(hintText);
        // ImageSpan imageSpan = new ImageSpan();

        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan imageSpan = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
        text.setSpan(imageSpan, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(text);
    }

    /**
     * dip 转化为 px
     * 1920*780分辨率的手机 480dip= 1920px
     *
     * @param ctx
     * @param dip
     * @return
     */
    public static int dip2px(Context ctx, int dip) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (scale * dip + 0.5F);
    }

    /**
     * px 转化为 dip
     * 1920*780分辨率的手机 480dip= 1920px
     *
     * @param ctx
     * @param px
     * @return
     */
    public static int px2dip(Context ctx, int px) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5F);
    }


    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();

    }

    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = {width, height};
        return result;
    }

    /**
     * 打开系统的Apk安装界面
     *
     * @param ctx
     * @param apkFile
     */
    public static void installApk(Context ctx, File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(apkFile);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        ctx.startActivity(intent);
    }

    /**
     * 卸载app
     *
     * @param context
     * @param packageName
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri uri = Uri.parse("package:" + packageName);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * @param context
     * @param clazzName
     * @return
     */
    public static boolean isServiceRunning(Context context, String clazzName) {
        boolean isRunning = false;
        if (context == null || TextUtils.isEmpty(clazzName)) {
            return isRunning;
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfos = activityManager.getRunningServices(Integer.MAX_VALUE);
//        serviceInfos.iterator();
        for (ActivityManager.RunningServiceInfo serviceInfo : serviceInfos) {
            if (clazzName.equalsIgnoreCase(serviceInfo.service.getClassName())) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 停止service
     *
     * @param context
     * @param clazzName
     * @return
     */
    public static boolean stopService(Context context, String clazzName) {
        boolean stopSucc = false;
        if (context == null || TextUtils.isEmpty(clazzName)) {
            return stopSucc;
        }
        try {
            Intent servieIntent = new Intent(context, Class.forName(clazzName));
            context.stopService(servieIntent);
            stopSucc = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stopSucc;
    }

    /**
     * 获取手机cpu内核数
     *
     * @return
     */
    public static int getCoresNum() {
        int count = 1;
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (Pattern.matches("cpu[0-9]", file.getName())) {
                        return true;
                    }
                    return false;
                }
            });
            count = files.length;
        } catch (Exception e) {
        }
        return count;
    }

    /**
     * 确认APP 网络是否可用
     * 注，权限动态请求，也必须现在manifest中先声明
     * 如，Manifest如果没有ACCESS_NETWORK_STATE，即使context请求了权限，也会没有ACCESS_NETWORK_STATE权限
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) return false;
        boolean isAvailable = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
                    networkInfo = connectivityManager.getActiveNetworkInfo();
                } else {
                    ToastUtil.Companion.show("没有权限,网络不可用！");
                    isAvailable = false;
                }
            } else {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
            if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
                isAvailable = true;
            }
        }
        return isAvailable;
    }

    /**
     * GPS是否可用
     *
     * @param context
     * @return
     */
    public static boolean isGPSenable(Context context) {
        if (context == null) return false;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null)
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        else
            return false;
    }

    /**
     * wifi是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiEnable(Context context) {
        if (context == null) return false;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            return wifiManager.isWifiEnabled();
        } else {
            return false;
        }
    }

    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager magerConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager magerTele = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ((magerConn.getActiveNetworkInfo() != null && magerConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED)
                || (magerTele.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS));
        //UMTS universal mobile telecommunications service 通用移动通信业务
    }

}
