package com.lzhs.library.utils.encryption;


import android.support.annotation.NonNull;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Description: 3DES 简单加密 <br/>
 * 用户可动态设置Constants ，动态配置向量、key 等其他重要参数<br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2018/12/27 : 5:44 PM<br/>
 */
public final class Encode3DES {
    static Constants mConstants;

    /**
     * @param str 需要加密的文字
     * @return 加密后的文字
     * @throws Exception 加密失败
     */
    public static String encode3DES(final String str) {
        try {
            if (mConstants == null) mConstants = new Constants();
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(mConstants.getSecretKey().getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory
                    .getInstance(mConstants.getAlgorithm());
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(mConstants.getIv().getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] encryptData = cipher.doFinal(str.getBytes(mConstants.getEncoding()));
            return Base64.encode(encryptData);
        } catch (Exception ex) {
            ex.printStackTrace();
            return str;
        }

    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    public static String decode3DES(String encryptText) {
        try {
            if (mConstants == null) mConstants = new Constants();
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(mConstants.getSecretKey().getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(mConstants.getAlgorithm());
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(mConstants.getIv().getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
            byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
            return new String(decryptData, mConstants.getEncoding());
        } catch (Exception ex) {
            ex.printStackTrace();
            return encryptText;
        }
    }

    public static void setConstants(@NonNull Constants mConstants) {
        Encode3DES.mConstants = mConstants;
    }
}