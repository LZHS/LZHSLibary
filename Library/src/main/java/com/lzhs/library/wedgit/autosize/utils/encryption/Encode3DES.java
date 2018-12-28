package com.lzhs.library.wedgit.autosize.utils.encryption;


import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Encode3DES {
    /**
     * @param str 需要加密的文字
     * @return 加密后的文字
     * @throws Exception 加密失败
     */
    public static String encode3DES(final String str) {
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(Constants.secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory
                    .getInstance(Constants.algorithm);
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(Constants.iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] encryptData = cipher.doFinal(str.getBytes(Constants.encoding));
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
    public static String decode3DES(String encryptText)   {
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(Constants.secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(Constants.algorithm);
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(Constants.iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
            byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
            return new String(decryptData, Constants.encoding);
        }catch (Exception ex){
            ex.printStackTrace();
            return encryptText;
        }
    }
}