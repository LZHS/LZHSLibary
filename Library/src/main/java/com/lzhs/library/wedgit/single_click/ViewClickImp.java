package com.lzhs.library.wedgit.single_click;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.view.View;

import com.lzhs.library.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019/2/19 : 5:03 PM<br/>
 */
public class ViewClickImp implements ViewClick {

    private View mView;
    private View.OnClickListener mOnClickListener;
    private long mOldTime;
    private long mDelayMilliseconds = 800L;

    public ViewClickImp(@NonNull View view, @NonNull View.OnClickListener onClickListener) {
        mView = view;
        mOnClickListener = onClickListener;
        mView.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        // 考虑到用户手动修改系统时间，所以要使用以下方式
        long nowTime = SystemClock.elapsedRealtime();
        long intervalTime = nowTime - mOldTime;
        if (mOldTime == 0 || intervalTime >= mDelayMilliseconds) {
            mOldTime = nowTime;
            mOnClickListener.onClick(v);
        }
    }

    @Override
    public void throttle(@Size(max = Long.MAX_VALUE, min = 0l) long skipDuration,
                         @NonNull TimeUnit timeUnit) {
        if (skipDuration < 0)
            skipDuration = 0;
        mDelayMilliseconds = timeUnit.toMillis(skipDuration);
    }
}
