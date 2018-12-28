package com.lzhs.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2018/12/27 : 5:30 PM<br/>
 */
public class LogUtil {
    public static final int V = android.util.Log.VERBOSE;
    public static final int D = android.util.Log.DEBUG;
    public static final int I = android.util.Log.INFO;
    public static final int W = android.util.Log.WARN;
    public static final int E = android.util.Log.ERROR;
    public static final int A = android.util.Log.ASSERT;

    @IntDef({V, D, I, W, E, A})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {
    }

    private static final char[] T = new char[]{'V', 'D', 'I', 'W', 'E', 'A'};

    private static final int FILE = 0x10;
    private static final int JSON = 0x20;
    private static final int XML = 0x30;

    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final String TOP_CORNER = "┌";
    private static final String MIDDLE_CORNER = "├";
    private static final String LEFT_BORDER = "│ ";
    private static final String BOTTOM_CORNER = "└";
    private static final String SIDE_DIVIDER = "────────────────────────────────────────────────────────────────────────────────────────────";
    private static final String MIDDLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final String TOP_BORDER = TOP_CORNER + SIDE_DIVIDER + SIDE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + MIDDLE_DIVIDER + MIDDLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_CORNER + SIDE_DIVIDER + SIDE_DIVIDER;
    private static final int MAX_LEN = 3000;
    @SuppressLint("SimpleDateFormat")
    private static final Format FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS ");
    private static final String NOTHING = "log nothing";
    private static final String NULL = "null";
    private static final String ARGS = "args";
    private static final String PLACEHOLDER = " ";
    private static Context mContext;
    private static Config mConfig;
    private static ExecutorService sExecutor;

    private LogUtil() {
        throw new UnsupportedOperationException("该类为静态类，不提供new 的方式来操作该类");
    }

    public static Config init(@NonNull Context mContext) {
        LogUtil.mContext = mContext;
        if (mConfig == null)
            mConfig = new Config();
        return mConfig;
    }

    public static Config getConfig() {
        if (mConfig == null) throw new NullPointerException("请先调用 init 方法");
        return mConfig;
    }

    public static void v(final Object... contents) {
        log(V, mConfig.mGlobalTag, contents);
    }

    public static void vTag(final String tag, final Object... contents) {
        log(V, tag, contents);
    }

    public static void d(final Object... contents) {
        log(D, mConfig.mGlobalTag, contents);
    }

    public static void dTag(final String tag, @NonNull final Object... contents) {
        log(D, tag, contents);
    }

    public static void i(final Object... contents) {
        log(I, mConfig.mGlobalTag, contents);
    }

    public static void iTag(final String tag, final Object... contents) {
        log(I, tag, contents);
    }

    public static void w(final Object... contents) {
        log(W, mConfig.mGlobalTag, contents);
    }

    public static void wTag(final String tag, final Object... contents) {
        log(W, tag, contents);
    }

    public static void e(final Object... contents) {
        log(E, mConfig.mGlobalTag, contents);
    }

    public static void eTag(final String tag, final Object... contents) {
        log(E, tag, contents);
    }

    public static void a(final Object... contents) {
        log(A, mConfig.mGlobalTag, contents);
    }

    public static void aTag(final String tag, final Object... contents) {
        log(A, tag, contents);
    }

    public static void file(final Object content) {
        log(FILE | D, mConfig.mGlobalTag, content);
    }

    public static void file(@TYPE final int type, final Object content) {
        log(FILE | type, mConfig.mGlobalTag, content);
    }

    public static void file(final String tag, final Object content) {
        log(FILE | D, tag, content);
    }

    public static void file(@TYPE final int type, final String tag, final Object content) {
        log(FILE | type, tag, content);
    }

    public static void json(final String content) {
        log(JSON | D, mConfig.mGlobalTag, content);
    }

    public static void json(@TYPE final int type, final String content) {
        log(JSON | type, mConfig.mGlobalTag, content);
    }

    public static void json(final String tag, final String content) {
        log(JSON | D, tag, content);
    }

