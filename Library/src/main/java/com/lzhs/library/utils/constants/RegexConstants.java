package com.lzhs.library.utils.constants;

/**
 * 时间常量<br/>
 * 作者：LZHS<br/>
 * 时间： 2018/2/6 17:37<br/>
 * 邮箱：1050629507@qq.com
 */
public final class RegexConstants {

    /**
     * 验证手机号（简单）
     */
    public static final String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";
    /**
     * 验证手机号（精确）
     * <p>china mobile: 134(0-8), 135, 136, 137, 138, 139, 147, 150, 151, 152, 157, 158, 159, 178, 182, 183, 184, 187, 188, 198</p>
     * <p>china unicom: 130, 131, 132, 145, 155, 156, 166, 171, 175, 176, 185, 186</p>
     * <p>china telecom: 133, 153, 173, 177, 180, 181, 189, 199</p>
     * <p>global star: 1349</p>
     * <p>virtual operator: 170</p>
     */
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6])|(17[0,1,3,5-8])|(18[0-9])|(19[8,9]))\\d{8}$";
    /**
     * 验证电话号码
     */
    public static final String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}";
    /**
     * 验证身份证号码 15 位
     */
    public static final String REGEX_ID_CARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /**
     * 验证身份证号码 18 位
     */
    public static final String REGEX_ID_CARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
    /**
     * 验证邮箱
     */
    public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * 验证Url
     */
    public static final String REGEX_URL = "[a-zA-z]+://[^\\s]*";
    /**
     * 验证汉字
     */
    public static final String REGEX_ZH = "^[\\u4e00-\\u9fa5]+$";
    /**
     * 验证用户名
     * <p>scope for "a-z", "A-Z", "0-9", "_", "Chinese character"</p>
     * <p>can't end with "_"</p>
     * <p>length is between 6 to 20</p>
     */
    public static final String REGEX_USERNAME = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$";
    /**
     * 验证 yyyy-MM-dd 格式的日期校验，已考虑平闰年
     */
    public static final String REGEX_DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    /**
     * 验证 IP 地址
     */
    public static final String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    ///////////////////////////////////////////////////////////////////////////
    // The following come from http://tool.oschina.net/regex
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Regex of double-byte characters.
     */
    public static final String REGEX_DOUBLE_BYTE_CHAR = "[^\\x00-\\xff]";
    /**
     * Regex of blank line.
     */
    public static final String REGEX_BLANK_LINE = "\\n\\s*\\r";
    /**
     * 验证 QQ 号
     */
    public static final String REGEX_QQ_NUM = "[1-9][0-9]{4,}";
    /**
     * Regex of postal code in China.
     */
    public static final String REGEX_CHINA_POSTAL_CODE = "[1-9]\\d{5}(?!\\d)";
    /**
     * Regex of positive integer.
     */
    public static final String REGEX_POSITIVE_INTEGER = "^[1-9]\\d*$";
    /**
     * Regex of negative integer.
     */
    public static final String REGEX_NEGATIVE_INTEGER = "^-[1-9]\\d*$";
    /**
     * Regex of integer.
     */
    public static final String REGEX_INTEGER = "^-?[1-9]\\d*$";
    /**
     * Regex of non-negative integer.
     */
    public static final String REGEX_NOT_NEGATIVE_INTEGER = "^[1-9]\\d*|0$";
    /**
     * Regex of non-positive integer.
     */
    public static final String REGEX_NOT_POSITIVE_INTEGER = "^-[1-9]\\d*|0$";
    /**
     * Regex of positive float.
     */
    public static final String REGEX_POSITIVE_FLOAT = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
    /**
     * Regex of negative float.
     */
    public static final String REGEX_NEGATIVE_FLOAT = "^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$";

    ///////////////////////////////////////////////////////////////////////////
    // If u want more please visit http://toutiao.com/i6231678548520731137  //
    ///////////////////////////////////////////////////////////////////////////
}
