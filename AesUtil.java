package com.taobao.tae.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created with IntelliJ IDEA.
 * User: Xijun(ansing100@qq.com)
 * Date: 13-7-28
 * Time: ����3:50
 * To change this template use File | Settings | File Templates.
 */
public class AesUtil {

    private static final String commonNick = "www.taobao.com";

    public static String getCommonSecretKey() {
        //ʵ����
        KeyGenerator kgen = null;

        try {
            kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(commonNick.getBytes());
            //������Կ����
            kgen.init(128, secureRandom);
            //������Կ
            SecretKey secretKey = kgen.generateKey();
            //������Կ�Ķ����Ʊ���
            byte[] enCodeFormat = secretKey.getEncoded();
            return parseByte2HexStr(enCodeFormat);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    /**
     * ����
     *
     * @param content  ��Ҫ���ܵ�����
     * @param password ��������
     * @return
     */
    public static String encrypt(String content, String password) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(password.getBytes());
        kgen.init(128, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        // ����������
        byte[] byteContent = content.getBytes("utf-8");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // ��ʼ��
        byte[] result = cipher.doFinal(byteContent);
        return parseByte2HexStr(result);
    }

    /**
     * ����
     *
     * @param content  ����������
     * @param password ������Կ
     * @return
     */
    public static String decrypt(String content, String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(password.getBytes());
        kgen.init(128, secureRandom);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        // ����������
        cipher.init(Cipher.DECRYPT_MODE, key);
        // ��ʼ��
        byte[] result = cipher.doFinal(parseHexStr2Byte(content));
        return new String(result);
    }

    /**
     * ��������ת����16����
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * ��16����ת��Ϊ������
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) {
//        String key = "sdflsja234s5dkssf";// 16, 24, or 32
//        String plaint = "��ã�������������yes!";
//        AesUtil aes = new AesUtil();
//        String secret;
//        try {
//            secret = encrypt(plaint, key);
//            System.out.println(secret);
//            String plaintworkd = decrypt(secret, key);
//            System.out.println(plaintworkd);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            System.out.println("��Կ���ԣ�");
//            // e.printStackTrace();
//        }

    }
}