    public static void json(@TYPE final int type, final String tag, final String content) {
        log(JSON | type, tag, content);
    }

    public static void xml(final String content) {
        log(XML | D, mConfig.mGlobalTag, content);
    }

    public static void xml(@TYPE final int type, final String content) {
        log(XML | type, mConfig.mGlobalTag, content);
    }

    public static void xml(final String tag, final String content) {
        log(XML | D, tag, content);
    }

    public static void xml(@TYPE final int type, final String tag, final String content) {
        log(XML | type, tag, content);
    }

    public static void log(final int type, final String tag, final Object... contents) {
        if (!mConfig.mLogSwitch || (!mConfig.mLogConsoleSwitch && !mConfig.mLogFileSwitch))
            return;
        int type_low = type & 0x0f, type_high = type & 0xf0;
        if (type_low < mConfig.mConsoleFilter && type_low < mConfig.mFileFilter) return;
        final TagHead tagHead = processTagAndHead(tag);
        String body = processBody(type_high, contents);
        if (mConfig.mLogConsoleSwitch && type_low >= mConfig.mConsoleFilter && type_high != FILE) {
            printConsole(type_low, tagHead.tag, tagHead.consoleHead, body);
        }
        if ((mConfig.mLogFileSwitch || type_high == FILE) && type_low >= mConfig.mFileFilter) {
            printFile(type_low, tagHead.tag, tagHead.fileHead + body);
        }
    }

    private static TagHead processTagAndHead(String tag) {
        if (!mConfig.mTagIsSpace && !mConfig.mLogHeadSwitch) {
            tag = mConfig.mGlobalTag;
        } else {
            final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            final int stackIndex = 3 + mConfig.mStackOffset;
            if (stackIndex >= stackTrace.length) {
                StackTraceElement targetElement = stackTrace[3];
                final String fileName = getFileName(targetElement);
                if (mConfig.mTagIsSpace && isSpace(tag)) {
                    int index = fileName.indexOf('.');// Use proguard may not find '.'.
                    tag = index == -1 ? fileName : fileName.substring(0, index);
                }
                return new TagHead(tag, null, " : ");
            }
            StackTraceElement targetElement = stackTrace[stackIndex];
            final String fileName = getFileName(targetElement);
            if (mConfig.mTagIsSpace && isSpace(tag)) {
                int index = fileName.indexOf('.');// Use proguard may not find '.'.
                tag = index == -1 ? fileName : fileName.substring(0, index);
            }
            if (mConfig.mLogHeadSwitch) {
                String tName = Thread.currentThread().getName();
                final String head = new Formatter()
                        .format("Thread: %s ,  %s.%s(%s:%d)",
                                tName,
                                targetElement.getClassName(),
                                targetElement.getMethodName(),
                                fileName,
                                targetElement.getLineNumber())
                        .toString();
                final String fileHead = " [ " + head + " ] : ";
                if (mConfig.mStackDeep <= 1) {
                    return new TagHead(tag, new String[]{head}, fileHead);
                } else {
                    final String[] consoleHead =
                            new String[Math.min(
                                    mConfig.mStackDeep,
                                    stackTrace.length - stackIndex
                            )];
                    consoleHead[0] = head;
                    int spaceLen = tName.length() + 2;
                    String space = new Formatter().format("%" + spaceLen + "s", "").toString();
                    for (int i = 1, len = consoleHead.length; i < len; ++i) {
                        targetElement = stackTrace[i + stackIndex];
                        consoleHead[i] = new Formatter()
                                .format("%s%s.%s(%s:%d)",
                                        space,
                                        targetElement.getClassName(),
                                        targetElement.getMethodName(),
                                        getFileName(targetElement),
                                        targetElement.getLineNumber())
                                .toString();
                    }
                    return new TagHead(tag, consoleHead, fileHead);
                }
            }
        }
        return new TagHead(tag, null, ": ");
    }

