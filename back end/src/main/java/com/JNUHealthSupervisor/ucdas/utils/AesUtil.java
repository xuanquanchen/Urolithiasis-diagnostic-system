package com.JNUHealthSupervisor.ucdas.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密工具类
 * @author Chen Yixing
 * @version 1.0.0
 */
public class AesUtil {
  
  /**
   * AES解密(USE AES/ECB/PKCS5Padding)
   * @param rsa 待解密信息
   * @param key 密码
   * @return String 解密结果
   * @throws Exception
   */
  public static String decrypt(byte[] rsa, String key) throws Exception{
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"));
    return new String(cipher.doFinal(rsa));
  }

  /**
   * AES加密(USE AES/ECB/PKCS5Padding)
   * @param data 待加密信息
   * @param key 密码
   * @return {@code byte[]} 加密结果
   * @throws Exception
   */
  public static byte[] encrypt(String data, String key) throws Exception{
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"));
    return cipher.doFinal(data.getBytes());
  }

}
