package com.lzhs.library.wedgit.single_click;

import android.support.annotation.NonNull;
import android.view.View;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019/2/19 : 5:20 PM<br/>
 */
public class RxViewClickImp implements ViewClick {

    private View mView;
    private View.OnClickListener mOnClickListener;
    private long mSkipDuration;
    private TimeUnit mTimeUnit = TimeUnit.MILLISECONDS;
    private ObservableEmitter mObservableEmitter;
    private Observable mObservable;

    public RxViewClickImp(@NonNull View view, @NonNull View.OnClickListener onClickListener) {
        mView = view;
        mOnClickListener = onClickListener;
        mView.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        if (mObservable == null) {
            mObservable = Observable.create((ObservableOnSubscribe<View>) emitter -> mObservableEmitter = emitter);
            throttleFirst(mObservable);
        }
        mObservableEmitter.onNext(mView);
    }

    @Override
    public void throttle(long skipDuration, TimeUnit timeUnit) {
        mSkipDuration = skipDuration;
        mTimeUnit = timeUnit;
    }

    private void throttleFirst(Observable observable) {
        observable.throttleFirst(mSkipDuration, mTimeUnit)
                .subscribe((Consumer<View>) view -> mOnClickListener.onClick(view));
    }

}
