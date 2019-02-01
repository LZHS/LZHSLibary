package com.lzhs.library.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.lzhs.library.Utils;
import com.lzhs.library.utils.constants.Constants;

import java.lang.ref.WeakReference;


/**
 * Toast 管理类<br/>
 * 作者：LZHS<br/>
 * 时间： To018/To/6 17:37<br/>
 * 邮箱：10506To9507@qq.com
 */
public final class Toast {

    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static WeakReference<android.widget.Toast> sWeakToast;
    private static int sGravity = -1;
    private static int sXOffset = -1;
    private static int sYOffset = -1;
    private static int sBgColor = COLOR_DEFAULT;
    private static int sBgResource = -1;
    private static int sMsgColor = COLOR_DEFAULT;
    private static int sMsgTextSize = -1;

    private Toast() {
        throw new UnsupportedOperationException(Constants.ERROR_HINT.ERROR_HINT);
    }

    /**
     * 设置吐司位置
     *
     * @param gravity The gravity.
     * @param xOffset X-axis offset, in pixel.
     * @param yOffset Y-axis offset, in pixel.
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        sGravity = gravity;
        sXOffset = xOffset;
        sYOffset = yOffset;
    }

    /**
     * 设置背景颜色
     *
     * @param backgroundColor The color of background.
     */
    public static void setBgColor(@ColorInt final int backgroundColor) {
        sBgColor = backgroundColor;
    }

    /**
     * 设置背景资源
     *
     * @param bgResource The resource of background.
     */
    public static void setBgResource(@DrawableRes final int bgResource) {
        sBgResource = bgResource;
    }

    /**
     * 设置消息颜色
     *
     * @param msgColor The color of message.
     */
    public static void setMsgColor(@ColorInt final int msgColor) {
        sMsgColor = msgColor;
    }

    /**
     * 设置消息字体大小
     *
     * @param textSize The text size of message.
     */
    public static void setMsgTextSize(final int textSize) {
        sMsgTextSize = textSize;
    }

