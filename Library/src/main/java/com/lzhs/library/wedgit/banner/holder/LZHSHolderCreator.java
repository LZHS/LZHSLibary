package com.lzhs.library.wedgit.banner.holder;


/**
 * Description: Banner ViewHolder 创建器
 * <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2018/9/19$ 下午2:12$ <br/>
 */
public interface LZHSHolderCreator<VH extends LZHSViewHolder> {
    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();
}
