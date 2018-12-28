package com.lzhs.library.wedgit.autosize.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lzhs.library.wedgit.autosize.utils.encryption.Encode3DES;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 该类实现了对 SharedPreferences 的二次封装<br/>
 * 加密方式，采用加密之后 key和val 都会被加密<br/>
 * 注意： 当你要保存的数据为 Set 时，数据不会被加密<br/>
 * 作者：lzhs <br/>
 * 时间： 2016/10/9 0009 17:43<br/>
 * 邮箱：1050629507@qq.com
 */
public class SharedPreUtils<T> {
    private static final String TAG = SharedPreUtils.class.getSimpleName();
    private SharedPreferences sharedPreferences;

    private static Context mContext;
    /**
     * 保存在手机里面的名字
     */
    private static String DEFAULT_FILE_NAME = "LZHS_" + TAG;
    /**
     * 是否进行对Key 和 Val 加密
     */
    private boolean isEncode = false;

    private SharedPreferences.Editor editor;

    private volatile static SharedPreUtils mInstance;

    /**
     * 获取 SharedPreUtils 实例
     *
     * @return
     */
    public static SharedPreUtils getInstance() {
        if (mInstance == null) synchronized (SharedPreUtils.class) {
            if (mInstance == null)
                mInstance = new SharedPreUtils(mContext, DEFAULT_FILE_NAME);
        }
        return mInstance;
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    private SharedPreUtils(Context context, String FileName) {
        this.mContext = context;
        sharedPreferences = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    /**
     * 保存数据的方法，拿到数据保存数据的基本类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param object
     */
    public SharedPreUtils put(String key, T object) {
        if (object instanceof Set) {
            isEncode = false;
            editor.putStringSet(key, (Set<String>) object);
        }
        if (isEncodeVal(key, object)) return this;
        if (object instanceof String)
            editor.putString(key, (String) object);
        else if (object instanceof Integer)
            editor.putInt(key, (Integer) object);
        else if (object instanceof Boolean)
            editor.putBoolean(key, (Boolean) object);
        else if (object instanceof Float)
            editor.putFloat(key, (Float) object);
        else if (object instanceof Long)
            editor.putLong(key, (Long) object);
        else editor.putString(key, JSON.toJSONString(object));
        editor.apply();
        return mInstance;
    }


    /**
     * 获取保存数据的方法，我们根据默认值的到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key_         键的值
     * @param defaultValue 默认值
     * @return
     */
    public Object get(String key_, Object defaultValue) {
        if (!contains(key_)) return defaultValue;
        String key = isEncode ? Encode3DES.encode3DES(key_) : key_;
        String val = isEncode ? sharedPreferences.getString(key, (String) defaultValue) : "";
        if (defaultValue instanceof String)
            return isEncode ? Encode3DES.decode3DES(val) : sharedPreferences.getString(key, (String) defaultValue);
        else if (defaultValue instanceof Integer) {
            if (isEncode) {
                String valInt = sharedPreferences.getString(key, (String) defaultValue);
                valInt = Encode3DES.decode3DES(valInt);
                return Integer.parseInt(valInt);
            }
            return sharedPreferences.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            if (isEncode) {
                String valBoolean = sharedPreferences.getString(key, "false");
                valBoolean = Encode3DES.decode3DES(valBoolean);
                return Boolean.parseBoolean(valBoolean);
            }
            return sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Float) {
            if (isEncode) {
                String valFloat = sharedPreferences.getString(key, "-1");
                valFloat = Encode3DES.decode3DES(valFloat);
                return Float.parseFloat(valFloat);
            }
            return sharedPreferences.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            if (isEncode) {
                String valLong = sharedPreferences.getString(key, "-1");
                valLong = Encode3DES.decode3DES(valLong);
                return Long.parseLong(valLong);
            }
            return sharedPreferences.getLong(key, (Long) defaultValue);
        } else if (defaultValue instanceof Set) {
            isEncode = false;
            return sharedPreferences.getStringSet(key, (Set<String>) defaultValue);
        } else return sharedPreferences.getString(key, null);
    }

    /**
     * SharedPreferences中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public int getInt(@NonNull String key) {
        return getInt(key, -1);
    }

    /**
     * SharedPreferences中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public int getInt(@NonNull String key, int defaultValue) {
        if (isEncode) {
            String valInt = sharedPreferences.getString(Encode3DES.encode3DES(key), "-1");
            valInt = Encode3DES.decode3DES(valInt);
            return Integer.parseInt(valInt);
        }
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * SharedPreferences中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public long getLong(@NonNull String key) {
        return getLong(key, -1L);
    }

    /**
     * SharedPreferences中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public long getLong(@NonNull String key, long defaultValue) {
        if (isEncode) {
            String valLong = sharedPreferences.getString(Encode3DES.encode3DES(key), "-1");
            valLong = Encode3DES.decode3DES(valLong);
            return Long.parseLong(valLong);
        }
        return sharedPreferences.getLong(key, defaultValue);
    }

    /**
     * SharedPreferences中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public float getFloat(@NonNull String key) {
        return getFloat(key, -1f);
    }

    /**
     * SharedPreferences中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public float getFloat(@NonNull String key, float defaultValue) {
        if (isEncode) {
            String valFloat = sharedPreferences.getString(Encode3DES.encode3DES(key), "-1");
            valFloat = Encode3DES.decode3DES(valFloat);
            return Float.parseFloat(valFloat);
        }
        return sharedPreferences.getFloat(key, defaultValue);
    }

    /**
     * SharedPreferences中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code false}
     */
    public boolean getBoolean(@NonNull String key) {
        return getBoolean(key, false);
    }

    /**
     * SharedPreferences中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        if (isEncode) {
            String valBoolean = sharedPreferences.getString(Encode3DES.encode3DES(key), "false");
            valBoolean = Encode3DES.decode3DES(valBoolean);
            return Boolean.parseBoolean(valBoolean);
        }
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * SharedPreferences中读取StringSet
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code null}
     */
    public Set<String> getStringSet(@NonNull String key) {
        return getStringSet(key, Collections.<String>emptySet());
    }

    /**
     * SharedPreferences中读取StringSet
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public Set<String> getStringSet(@NonNull String key, @NonNull Set<String> defaultValue) {
        isEncode = false;
        return sharedPreferences.getStringSet(key, defaultValue);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        key = isEncode ? Encode3DES.encode3DES(key) : key;
        editor.remove(key);
        editor.apply();
    }

    /**
     * 清除所有的数据
     */
    public void clear() {
        editor.clear();
        editor.apply();
    }

    /**
     * 查询某个key是否存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        boolean isContains = sharedPreferences.contains(key);
        if (!isContains) isContains = sharedPreferences.contains(Encode3DES.encode3DES(key));
        if (!isContains) Log.e(TAG, "你所查询的 " + key + " 值不存在，请检查...");
        return isContains;
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    public String getFileName() {
        return this.DEFAULT_FILE_NAME;
    }

    /**
     * 修改SharedPreferences 存储的文件
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    public SharedPreUtils setFileName(String fileName) throws Exception {
        if (mContext == null) throw new Exception("mContext  为空！");
        DEFAULT_FILE_NAME = fileName;
        sharedPreferences = mContext.getSharedPreferences(this.DEFAULT_FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return mInstance;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }


    public boolean isEncode() {
        return isEncode;

    }

    /**
     * 设置是否加密存储数据
     *
     * @return
     */
    public SharedPreUtils setEncode(boolean encode) {
        isEncode = encode;
        return mInstance;

    }

    /**
     * 将要保存的数据进行加密
     *
     * @param key
     * @param object
     * @return
     */
    private boolean isEncodeVal(String key, Object object) {
        if (isEncode) {
            key = Encode3DES.encode3DES(key);
            String val = Encode3DES.encode3DES(object.toString());
            editor.putString(key, val);
            editor.apply();
            return true;
        }
        return false;
    }

}
