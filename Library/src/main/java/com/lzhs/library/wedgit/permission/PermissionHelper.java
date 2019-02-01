package com.lzhs.library.wedgit.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;


import com.lzhs.library.R;
import com.lzhs.library.Utils;
import com.lzhs.library.utils.LogUtil;
import com.lzhs.library.wedgit.permission.constants.PermissionConstants;

import java.util.List;


/**
 * 动态申请的权限帮助类<br/>
 * 作者：LZHS<br/>
 * 时间： 2018/4/26 10:57<br/>
 * 邮箱：1050629507@qq.com
 */
public class PermissionHelper {

    /**
     * 允许程序获取用户的日历数据<br/><br/>
     * android.permission.READ_CALENDAR  允许程序读取用户的日程信息<br/>
     * android.permission.WRITE_CALENDAR  允许程序写入用户的日程信息<br/>
     *
     * @param listener
     */
    public static void requestCalendar(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.CALENDAR);
    }


    /**
     * 允许程序获取用户的日历数据<br/><br/>
     * android.permission.READ_CALENDAR  允许程序读取用户的日程信息<br/>
     * android.permission.WRITE_CALENDAR  允许程序写入用户的日程信息<br/>
     *
     * @param listener
     * @param deniedListener
     */
    public static void requestCalendar(final OnPermissionGrantedListener listener
            , final OnPermissionDeniedListener deniedListener) {
        request(listener, deniedListener, PermissionConstants.CALENDAR);
    }

    /**
     * 允许程序访问摄像头进行拍照<br/>
     * android.permission.CAMERA
     *
     * @param listener
     */
    public static void requestCamera(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.CAMERA);
    }

    /**
     * 允许程序访问摄像头进行拍照<br/>
     * android.permission.CAMERA
     *
     * @param listener
     * @param deniedListener
     */
    public static void requestCamera(final OnPermissionGrantedListener listener
            , final OnPermissionDeniedListener deniedListener) {
        request(listener, deniedListener, PermissionConstants.CAMERA);
    }

    /**
     * 允许程序读取联系<br/>
     * android.permission.READ_CONTACTS  允许应用访问联系人通讯录信息<br/>
     * android.permission.WRITE_CONTACTS  允许应用写入联系人通讯录信息<br/>
     * android.permission.GET_ACCOUNTS  允许应用访问GMail账户列表<br/>
     *
     * @param listener
     */
    public static void requestContacts(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.CONTACTS);
    }

    /**
     * 允许程序读取联系<br/>
     * android.permission.READ_CONTACTS  允许应用访问联系人通讯录信息<br/>
     * android.permission.WRITE_CONTACTS  允许应用写入联系人通讯录信息<br/>
     * android.permission.GET_ACCOUNTS  允许应用访问GMail账户列表<br/>
     *
     * @param listener
     * @param deniedListener
     */
    public static void requestContacts(final OnPermissionGrantedListener listener
            , final OnPermissionDeniedListener deniedListener) {
        request(listener, deniedListener, PermissionConstants.CONTACTS);
    }

    /**
     * 允许程序访问位置<br/>
     * android.permission.ACCESS_FINE_LOCATION  通过GPS芯片接收卫星的定位信息 定位精度达10米以内  <br/>
     * android.permission.ACCESS_COARSE_LOCATION  获得 粗略位置信息数据  <br/>
     *
     * @param listener
     */
    public static void requestLocation(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.LOCATION);
    }

    /**
     * 允许程序访问位置<br/>
     * android.permission.ACCESS_FINE_LOCATION  通过GPS芯片接收卫星的定位信息 定位精度达10米以内  <br/>
     * android.permission.ACCESS_COARSE_LOCATION  获得 粗略位置信息数据  <br/>
     *
     * @param listener
     * @param deniedListener
     */
    public static void requestLocation(final OnPermissionGrantedListener listener
            , final OnPermissionDeniedListener deniedListener) {
        request(listener, deniedListener, PermissionConstants.LOCATION);
    }

    /**
     * 允许程序访问麦克风<br/>
     * android.permission.RECORD_AUDIO    允许一个应用程序录音.<br/>
     *
     * @param listener
     */
    public static void requestMicrophone(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.MICROPHONE);
    }

    /**
     * 允许程序访问麦克风<br/>
     * android.permission.RECORD_AUDIO    允许一个应用程序录音.<br/>
     *
     * @param listener
     * @param deniedListener
     */
    public static void requestMicrophone(final OnPermissionGrantedListener listener
            , final OnPermissionDeniedListener deniedListener) {
        request(listener, deniedListener, PermissionConstants.MICROPHONE);
    }

    /**
     * 允许程序访问电话信息权限组
     *
     * @param listener
     */
    public static void requestPhone(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.PHONE);
    }

    /**
     * 允许程序访问电话信息权限组
     *
     * @param grantedListener
     * @param deniedListener
     */
    public static void requestPhone(final OnPermissionGrantedListener grantedListener,
                                    final OnPermissionDeniedListener deniedListener) {
        request(grantedListener, deniedListener, PermissionConstants.PHONE);
    }


    /**
     * 允许程序访问手机传感器<br/>
     * android.permission.BODY_SENSORS  <br/>
     *
     * @param listener
     */
    public static void requestSensors(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.SENSORS);
    }

    /**
     * 允许程序访问手机传感器<br/>
     * android.permission.BODY_SENSORS  <br/>
     *
     * @param listener
     * @param deniedListener
     */
    public static void requestSensors(final OnPermissionGrantedListener listener
            , final OnPermissionDeniedListener deniedListener) {
        request(listener, deniedListener, PermissionConstants.SENSORS);
    }

    /**
     * 允许程序访问手机短信<br/>
     * android.permission.SEND_SMS   发送短信<br/>
     * android.permission.RECEIVE_SMS    接收短信<br/>
     * android.permission.READ_SMS    读取短信内容<br/>
     * android.permission.RECEIVE_WAP_PUSH    接收WAP PUSH信息<br/>
     * android.permission.RECEIVE_MMS   接收彩信<br/>
     * android.permission.READ_CELL_BROADCASTS   获取小区广播 <br/>
     *
     * @param listener
     */
    public static void requestSms(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.SMS);
    }

    /**
     * 允许程序访问手机短信<br/>
     * android.permission.SEND_SMS   发送短信<br/>
     * android.permission.RECEIVE_SMS    接收短信<br/>
     * android.permission.READ_SMS    读取短信内容<br/>
     * android.permission.RECEIVE_WAP_PUSH    接收WAP PUSH信息<br/>
     * android.permission.RECEIVE_MMS   接收彩信<br/>
     * android.permission.READ_CELL_BROADCASTS   获取小区广播 <br/>
     *
     * @param listener
     * @param deniedListener
     */
    public static void requestSms(final OnPermissionGrantedListener listener
            , final OnPermissionDeniedListener deniedListener) {
        request(listener, deniedListener, PermissionConstants.SMS);
    }

    /**
     * 允许程序访问存储<br/>
     * android.permission.READ_EXTERNAL_STORAGE   读取内存卡<br/>
     * android.permission.WRITE_EXTERNAL_STORAGE   写内存卡 <br/>
     *
     * @param listener
     */
    public static void requestStorage(final OnPermissionGrantedListener listener) {
        request(listener, PermissionConstants.STORAGE);
    }

    /**
     * 允许程序访问存储<br/>
     * android.permission.READ_EXTERNAL_STORAGE   读取内存卡<br/>
     * android.permission.WRITE_EXTERNAL_STORAGE   写内存卡 <br/>
     *
     * @param listener
     * @param deniedListener
     */
    public static void requestStorage(final OnPermissionGrantedListener listener
            , final OnPermissionDeniedListener deniedListener) {
        request(listener, deniedListener, PermissionConstants.STORAGE);
    }


    private static void request(final OnPermissionGrantedListener grantedListener,
                                final @PermissionConstants.Permission String... permissions) {
        request(grantedListener, null, permissions);
    }

    private static void request(final OnPermissionGrantedListener grantedListener,
                                final OnPermissionDeniedListener deniedListener,
                                final @PermissionConstants.Permission String... permissions) {
        PermissionUtils.permission(permissions)
                .rationale(shouldRequest -> showRationaleDialog(shouldRequest))
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        if (grantedListener != null)
                            grantedListener.onPermissionGranted();
                        LogUtil.d(permissionsGranted);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty())
                            showOpenAppSettingDialog();
                        if (deniedListener != null)
                            deniedListener.onPermissionDenied();
                        LogUtil.d(permissionsDeniedForever, permissionsDenied);
                    }
                })
                .request();
    }

    public static void showRationaleDialog(final PermissionUtils.OnRationaleListener.ShouldRequest shouldRequest) {
        Activity topActivity = Utils.getTopActivity();
        if (topActivity == null) return;
        new AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_rationale_message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> shouldRequest.again(true))
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> shouldRequest.again(false))
                .setCancelable(false)
                .create()
                .show();

    }

   public static void showOpenAppSettingDialog() {
        Activity topActivity = Utils.getTopActivity();
        if (topActivity == null) return;
        new AlertDialog.Builder(topActivity)
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> PermissionUtils.launchAppDetailsSettings())
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> {

                })
                .setCancelable(false)
                .create()
                .show();
    }

    public interface OnPermissionGrantedListener {
        void onPermissionGranted();
    }

    public interface OnPermissionDeniedListener {
        void onPermissionDenied();
    }
}