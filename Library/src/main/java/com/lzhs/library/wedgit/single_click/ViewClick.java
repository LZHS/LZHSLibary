package com.lzhs.library.wedgit.single_click;

import android.view.View;

import java.util.concurrent.TimeUnit;

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019/2/19 : 5:02 PM<br/>
 */
public interface ViewClick extends View.OnClickListener {
    void throttle(long skipDuration, TimeUnit timeUnit);

}

