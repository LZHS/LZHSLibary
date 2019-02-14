package com.lzhs.library.wedgit.banner.holder;

import android.content.Context;
import android.view.View;

/**
 * Description:  ViewHolder 创建器
 * <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2018/9/19$ 下午2:12$ <br/>
 */
public interface LZHSViewHolder<T> {
    /**
     *  创建View
     * @param context
     * @return
     */
    View createView(Context context);

    /**
     * 绑定数据
     * @param context
     * @param position
     * @param data
     */
    void onBind(Context context, int position, T data);
}
