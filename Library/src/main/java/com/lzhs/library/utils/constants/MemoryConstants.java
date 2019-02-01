package com.lzhs.library.utils.constants;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 字节单位常量<br/>
 * 作者：LZHS<br/>
 * 时间： 2018/2/6 17:37<br/>
 * 邮箱：1050629507@qq.com
 */
public final class MemoryConstants {

    public static final int BYTE = 1;
    public static final int KB   = 1024;
    public static final int MB   = 1048576;
    public static final int GB   = 1073741824;

    @IntDef({BYTE, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
