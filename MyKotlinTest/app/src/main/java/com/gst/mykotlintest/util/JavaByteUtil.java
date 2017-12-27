package com.gst.mykotlintest.util;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * author: GuoSongtao on 2017/12/20 17:16
 * email: 157010607@qq.com
 */

public class JavaByteUtil {

    public static byte[] hexsting2ByteArray(String hexStr) {
        byte[] data = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length(); i = i + 2) {
            char cl = hexStr.charAt(i);
            char cr = hexStr.charAt(i + 1);
            byte bl = hexChar2Byte(cl);
            byte br = hexChar2Byte(cr);
            if (bl < 0 || br < 0) {
                continue;
            }
            int left = bl << 4;
            data[i / 2] = (byte) (left + br);
        }
        return data;
    }

    public static String byteArray2Hexstring(byte[] data) {
        StringBuilder hexStrBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            byte currByte = data[i];
            if (currByte < 0) {
                continue;
            }
            byte bl = (byte) (currByte >> 4);
            byte br = (byte) (currByte & 0x0F);
            char cl = byte2HexChar(bl);
            char cr = byte2HexChar(br);
            hexStrBuilder.append(cl);
            hexStrBuilder.append(cr);
        }
        return hexStrBuilder.toString();
    }

    public static byte[] sting2ByteArray(String str) {
        return str.getBytes();//str.getBytes("UTF-8");
    }

    public static String byteArray2String(byte[] data) {
        return new String(data);
    }

    public static String sting2Hexstring(String str) {
        return byteArray2Hexstring(str.getBytes());
    }

    public static String byteArray2String(String hexStr) {
        return new String(hexsting2ByteArray(hexStr));
    }

    /**
     * byte=30 变为 hex="0" 代表 十进制=0
     * byte=40 变为 hex="A"  代表十进制=11
     *
     * @param b
     * @return
     */
    private static char byte2HexChar(byte b) {
        if (b <= '9' + 6 && b >= '0') {
            return (char) b;
        }

        if (b > '9' && b <= '9' + 6) {
            return (char) ('A' + (b - 10));
        }
        return ' ';
    }


    public static byte[] hex2bytes(String str) {
        int len = str.length();
        String subChar = null;
        byte[] result = new byte[len / 2];

        for (int var4 = 0; var4 < len / 2; ++var4) {
            subChar = str.substring(var4 * 2, var4 * 2 + 2);
            result[var4] = (byte) Integer.parseInt(subChar, 16);
        }

        return result;
    }

    /**
     * hex="0" 变为 byte=30 代表 十进制=0
     * hex="A"  变为 byte=40 代表十进制=11
     *
     * @param c
     * @return
     */
    private static byte hexChar2Byte(char c) {
        if ('A' <= c && 'Z' >= c) {
            return (byte) (c - 'A' + 10);
        }
        if ('0' <= c && '9' >= c) {
            return (byte) (c - '0');
        }
        if ('a' <= c && 'z' >= c) {
            return (byte) (c - 'a' + 10);
        }
        return -1;
    }


    /***************3DES加密解密的工具类 start**********************/

    //定义加密算法，有DES、DESede(即3DES)、Blowfish
    private static final String Algorithm = "DESede";
    private static final String Cipher_Algorithm = "DESede/ECB/NoPadding";
    private static final String AlgorithmDES = "DES";
    private static final String Cipher_AlgorithmDES = "DES/ECB/NoPadding";//DESede/ECB/NoPadding DESede/ECB/PKCS5Padding
    private static final String PASSWORD_CRYPT_KEY = "2012PinganVitality075522628888ForShenZhenBelter075561869839";


    /**
     * 加密方法
     *
     * @param src 源数据的字节数组
     * @return
     */
    public static byte[] encryptMode(byte[] src) {
        try {
            SecretKey deskey = new SecretKeySpec(build3DesKey(PASSWORD_CRYPT_KEY), Algorithm);    //生成密钥
            Cipher c1 = Cipher.getInstance(Algorithm);    //实例化负责加密/解密的Cipher工具类
            c1.init(Cipher.ENCRYPT_MODE, deskey);    //初始化为加密模式
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 加密方法
     *
     * @param src 源数据的字节数组
     * @return
     */
    public static byte[] encryptMode(byte[] src, String key) throws Exception {

        String asciiString = "0123456789ABCDEF";
        key = key.toUpperCase();
        char[] chars = key.toCharArray();
        byte[] bytes = new byte[key.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            byte high = (byte) asciiString.indexOf(chars[i * 2]);
            byte low = (byte) asciiString.indexOf(chars[i * 2 + 1]);
            bytes[i] = (byte) (high << 4 | low);
//            System.out.print(bytes[i]+",");
        }

        SecretKey deskey = new SecretKeySpec(bytes, Algorithm);    //生成密钥
        Cipher c1 = Cipher.getInstance(Cipher_Algorithm);    //实例化负责加密/解密的Cipher工具类
        c1.init(Cipher.ENCRYPT_MODE, deskey);    //初始化为加密模式
        return c1.doFinal(src);
    }

    /**
     * 解密函数
     *
     * @param src 密文的字节数组
     * @return
     */
    public static byte[] decryptMode(byte[] src, String key) {
        try {

            String asciiString = "0123456789ABCDEF";
            key = key.toUpperCase();
            char[] chars = key.toCharArray();
            byte[] bytes = new byte[key.length() / 2];
            for (int i = 0; i < bytes.length; i++) {
                byte high = (byte) asciiString.indexOf(chars[i * 2]);
                byte low = (byte) asciiString.indexOf(chars[i * 2 + 1]);
                bytes[i] = (byte) (high << 4 | low);
            }

            SecretKey deskey = new SecretKeySpec(bytes, Algorithm);
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);    //初始化为解密模式
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 解密函数
     *
     * @param src 密文的字节数组
     * @return
     */
    public static byte[] decryptMode(byte[] src) {
        try {
            SecretKey deskey = new SecretKeySpec(build3DesKey(PASSWORD_CRYPT_KEY), Algorithm);
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);    //初始化为解密模式
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /*
     * 根据字符串生成密钥字节数组
      * @param keyStr 密钥字符串
     * @return
      * @throws UnsupportedEncodingException
    */
    public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24];    //声明一个24位的字节数组，默认里面都是0
        byte[] temp = keyStr.getBytes("UTF-8");    //将字符串转成字节数组
     /*
          * 执行数组拷贝
         * System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
      */
        if (key.length > temp.length) {
            //如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            //如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }

    /***************3DES加密解密的工具类 start**********************/


    /************ANSIX9_19MAC start *************/
    /**
     * (1)               ANSI X9.19MAC算法只使用双倍长密钥，也就是16字节密钥；
     * (2)               MAC数据按8字节分组，表示为D0～Dn，如果Dn不足8字节时，尾部以字节00补齐；
     * (3)               用MAC密钥左半部加密D0，加密结果与D1异或作为下一次的输入。
     * (4)               将上一步的加密结果与下一分组异或，然后用MAC密钥左半部加密。
     * (5)               直至所有分组结束。
     * (6)               用MAC密钥右半部解密(5)的结果。
     * （7)               用MAC密钥左半部加密(6)的结果。
     * (8)               取(7)的结果的左半部作为MAC。
     *
     * @param key  16字节密钥数据
     * @param data 待计算的缓冲区
     */
    public static byte[] calculateANSIX9_19MAC(byte[] key, byte[] data) throws Exception {
        if (key == null || data == null)
            return null;

        if (key.length != 16) {
            throw new RuntimeException("秘钥长度错误.");
        }

        byte[] keyLeft = new byte[8];
        byte[] keyRight = new byte[8];
        System.arraycopy(key, 0, keyLeft, 0, 8);
        System.arraycopy(key, 8, keyRight, 0, 8);

        byte[] result99 = calculateANSIX9_9MAC(keyLeft, data);

        byte[] resultTemp = decryptByDesEcb(result99, keyRight);
        return encryptByDesEcb(resultTemp, keyLeft);
    }

    /**
     * des加密算法，ECB方式，NoPadding模式，数据字节必须是8的整数倍
     *
     * @param key
     * @param content 数据字节必须是8的整数倍
     * @return 加密结果
     */
    public static byte[] encryptByDesEcb(byte[] content, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(Cipher_AlgorithmDES);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(AlgorithmDES);
        SecretKey secretKey = keyFactory.generateSecret(new DESKeySpec(key));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(content);
    }

    /**
     * des解密算法，ECB方式，NoPadding模式，数据字节必须是8的整数倍
     *
     * @param key     秘钥
     * @param content 数据字节必须是8的整数倍
     * @return
     * @throws Exception
     */
    public static byte[] decryptByDesEcb(byte[] content, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(Cipher_AlgorithmDES);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(AlgorithmDES);
        SecretKey secretKey = keyFactory.generateSecret(new DESKeySpec(key));
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(content);
    }


    /**
     * ANSI X9.9MAC算法  <br/>
     * (1) ANSI X9.9MAC算法只使用单倍长密钥。  <br/>
     * (2)  MAC数据先按8字节分组，表示为D0～Dn，如果Dn不足8字节时，尾部以字节00补齐。 <br/>
     * (3) 用MAC密钥加密D0，加密结果与D1异或作为下一次的输入。 <br/>
     * (4) 将上一步的加密结果与下一分组异或，然后再用MAC密钥加密。<br/>
     * (5) 直至所有分组结束，取最后结果的左半部作为MAC。<br/>
     * 采用x9.9算法计算MAC (Count MAC by ANSI-x9.9).
     *
     * @param key  8字节密钥数据
     * @param data 待计算的缓冲区
     * @throws Exception
     */
    public static byte[] calculateANSIX9_9MAC(byte[] key, byte[] data) throws Exception {

        final int dataLength = data.length;
        final int lastLength = dataLength % 8;
        final int lastBlockLength = lastLength == 0 ? 8 : lastLength;
        final int blockCount = dataLength / 8 + (lastLength > 0 ? 1 : 0);

        // 拆分数据（8字节块/Block）
        byte[][] dataBlock = new byte[blockCount][8];
        for (int i = 0; i < blockCount; i++) {
            int copyLength = i == blockCount - 1 ? lastBlockLength : 8;
            System.arraycopy(data, i * 8, dataBlock[i], 0, copyLength);
        }

        byte[] desXor = new byte[8];
        for (int i = 0; i < blockCount; i++) {
            byte[] tXor = xOr(desXor, dataBlock[i]);
            desXor = encryptByDesEcb(tXor, key); // DES加密
        }
        return desXor;
    }

    /**
     * 将b1和b2做异或，然后返回
     *
     * @param b1
     * @param b2
     * @return 异或结果
     */
    public static byte[] xOr(byte[] b1, byte[] b2) {
        byte[] tXor = new byte[Math.min(b1.length, b2.length)];
        for (int i = 0; i < tXor.length; i++)
            tXor[i] = (byte) (b1[i] ^ b2[i]); // 异或(Xor)
        return tXor;
    }

    /************ANSIX9_19MAC end *************/
}
