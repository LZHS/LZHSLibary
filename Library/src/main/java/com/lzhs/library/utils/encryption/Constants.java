package com.lzhs.library.utils.encryption;

public class Constants {
    /**
     * 密钥 key长度为24个字符，不足可补零
     */
    private  String secretKey = "1050659507@qq.com.lzhs.lzhs_library";
    /**
     * 向量 向量必须为8位
     */
    private String iv = "5l9z2h5s";
    /**
     * 加解密统一使用的编码方式
     */
    private String encoding = "UTF-8";
    /**
     * 3des加密
     */
    private String algorithm = "desede";

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}