package com.lzhs.library.utils.des3;

import com.lzhs.library.utils.encryption.Constants;
import com.lzhs.library.utils.encryption.Encode3DES;

import org.junit.Assert;
import org.junit.Test;

/**
 * Description: 描述 <br/>
 * Author: LZHS <br/>
 * Email: 1050629507@qq.com <br/>
 * Time: 2019/1/25 : 5:16 PM<br/>
 */
public class Encode3DESTest {

    String testVal = "3DES 加解密测试";

    @Test
    public void encode3DES() {
        long encodeTime = System.currentTimeMillis();
        String encodeVal = Encode3DES.encode3DES(testVal);
        long runTime = System.currentTimeMillis() - encodeTime;
        System.out.println("【3DES 加密】".concat("\n耗时：" + runTime).concat("\n运行结果 ： " + encodeVal));
        Assert.assertNotNull(encodeVal);
    }

    @Test
    public void decode3DES() {
        String encodeVal = Encode3DES.encode3DES(testVal);
        long dcodeTime = System.currentTimeMillis();
        String decodeVal = Encode3DES.decode3DES(encodeVal);
        long runTime = System.currentTimeMillis() - dcodeTime;
        System.out.println("【3DES 解密】".concat("\n耗时：" + runTime).concat("\n运行结果 ： " + decodeVal));
        Assert.assertNotNull(decodeVal);

    }

    @Test
    public void setConstants() {
        Constants mConstants = new Constants();
        mConstants.setSecretKey("1234567890@qq.com.lzhs.lzhs_library");
        mConstants.setIv("00000000");
        Encode3DES.setConstants(mConstants);
        encode3DES();
        decode3DES();

    }
}