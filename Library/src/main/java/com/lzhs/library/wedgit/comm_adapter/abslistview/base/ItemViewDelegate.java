package com.lzhs.library.wedgit.comm_adapter.abslistview.base;


import com.lzhs.library.wedgit.comm_adapter.abslistview.ViewHolder;

/**
 * <br/>
 * 作者：LZHS<br/>
 * 时间： 2018/2/6 17:37<br/>
 * 邮箱：1050629507@qq.com
 */
public interface ItemViewDelegate<T>
{

    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T item, int position);

    public abstract void convert(ViewHolder holder, T t, int position);



}
