package com.lzhs.library;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.net.Uri;

import com.lzhs.library.utils.InitDefaultUtils;

/**
 * ================================================
 * 通过声明 {@link ContentProvider} 自动完成初始化
 * <a href="https://www.jianshu.com/u/6c0638378cc8">Contact me</a>
 * <a href="https://github.com/LZHS/LZHSLibary">Follow me</a>
 * ================================================
 */
public class InitProvider extends ContentProvider {
    @Override
    public boolean onCreate() {
        boolean isDebug = (getContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        Utils.init(getContext());
        InitDefaultUtils.initLogUtil(getContext(), isDebug);
        InitDefaultUtils.initAutoSize(getContext());
        InitDefaultUtils.initSharedPreferencesp(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