    private static String getFileName(final StackTraceElement targetElement) {
        String fileName = targetElement.getFileName();
        if (fileName != null) return fileName;
        // If name of file is null, should add
        // "-keepattributes SourceFile,LineNumberTable" in proguard file.
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1];
        }
        int index = className.indexOf('$');
        if (index != -1) {
            className = className.substring(0, index);
        }
        return className + ".java";
    }

    private static String processBody(final int type, final Object... contents) {
        String body = NULL;
        if (contents != null) {
            if (contents.length == 1) {
                Object object = contents[0];
                if (object != null) body = createContent(object);
                if (type == JSON) body = formatJson(body);
                else if (type == XML) body = formatXml(body);

            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0, len = contents.length; i < len; ++i) {
                    Object content = contents[i];
                    sb.append("")
                            .append("indext = [" + i + "]")
                            .append("       ")
                            .append(content == null ? NULL : createContent(content))
                            .append(LINE_SEP)
                            .append("\n");
                }
                body = sb.toString();
            }
        }
        return body.length() == 0 ? NOTHING : body;
    }

    private static String createContent(Object msg) {
        try {
            if (msg instanceof List) return createList((List) msg);
            if (msg instanceof ArrayList) return createArrayList((ArrayList) msg);
            else if (msg instanceof List) return createList((List) msg);
            else if (msg instanceof HashMap) return createHashMap((HashMap) msg);
            else if (msg instanceof SparseArray) return createSparseArray((SparseArray) msg);
            else if (isPrimitive(msg)) return msg.toString();
            else if (msg.getClass().isArray()) return createArray((Object[]) msg);
            else if (msg instanceof ConcurrentHashMap)
                return createConcurrentHashMap((ConcurrentHashMap<String, Object>) msg);
            else return "\n" + com.alibaba.fastjson.JSON.toJSONString(msg, true);
        } catch (Exception e) {
            e.printStackTrace();
            return msg.toString();
        }

    }


    private static boolean isPrimitive(Object obj) {
        if (obj.getClass().getName().equals("java.lang.Boolean") ||
                obj.getClass().getName().equals("java.lang.Character") ||
                obj.getClass().getName().equals("java.lang.Byte") ||
                obj.getClass().getName().equals("java.lang.Short") ||
                obj.getClass().getName().equals("java.lang.Integer") ||
                obj.getClass().getName().equals("java.lang.Long") ||
                obj.getClass().getName().equals("java.lang.Float") ||
                obj.getClass().getName().equals("java.lang.Double") ||
                obj.getClass().getName().equals("java.lang.Void") ||
                obj.getClass().getName().equals("java.lang.String"))
            return true;
        return false;
    }


    private static String createConcurrentHashMap(ConcurrentHashMap<String, Object> msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("数组大小 : " + msg.size() + "\n      value =  { \n");
        for (Map.Entry<String, Object> e : msg.entrySet())
            sb.append("             Key = " + e.getKey() + " ; value = " + e.getValue() + " ; \n");
        sb.append("             }");
        return sb.toString();
    }

    private static String createArray(Object[] msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("数组大小 : " + msg.length + "\n      value =  { \n");
        for (int i = 0; i < msg.length; i++)
            sb.append("             Key = " + i + " ; value = " + msg[i].toString() + " ;\n");
        sb.append("             }");
        return sb.toString();
    }

    private static String createSparseArray(SparseArray msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("数组大小 : " + msg.size() + "\n      value =  {\n");
        for (int i = 0; i < msg.size(); i++)
            sb.append("             Key = " + msg.keyAt(i) + " ; value = " + msg.get(i).toString() + " ;\n");
        sb.append("             }");
        return sb.toString();
    }

    private static String createHashMap(HashMap msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("数组大小 : " + msg.size() + "\n      value = { \n");
        Set set = msg.entrySet();
        for (Iterator iter = set.iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iter.next();
            sb.append("             Key = " + entry.getKey().toString() + " ; value = " + entry.getValue() + " ;\n");
        }
        sb.append("             }");
        return sb.toString();
    }

    private static String createArrayList(ArrayList msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("数组大小 : " + msg.size() + "\n      value = { \n");
        for (int i = 0; i < msg.size(); i++)
            sb.append("             Key = " + i + " ; value = " + msg.get(i).toString() + "\n");
        sb.append("             }");
        return sb.toString();
    }


    private static String createList(List msg) {
        StringBuilder sb = new StringBuilder();
        sb.append("数组大小 : " + msg.size() + "\n      value = { \n");
        for (int i = 0; i < msg.size(); i++)
            sb.append("             Key = " + i + " ; value = " + msg.get(i).toString() + " ;  \n");
        sb.append("             }");
        return sb.toString();
    }

    private static String formatJson(String json) {
        try {
            if (json.startsWith("{")) {
                json = new JSONObject(json).toString(4);
            } else if (json.startsWith("[")) {
                json = new JSONArray(json).toString(4);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    private static String formatXml(String xml) {
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(xmlInput, xmlOutput);
            xml = xmlOutput.getWriter().toString().replaceFirst(">", ">" + LINE_SEP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xml;
    }

    private static void printConsole(final int type,
                                     final String tag,
                                     final String[] head,
                                     final String msg) {
        if (mConfig.mSingleTagSwitch) {
            StringBuilder sb = new StringBuilder();
            sb.append(PLACEHOLDER).append(LINE_SEP);
            if (mConfig.mLogBorderSwitch) {
                sb.append(TOP_BORDER).append(LINE_SEP);
                if (head != null) {
                    for (String aHead : head) {
                        sb.append(LEFT_BORDER).append(aHead).append(LINE_SEP);
                    }
                    sb.append(MIDDLE_BORDER).append(LINE_SEP);
                }
                for (String line : msg.split(LINE_SEP)) {
                    sb.append(LEFT_BORDER).append(line).append(LINE_SEP);
                }
                sb.append(BOTTOM_BORDER);
            } else {
                if (head != null) {
                    for (String aHead : head) {
                        sb.append(aHead).append(LINE_SEP);
                    }
                }
                sb.append(msg);
            }
            printMsgSingleTag(type, tag, sb.toString());
        } else {
            printBorder(type, tag, true);
            printHead(type, tag, head);
            printMsg(type, tag, msg);
            printBorder(type, tag, false);
        }
    }

    private static void printBorder(final int type, final String tag, boolean isTop) {
        if (mConfig.mLogBorderSwitch) {
            android.util.Log.println(type, tag, isTop ? TOP_BORDER : BOTTOM_BORDER);
        }
    }

    private static void printHead(final int type, final String tag, final String[] head) {
        if (head != null) {
            for (String aHead : head) {
                android.util.Log.println(type, tag, mConfig.mLogBorderSwitch ? LEFT_BORDER + aHead : aHead);
            }
            if (mConfig.mLogBorderSwitch) android.util.Log.println(type, tag, MIDDLE_BORDER);
        }
    }

    private static void printMsg(final int type, final String tag, final String msg) {
        int len = msg.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                printSubMsg(type, tag, msg.substring(index, index + MAX_LEN));
                index += MAX_LEN;
            }
            if (index != len) {
                printSubMsg(type, tag, msg.substring(index, len));
            }
        } else {
            printSubMsg(type, tag, msg);
        }
    }

    private static void printMsgSingleTag(final int type, final String tag, final String msg) {
        int len = msg.length();
        int countOfSub = len / MAX_LEN;
        if (countOfSub > 0) {
            if (mConfig.mLogBorderSwitch) {
                android.util.Log.println(type, tag, msg.substring(0, MAX_LEN) + LINE_SEP + BOTTOM_BORDER);
                int index = MAX_LEN;
                for (int i = 1; i < countOfSub; i++) {
                    android.util.Log.println(type, tag, PLACEHOLDER + LINE_SEP + TOP_BORDER + LINE_SEP
                            + LEFT_BORDER + msg.substring(index, index + MAX_LEN)
                            + LINE_SEP + BOTTOM_BORDER);
                    index += MAX_LEN;
                }
                if (index != len) {
                    android.util.Log.println(type, tag, PLACEHOLDER + LINE_SEP + TOP_BORDER + LINE_SEP
                            + LEFT_BORDER + msg.substring(index, len));
                }
            } else {
                int index = 0;
                for (int i = 0; i < countOfSub; i++) {
                    android.util.Log.println(type, tag, msg.substring(index, index + MAX_LEN));
                    index += MAX_LEN;
                }
                if (index != len) {
                    android.util.Log.println(type, tag, msg.substring(index, len));
                }
            }
        } else {
            android.util.Log.println(type, tag, msg);
        }
    }

    private static void printSubMsg(final int type, final String tag, final String msg) {
        if (!mConfig.mLogBorderSwitch) {
            android.util.Log.println(type, tag, msg);
            return;
        }
        StringBuilder sb = new StringBuilder();
        String[] lines = msg.split(LINE_SEP);
        for (String line : lines) {
            android.util.Log.println(type, tag, LEFT_BORDER + line);
        }
    }

    private static void printFile(final int type, final String tag, final String msg) {
        Date now = new Date(System.currentTimeMillis());
        String format = FORMAT.format(now);
        String date = format.substring(0, 5);
        String time = format.substring(6);
        final String fullPath =
                (mConfig.mDir == null ? mConfig.mDefaultDir : mConfig.mDir)
                        + mConfig.mFilePrefix + "-" + date + ".txt";
        if (!createOrExistsFile(fullPath)) {
            Log.e("LogUtils", "create " + fullPath + " failed!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(time)
                .append(T[type - V])
                .append("/")
                .append(tag)
                .append(msg)
                .append(LINE_SEP);
        final String content = sb.toString();
        inputFile(content, fullPath);
    }

    private static boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            boolean isCreate = file.createNewFile();
            if (isCreate) printDeviceInfo(filePath);
            return isCreate;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void printDeviceInfo(final String filePath) {
        String versionName = "";
        int versionCode = 0;
        try {
            PackageInfo pi = mContext
                    .getPackageManager()
                    .getPackageInfo(mContext.getPackageName(), 0);
            if (pi != null) {
                versionName = pi.versionName;
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String time = filePath.substring(filePath.length() - 9, filePath.length() - 4);
        final String head = "************* Log Head ****************" +
                "\nDate of Log        : " + time +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +
                "\nDevice Model       : " + Build.MODEL +
                "\nAndroid Version    : " + Build.VERSION.RELEASE +
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +
                "\nApp VersionName    : " + versionName +
                "\nApp VersionCode    : " + versionCode +
                "\n************* Log Head ****************\n\n";
        inputFile(head, filePath);
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static void inputFile(final String input, final String filePath) {
        if (sExecutor == null) {
            sExecutor = Executors.newSingleThreadExecutor();
        }
        Future<Boolean> submit = sExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(filePath, true));
                    bw.write(input);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        try {
            if (submit.get()) return;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.e("LogUtils", "log to " + filePath + " failed!");
    }


    public static class Config {
        private String mDefaultDir;// log默认存储目录
        private String mDir;       // log存储目录
        private String mFilePrefix = "util_lzhs";//log 文件前缀
        private boolean mLogSwitch = true;  // log 总开关
        private boolean mLogConsoleSwitch = true;  // log 控制台开关
        private String mGlobalTag = null;  // log 全局 tag
        private boolean mTagIsSpace = true;  // log tag是否为空
        private boolean mLogHeadSwitch = true;  // log 头部信息开关
        private boolean mLogFileSwitch = false; // log 文件开关
        private boolean mLogBorderSwitch = true;  // log 边框开关
        private boolean mSingleTagSwitch = true;  // log 单一 tag 开关（为美化 AS 3.1 的 Logcat）
        private int mConsoleFilter = V;     // log 控制台过滤器
        private int mFileFilter = V;     // log 文件过滤器
        private int mStackDeep = 1;     // log 栈深度
        private int mStackOffset = 0;     // log 栈偏移

        private Config() {
            if (mDefaultDir != null) return;
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && mContext.getExternalCacheDir() != null)
                mDefaultDir = mContext.getExternalCacheDir() + FILE_SEP + "log" + FILE_SEP;
            else {
                mDefaultDir = mContext.getCacheDir() + FILE_SEP + "log" + FILE_SEP;
            }
        }

        public Config setLogSwitch(final boolean logSwitch) {
            mLogSwitch = logSwitch;
            return this;
        }

        public Config setConsoleSwitch(final boolean consoleSwitch) {
            mLogConsoleSwitch = consoleSwitch;
            return this;
        }

        public Config setGlobalTag(final String tag) {
            if (isSpace(tag)) {
                mGlobalTag = "";
                mTagIsSpace = true;
            } else {
                mGlobalTag = tag;
                mTagIsSpace = false;
            }
            return this;
        }

        public Config setLogHeadSwitch(final boolean logHeadSwitch) {
            mLogHeadSwitch = logHeadSwitch;
            return this;
        }

        public Config setLogFileSwitch(final boolean logFileSwitch) {
            mLogFileSwitch = logFileSwitch;
            return this;
        }

        public Config setDir(final String dir) {
            if (isSpace(dir)) {
                mDir = null;
            } else {
                mDir = dir.endsWith(FILE_SEP) ? dir : dir + FILE_SEP;
            }
            return this;
        }

        public Config setDir(final File dir) {
            mDir = dir == null ? null : dir.getAbsolutePath() + FILE_SEP;
            return this;
        }

        public Config setFilePrefix(final String filePrefix) {
            if (isSpace(filePrefix)) {
                mFilePrefix = "util";
            } else {
                mFilePrefix = filePrefix;
            }
            return this;
        }

        public Config setBorderSwitch(final boolean borderSwitch) {
            mLogBorderSwitch = borderSwitch;
            return this;
        }

        public Config setSingleTagSwitch(final boolean singleTagSwitch) {
            mSingleTagSwitch = singleTagSwitch;
            return this;
        }

        public Config setConsoleFilter(@TYPE final int consoleFilter) {
            mConsoleFilter = consoleFilter;
            return this;
        }

        public Config setFileFilter(@TYPE final int fileFilter) {
            mFileFilter = fileFilter;
            return this;
        }

        public Config setStackDeep(@IntRange(from = 1) final int stackDeep) {
            mStackDeep = stackDeep;
            return this;
        }

        public Config setStackOffset(@IntRange(from = 0) final int stackOffset) {
            mStackOffset = stackOffset;
            return this;
        }

        @Override
        public String toString() {
            return "switch: " + mLogSwitch
                    + LINE_SEP + "console: " + mLogConsoleSwitch
                    + LINE_SEP + "tag: " + (mTagIsSpace ? "null" : mGlobalTag)
                    + LINE_SEP + "head: " + mLogHeadSwitch
                    + LINE_SEP + "file: " + mLogFileSwitch
                    + LINE_SEP + "dir: " + (mDir == null ? mDefaultDir : mDir)
                    + LINE_SEP + "filePrefix: " + mFilePrefix
                    + LINE_SEP + "border: " + mLogBorderSwitch
                    + LINE_SEP + "singleTag: " + mSingleTagSwitch
                    + LINE_SEP + "consoleFilter: " + T[mConsoleFilter - V]
                    + LINE_SEP + "fileFilter: " + T[mFileFilter - V]
                    + LINE_SEP + "stackDeep: " + mStackDeep
                    + LINE_SEP + "mStackOffset: " + mStackOffset;
        }
    }

    private static class TagHead {
        String tag;
        String[] consoleHead;
        String fileHead;

        TagHead(String tag, String[] consoleHead, String fileHead) {
            this.tag = tag;
            this.consoleHead = consoleHead;
            this.fileHead = fileHead;
        }
    }
}