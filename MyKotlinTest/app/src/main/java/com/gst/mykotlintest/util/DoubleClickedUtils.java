package com.gst.mykotlintest.util;

import android.util.SparseArray;
import android.view.View;

/**
 * author: GuoSongtao on 2017/3/31 21:56
 * email: 157010607@qq.com
 */

public class DoubleClickedUtils {
    private static long lastClicked = 0;
    private final static int TIME_SPAN = 290;
    private static SparseArray<Long> viewOnClickedTimes = new SparseArray<>();

    public static boolean isDoubleClicked(int timeout) {
        long now = System.currentTimeMillis();
        if (now - lastClicked < timeout) {
            lastClicked = now;
            return true;
        }
        lastClicked = now;
        return false;
    }

    public static boolean isDoubleClicked(int timeout, View view) {
        int viewId = view.getId();
        long oldTime = viewOnClickedTimes.get(viewId, 0L);

        long now = System.currentTimeMillis();
        viewOnClickedTimes.put(viewId, now);
        if (now - oldTime < timeout) {
            return true;
        }
        return false;
    }

    public static boolean isDoubleClicked() {
        return isDoubleClicked(TIME_SPAN);
    }

    public static boolean isDoubleClickedShowToast(int timeout) {
        boolean flag = isDoubleClicked(timeout);
        if (flag) ToastUtil.Companion.show("请不要连续点击！");
        return flag;
    }

    public static boolean isDoubleClicked(View view) {
        return isDoubleClicked(TIME_SPAN, view);
    }

    public static boolean isDoubleClickedShowToast(int timeout, View view) {
        boolean flag = isDoubleClicked(timeout, view);
        if (flag) ToastUtil.Companion.show("请不要连续点击！");
        return flag;
    }

    public static void clearViewOnClickedTimes() {
        viewOnClickedTimes.clear();
    }
}
