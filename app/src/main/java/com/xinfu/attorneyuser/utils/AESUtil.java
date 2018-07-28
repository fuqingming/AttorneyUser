package com.xinfu.attorneyuser.utils;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

	public static AESUtil instance;

	/**
	 * 获取工具类实例
	 * @return
	 */
	public static AESUtil getInstance(){
		if(instance == null){
			instance = new AESUtil();
		}
		return instance;
	}
	
	public static String generateKey()
	{
		byte[] key = new byte[16];
		Random random = new Random();
		for(int i=0;i<key.length;i++)
		{
			int val = random.nextInt(256);
			
			key[i] = (byte) val;
		}
		
		return HexUtil.parseByte2HexStr(key);
	}
	
	public static String generateIV()
	{
		byte[] iv = new byte[16];
		Random random = new Random();
		for(int i=0;i<iv.length;i++)
		{
			int val = random.nextInt(256);
			
			iv[i] = (byte) val;
		}
		
		return HexUtil.parseByte2HexStr(iv);
	}
	
	public static byte[] encrypt(byte[] plain,byte[] key,byte[] iv)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			IvParameterSpec ivParaSpec = new IvParameterSpec(iv);//使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParaSpec);
			byte[] encrypted = cipher.doFinal(plain);
			return encrypted; 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public static byte[] decrypt(byte[] encrypted,byte[] key,byte[] iv)
	{
		try 
		{
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			IvParameterSpec ivParaSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParaSpec);
			byte[] plain = cipher.doFinal(encrypted);
			return plain;
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
}
