package com.lzhs.library.wedgit.permission.constants;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * android 6.0 之后需要动态申请的权限常量类<br/>
 * google是以权限组进行分类的，一旦组内的某个权限被赋予了，那么这个组的其他权限也将自动被赋予
 * 作者：LZHS<br/>
 * 时间： 2017/2/6 17:35<br/>
 * 邮箱：1050629507@qq.com
 */
@SuppressLint("InlinedApi")
public final class PermissionConstants {
    /**
     * 允许程序获取用户的日历数据<br/><br/>
     * android.permission.READ_CALENDAR  允许程序读取用户的日程信息<br/>
     * android.permission.WRITE_CALENDAR  允许程序写入用户的日程信息<br/>
     */
    public static final String CALENDAR = Manifest.permission_group.CALENDAR;
    /**
     * 允许程序访问摄像头进行拍照<br/>
     * android.permission.CAMERA
     */
    public static final String CAMERA = Manifest.permission_group.CAMERA;
    /**
     * 允许程序读取联系人<br/><br/>
     * android.permission.READ_CONTACTS  允许应用访问联系人通讯录信息<br/>
     * android.permission.WRITE_CONTACTS  允许应用写入联系人通讯录信息<br/>
     * android.permission.GET_ACCOUNTS  允许应用访问GMail账户列表<br/>
     */
    public static final String CONTACTS = Manifest.permission_group.CONTACTS;
    /**
     * 允许程序访问位置<br/>
     * android.permission.ACCESS_FINE_LOCATION  通过GPS芯片接收卫星的定位信息 定位精度达10米以内  <br/>
     * android.permission.ACCESS_COARSE_LOCATION  获得 粗略位置信息数据  <br/>
     */
    public static final String LOCATION = Manifest.permission_group.LOCATION;
    /**
     * 允许程序访问麦克风<br/>
     * android.permission.RECORD_AUDIO    允许一个应用程序录音.<br/>
     */
    public static final String MICROPHONE = Manifest.permission_group.MICROPHONE;
    /**
     * 允许程序访问电话信息权限组<br/><br/>
     * android.permission.READ_PHONE_STATE  访问电话状态<br/>
     * android.permission.CALL_PHONE  允许程序从非系统拨号器里输入电话号码<br/>
     * android.permission.READ_CALL_LOG  <br/>
     * android.permission.WRITE_CALL_LOG  <br/>
     * com.android.voicemail.permission.ADD_VOICEMAIL  允许应用程序添加系统中的语音邮件<br/>
     * android.permission.USE_SIP  允许程序使用SIP视频服务<br/>
     * android.permission.PROCESS_OUTGOING_CALLS  允许应用程序监视、修改、忽略拨出的电话<br/>
     */
    public static final String PHONE = Manifest.permission_group.PHONE;
    /**
     * 允许程序访问手机传感器<br/>
     * android.permission.BODY_SENSORS  <br/>
     */
    public static final String SENSORS = Manifest.permission_group.SENSORS;
    /**
     * 允许程序访问手机短信<br/>
     * android.permission.SEND_SMS   发送短信<br/>
     * android.permission.RECEIVE_SMS    接收短信<br/>
     * android.permission.READ_SMS    读取短信内容<br/>
     * android.permission.RECEIVE_WAP_PUSH    接收WAP PUSH信息<br/>
     * android.permission.RECEIVE_MMS   接收彩信<br/>
     * android.permission.READ_CELL_BROADCASTS   获取小区广播 <br/>
     */
    public static final String SMS = Manifest.permission_group.SMS;
    /**
     * 允许程序访问存储<br/><br/>
     * android.permission.READ_EXTERNAL_STORAGE   读取内存卡<br/>
     * android.permission.WRITE_EXTERNAL_STORAGE   写内存卡 <br/>
     */
    public static final String STORAGE = Manifest.permission_group.STORAGE;

    private static final String[] GROUP_CALENDAR = {
            permission.READ_CALENDAR, permission.WRITE_CALENDAR
    };
    private static final String[] GROUP_CAMERA = {
            permission.CAMERA
    };
    private static final String[] GROUP_CONTACTS = {
            permission.READ_CONTACTS, permission.WRITE_CONTACTS, permission.GET_ACCOUNTS
    };
    private static final String[] GROUP_LOCATION = {
            permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION
    };
    private static final String[] GROUP_MICROPHONE = {
            permission.RECORD_AUDIO
    };
    private static final String[] GROUP_PHONE = {
            permission.READ_PHONE_STATE, permission.READ_PHONE_NUMBERS, permission.CALL_PHONE,
            permission.ANSWER_PHONE_CALLS, permission.READ_CALL_LOG, permission.WRITE_CALL_LOG,
            permission.ADD_VOICEMAIL, permission.USE_SIP, permission.PROCESS_OUTGOING_CALLS
    };
    private static final String[] GROUP_SENSORS = {
            permission.BODY_SENSORS
    };
    private static final String[] GROUP_SMS = {
            permission.SEND_SMS, permission.RECEIVE_SMS, permission.READ_SMS,
            permission.RECEIVE_WAP_PUSH, permission.RECEIVE_MMS,
    };
    private static final String[] GROUP_STORAGE = {
            permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE
    };

    @StringDef({CALENDAR, CAMERA, CONTACTS, LOCATION, MICROPHONE, PHONE, SENSORS, SMS, STORAGE,})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Permission {
    }

    public static String[] getPermissions(@Permission final String permission) {
        switch (permission) {
            case CALENDAR:
                return GROUP_CALENDAR;
            case CAMERA:
                return GROUP_CAMERA;
            case CONTACTS:
                return GROUP_CONTACTS;
            case LOCATION:
                return GROUP_LOCATION;
            case MICROPHONE:
                return GROUP_MICROPHONE;
            case PHONE:
                return GROUP_PHONE;
            case SENSORS:
                return GROUP_SENSORS;
            case SMS:
                return GROUP_SMS;
            case STORAGE:
                return GROUP_STORAGE;
        }
        return new String[]{permission};
    }
}
