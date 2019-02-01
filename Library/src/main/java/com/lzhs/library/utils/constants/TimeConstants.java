package com.lzhs.library.utils.constants;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 时间常量<br/>
 * 作者：LZHS<br/>
 * 时间： 2018/2/6 17:37<br/>
 * 邮箱：1050629507@qq.com
 */
public final class TimeConstants {
    /**
     * 毫秒
     */
    public static final int MSEC = 1;
    /**
     * 秒
     */
    public static final int SEC = 1000;
    /**
     * 分钟
     */
    public static final int MIN = 60000;
    /**
     * 小时
     */
    public static final int HOUR = 3600000;
    /**
     * 天
     */
    public static final int DAY = 86400000;

    @IntDef({MSEC, SEC, MIN, HOUR, DAY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }
}