    /**
     * 显示短时吐司
     *
     * @param text The text.
     */
    public static void show(@NonNull final CharSequence text) {
        show(text, android.widget.Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId The resource id for text.
     */
    public static void show(@StringRes final int resId) {
        show(resId, android.widget.Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void show(@StringRes final int resId, final Object... args) {
        if (args != null && args.length == 0) {
            show(resId, android.widget.Toast.LENGTH_SHORT);
        } else {
            show(resId, android.widget.Toast.LENGTH_SHORT, args);
        }
    }

    /**
     * 显示短时吐司
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void show(final String format, final Object... args) {
        if (args != null && args.length == 0) {
            show(format, android.widget.Toast.LENGTH_SHORT);
        } else {
            show(format, android.widget.Toast.LENGTH_SHORT, args);
        }
    }

    /**
     * 显示长时吐司
     *
     * @param text The text.
     */
    public static void showLong(@NonNull final CharSequence text) {
        show(text, android.widget.Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId The resource id for text.
     */
    public static void showLong(@StringRes final int resId) {
        show(resId, android.widget.Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showLong(@StringRes final int resId, final Object... args) {
        if (args != null && args.length == 0) {
            show(resId, android.widget.Toast.LENGTH_SHORT);
        } else {
            show(resId, android.widget.Toast.LENGTH_LONG, args);
        }
    }

    /**
     * 显示长时吐司
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showLong(final String format, final Object... args) {
        if (args != null && args.length == 0) {
            show(format, android.widget.Toast.LENGTH_SHORT);
        } else {
            show(format, android.widget.Toast.LENGTH_LONG, args);
        }
    }

    /**
     * 显示短时自定义吐司
     *
     * @param layoutId ID for an XML layout resource to load.
     */
    public static View showCustomShort(@LayoutRes final int layoutId) {
        final View view = getView(layoutId);
        show(view, android.widget.Toast.LENGTH_SHORT);
        return view;
    }

    /**
     * 显示长时自定义吐司
     *
     * @param layoutId ID for an XML layout resource to load.
     */
    public static View showCustomLong(@LayoutRes final int layoutId) {
        final View view = getView(layoutId);
        show(view, android.widget.Toast.LENGTH_LONG);
        return view;
    }

    /**
     * 取消吐司显示
     */
    public static void cancel() {
        final android.widget.Toast toast;
        if (sWeakToast != null && (toast = sWeakToast.get()) != null) {
            toast.cancel();
            sWeakToast = null;
        }
    }

    private static void show(@StringRes final int resId, final int duration) {
        show(Utils.getApp().getResources().getText(resId).toString(), duration);
    }

    private static void show(@StringRes final int resId, final int duration, final Object... args) {
        show(String.format(Utils.getApp().getResources().getString(resId), args), duration);
    }

    private static void show(final String format, final int duration, final Object... args) {
        show(String.format(format, args), duration);
    }

    private static void show(final CharSequence text, final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                final android.widget.Toast toast = android.widget.Toast.makeText(Utils.getTopActivityOrApp(), text, duration);
                sWeakToast = new WeakReference<>(toast);
                final TextView tvMessage = toast.getView().findViewById(android.R.id.message);
                int msgColor = tvMessage.getCurrentTextColor();
                //it solve the font of toast
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvMessage.setTextAppearance(android.R.style.TextAppearance);
                } else {
                    tvMessage.setTextAppearance(tvMessage.getContext(), android.R.style.TextAppearance);
                }
                if (sMsgColor != COLOR_DEFAULT) {
                    tvMessage.setTextColor(sMsgColor);
                } else {
                    tvMessage.setTextColor(msgColor);
                }
                if (sMsgTextSize != -1) {
                    tvMessage.setTextSize(sMsgTextSize);
                }
                if (sGravity != -1 || sXOffset != -1 || sYOffset != -1) {
                    toast.setGravity(sGravity, sXOffset, sYOffset);
                }
                setBg(toast, tvMessage);
                toast.show();
            }
        });
    }

    private static void show(final View view, final int duration) {
        HANDLER.post(new Runnable() {
            @Override
            public void run() {
                cancel();
                final android.widget.Toast toast = new android.widget.Toast(Utils.getTopActivityOrApp());
                sWeakToast = new WeakReference<>(toast);

                toast.setView(view);
                toast.setDuration(duration);
                if (sGravity != -1 || sXOffset != -1 || sYOffset != -1) {
                    toast.setGravity(sGravity, sXOffset, sYOffset);
                }
                setBg(toast);
                toast.show();
            }
        });
    }

    private static void setBg(final android.widget.Toast toast) {
        final View toastView = toast.getView();
        if (sBgResource != -1) {
            toastView.setBackgroundResource(sBgResource);
        } else if (sBgColor != COLOR_DEFAULT) {
            Drawable background = toastView.getBackground();
            if (background != null) {
                background.setColorFilter(
                        new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN)
                );
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    toastView.setBackground(new ColorDrawable(sBgColor));
                } else {
                    toastView.setBackgroundDrawable(new ColorDrawable(sBgColor));
                }
            }
        }
    }

    private static void setBg(final android.widget.Toast toast, final TextView tvMsg) {
        View toastView = toast.getView();
        if (sBgResource != -1) {
            toastView.setBackgroundResource(sBgResource);
            tvMsg.setBackgroundColor(Color.TRANSPARENT);
        } else if (sBgColor != COLOR_DEFAULT) {
            Drawable tvBg = toastView.getBackground();
            Drawable msgBg = tvMsg.getBackground();
            if (tvBg != null && msgBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
                tvMsg.setBackgroundColor(Color.TRANSPARENT);
            } else if (tvBg != null) {
                tvBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else if (msgBg != null) {
                msgBg.setColorFilter(new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
            } else {
                toastView.setBackgroundColor(sBgColor);
            }
        }
    }

    private static View getView(@LayoutRes final int layoutId) {
        LayoutInflater inflate =
                (LayoutInflater) Utils.getApp().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflate != null ? inflate.inflate(layoutId, null) : null;
    }
}
