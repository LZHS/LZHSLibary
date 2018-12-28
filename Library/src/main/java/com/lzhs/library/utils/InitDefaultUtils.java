package com.lzhs.library.utils;

import android.app.Application;
import android.content.Context;

import com.lzhs.library.wedgit.autosize.AutoSizeConfig;
import com.lzhs.library.wedgit.autosize.utils.SharedPreUtils;

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2018/12/27 : 5:44 PM<br/>
 */
public final class InitDefaultUtils {
    private InitDefaultUtils() {
    }

    /**
     * 初始化 日志打印器
     *
     * @param mContext
     * @param isDebug
     */
    public static void initLogUtil(Context mContext, boolean isDebug) {
        LogUtil.Config config = LogUtil.init(mContext)
                .setLogSwitch(isDebug)// 设置 log 总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(isDebug)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置 log 全局标签，默认为空
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setLogHeadSwitch(isDebug)// 设置 log 头信息开关，默认为开
                .setLogFileSwitch(false)// 打印 log 时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的 /cache/log/ 目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为 "alog"，即写入文件为 "alog-MM-dd.txt"
                .setBorderSwitch(isDebug)// 输出日志是否带边框开关，默认开
                .setSingleTagSwitch(isDebug)// 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setConsoleFilter(LogUtil.V)// log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(LogUtil.V)// log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setStackDeep(1)// log 栈深度，默认为 1
                .setStackOffset(0);// 设置栈偏移，比如二次封装的话就需要设置，默认为 0
        LogUtil.d(config.toString());
    }

    /**
     * 初始化AndroidAutoSize
     *
     * @param mContext
     */
    public static void initAutoSize(Context mContext) {
        AutoSizeConfig.getInstance()
                .init((Application) mContext.getApplicationContext())
                .setUseDeviceSize(false);
    }

    public static void initSharedPreferencesp(Context mContext) {
        SharedPreUtils.setContext(mContext);
    }
}
