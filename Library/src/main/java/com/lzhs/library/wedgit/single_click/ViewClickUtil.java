package com.lzhs.library.wedgit.single_click;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.concurrent.TimeUnit;

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019/2/19 : 5:08 PM<br/>
 */
public final class ViewClickUtil {

    private long mSkipDuration;
    private TimeUnit mTimeUnit;
    private ViewClick mViewClick;
    private Type mType = Type.RX_VIEW;

    public void clicks(@NonNull View.OnClickListener onClickListener, @NonNull View view) {
        switch (mType) {
            case RX_VIEW:
                mViewClick = new RxViewClickImp(view, onClickListener);
                break;
            case VIEW:
                mViewClick = new ViewClickImp(view, onClickListener);
                break;
        }
        mViewClick.throttle(mSkipDuration, mTimeUnit);
    }

    public void clicks(@NonNull View.OnClickListener onClickListener, @NonNull View... views) {
        for (View view : views) clicks(onClickListener, view);
    }

    private ViewClickUtil(Builder builder) {
        mSkipDuration = builder.mSkipDuration;
        mTimeUnit = builder.mTimeUnit;
        mType=builder.mType;
    }

    public static class Builder {

        private long mSkipDuration = 800l;
        private TimeUnit mTimeUnit = TimeUnit.MILLISECONDS;
        private Type mType = Type.RX_VIEW;
        private static Builder mInstance = new Builder();

        /**
         * 设置按钮间隔时间 默认800毫秒
         * @param mSkipDuration
         * @return
         */
        public static Builder setSkipDuration(long mSkipDuration) {
            mInstance.mSkipDuration = mSkipDuration;
            return mInstance;
        }

        /**
         * 设置按钮间隔时间单位 默认毫秒级
         * @param mTimeUnit
         * @return
         */
        public static Builder setTimeUnit(TimeUnit mTimeUnit) {
            mInstance.mTimeUnit = mTimeUnit;
            return mInstance;
        }

        /**
         * 设置间隔实现方式 默认RX
         * @param type
         * @return
         */
        public static Builder setType(Type type) {
            mInstance.mType = type;
            return mInstance;
        }

        public static ViewClickUtil build() {
            return new ViewClickUtil(mInstance);
        }

    }

    public enum Type {
        RX_VIEW, VIEW
    }
}
