package com.lzhs.library.bases;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lzhs.library.wedgit.swipe_back.SwipeBackLayout;
import com.lzhs.library.wedgit.swipe_back.Utils;
import com.lzhs.library.wedgit.swipe_back.app.SwipeBackActivityBase;
import com.lzhs.library.wedgit.swipe_back.app.SwipeBackActivityHelper;

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2018/12/27 : 2:23 PM<br/>
 */
public class BaseSwipeBackActivity extends AppCompatActivity implements SwipeBackActivityBase {
    protected final String TAG = this.getClass().getName();
    protected SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}