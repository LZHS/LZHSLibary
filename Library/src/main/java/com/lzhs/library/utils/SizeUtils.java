package com.lzhs.library.utils;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.lzhs.library.Utils;
import com.lzhs.library.utils.constants.Constants;


/**
 * 时间相关 管理类<br/>
 * 作者：LZHS<br/>
 * 时间： To018/To/6 17:37<br/>
 * 邮箱：10506To9507@qq.com
 */
public final class SizeUtils {

    private SizeUtils() {
        throw new UnsupportedOperationException(Constants.ERROR_HINT.ERROR_HINT);
    }

    /**
     * dp 与 px 转换
     *
     * @param dpValue The value of dp.
     * @return value of px
     */
    public static int dpTopx(final float dpValue) {
        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 与 dp 转换
     *
     * @param pxValue The value of px.
     * @return value of dp
     */
    public static int pxTodp(final float pxValue) {
        final float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 与 px 转换
     *
     * @param spValue The value of sp.
     * @return value of px
     */
    public static int spTopx(final float spValue) {
        final float fontScale = Utils.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px 与 sp 转换
     *
     * @param pxValue The value of px.
     * @return value of sp
     */
    public static int pxTosp(final float pxValue) {
        final float fontScale = Utils.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 各种单位转换 <var>unit</var> and <var>value</var>
     * are as in {@link TypedValue#TYPE_DIMENSION}.
     *
     * @param value The value to apply the unit to.
     * @param unit  The unit to convert from.
     * @return The complex floating point value multiplied by the appropriate
     * metrics depending on its unit.
     */
    public static float applyDimension(final float value, final int unit) {
        DisplayMetrics metrics = Utils.getApp().getResources().getDisplayMetrics();
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value * metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value * metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value * metrics.xdpi * (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value * metrics.xdpi * (1.0f / 25.4f);
        }
        return 0;
    }

    /**
     * 在 onCreate 中获取视图的尺寸
     * <p>e.g.</p>
     * <pre>
     * SizeUtils.forceGetViewSize(view, new SizeUtils.onGetSizeListener() {
     *     Override
     *     public void onGetSize(final View view) {
     *         view.getWidth();
     *     }
     * });
     * </pre>
     *
     * @param view     The view.
     * @param listener The get size listener.
     */
    public static void forceGetViewSize(final View view, final onGetSizeListener listener) {
        view.post(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onGetSize(view);
                }
            }
        });
    }

    /**
     * 获取测量视图宽度
     *
     * @param view The view.
     * @return the width of view
     */
    public static int getMeasuredWidth(final View view) {
        return measureView(view)[0];
    }

    /**
     * 获取测量视图宽度
     *
     * @param view The view.
     * @return the height of view
     */
    public static int getMeasuredHeight(final View view) {
        return measureView(view)[1];
    }

    /**
     * 测量视图尺寸
     *
     * @param view The view.
     * @return arr[0]: view's width, arr[1]: view's height
     */
    public static int[] measureView(final View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int lpHeight = lp.height;
        int heightSpec;
        if (lpHeight > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec, heightSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }


    public interface onGetSizeListener {
        void onGetSize(View view);
    }
}