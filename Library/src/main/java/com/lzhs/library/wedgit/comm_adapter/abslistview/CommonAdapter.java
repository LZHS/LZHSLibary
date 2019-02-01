package com.lzhs.library.wedgit.comm_adapter.abslistview;

import android.content.Context;


import com.lzhs.library.wedgit.comm_adapter.abslistview.base.ItemViewDelegate;

import java.util.List;


/**
 * <br/>
 * 作者：LZHS<br/>
 * 时间： 2018/2/6 17:37<br/>
 * 邮箱：1050629507@qq.com
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T>
{

    /**
     *
     * @param context
     * @param layoutId
     * @param datas
     */
    public CommonAdapter(Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position)
            {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T item, int position)
            {
                CommonAdapter.this.convert(holder, item, position);
            }
        });
    }

    /**
     *
     * @param viewHolder
     * @param item
     * @param position
     */
    protected abstract void convert(ViewHolder viewHolder, T item, int position);

}
