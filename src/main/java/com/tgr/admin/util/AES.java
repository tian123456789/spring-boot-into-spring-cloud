package com.tgr.admin.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AES {
	private static Log log = LogFactory.getLog(AES.class);
	/**
	 * 加密
	 * @param bef_aes
	 * @param password
	 * @return
	 */
	public static String encrypt(String bef_aes, String password) {
		byte[] byteContent = null;
		try {
			byteContent = bef_aes.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encrypt(byteContent, password);
	}
	/**
	 * 加密
	 * @param content
	 * @param password
	 * @return
	 */
	public static String encrypt(byte[] content, String password) {
		try {
			SecretKey secretKey = getKey(password);
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			String aft_aes = parseByte2HexStr(result);
			return aft_aes; // 加密
		} catch (NoSuchAlgorithmException e) {
			log.warn("AES加密失败！NoSuchAlgorithmException");
		} catch (NoSuchPaddingException e) {
			log.warn("AES加密失败！NoSuchPaddingException");
		} catch (InvalidKeyException e) {
			log.warn("AES加密失败！InvalidKeyException");
		} catch (IllegalBlockSizeException e) {
			log.warn("AES加密失败！IllegalBlockSizeException");
		} catch (BadPaddingException e) {
			log.warn("AES加密失败！BadPaddingException");
		}
		return null;
	}
	/**
	 * 解密
	 * @param aft_aes
	 * @param password
	 * @return
	 */
	public static String decrypt(String aft_aes, String password) {
		try {
			byte[] content = parseHexStr2Byte(aft_aes);
			SecretKey secretKey = getKey(password);
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			String bef_aes = new String(result);
			return bef_aes; // 加密
		} catch (NoSuchAlgorithmException e) {
			log.warn("AES解密失败！NoSuchAlgorithmException");
		} catch (NoSuchPaddingException e) {
			log.warn("AES解密失败！NoSuchPaddingException");
		} catch (InvalidKeyException e) {
			log.warn("AES解密失败！InvalidKeyException");
		} catch (IllegalBlockSizeException e) {
			log.warn("AES解密失败！IllegalBlockSizeException");
		} catch (BadPaddingException e) {
			log.warn("AES解密失败！BadPaddingException");
		}
		return null;
	}

	private static String parseByte2HexStr(byte buf[]) {
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

	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int value = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16);
			result[i] = (byte) value;
		}
		return result;
	}

	private static SecretKey getKey(String strKey) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("AES");
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(strKey.getBytes());
			_generator.init(128, secureRandom);
			return _generator.generateKey();
		} catch (Exception e) {
			throw new RuntimeException("初始化密钥出现异常");
		}
	}
}
