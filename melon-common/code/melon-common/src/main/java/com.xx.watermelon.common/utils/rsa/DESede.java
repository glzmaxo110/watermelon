package com.zc.travel.common.utils.rsa;

import com.zc.travel.common.utils.Base64;
import com.zc.travel.common.utils.ByteUtils;
import com.zc.travel.common.utils.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.ByteBuffer;
import java.security.Key;

/**
 * @description 3DES对称加密/解密
 * @remark
 */
public class DESede {

	//对称加密DESede密钥算法 JAVA 6 支持长度为112和168位 Bouncy Castle支持密钥长度为128位和192位
	public static final String KEY_ALGORITHM = "DESede";
	//加解密算法/工作模式/填充方式NoPadding，JAVA6支持DESede/ECB/PKCS5Padding填充方式；Bouncy Castle支持PKCS7Padding填充方式
	public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";//NoPadding
	//Bouncy Castle算法提供者
//	public static final String PROVIDER_BOUNCY_CASTOLE = "BC";

	/**
	 * 将C#的字节数组转换成JAVA字节数组
	 * @param keyInt
	 * @return
	 * @version 1.0,2017年8月18日 上午10:45:27,Liugl,Ins
	 */
	public static byte[] convertByte(int[] keyInt) {
		byte[] keyByte = new byte[keyInt.length];
		for (int i = 0; i < keyByte.length; i++) {
			if (keyInt[i] > 127) {
				keyByte[i] = (byte) (keyInt[i] - 256);
			} else {
				keyByte[i] = (byte) keyInt[i];
			}
		}
		return keyByte;
	}
	
	/**
	 * 生成密钥
	 * @return
	 * @throws Exception
	 * @version 1.0,2017年8月18日 上午10:45:55,Liugl,Ins
	 */
	public static byte[] initKey() throws Exception {
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(192);
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}
	
	/**
	 * 转换密钥
	 * @param key
	 * @return
	 * @throws Exception
	 * @version 1.0,2017年8月18日 上午10:46:10,Liugl,Ins
	 */
	private static final Key toKey(byte[] key) throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		return keyFactory.generateSecret(dks);
	}

	/**
	 * 加密数据
	 * @param data	需要加密的数据
	 * @param key	加密数据使用的key
	 * @return
	 * @throws Exception
	 * @version 1.0,2017年8月18日 上午10:46:15,Liugl,Ins
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		if (key.length < 24) {
			key = convertKey(key);
		}
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	/**
	 * 解密数据
	 * @param data	需要解密的数据
	 * @param key	解密数据使用的key
	 * @return
	 * @throws Exception
	 * @version 1.0,2017年8月18日 上午10:42:34,Liugl,Ins
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		if (key.length < 24) {
			key = convertKey(key);
		}
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}
	
	/**
	 * 解密数据，data数据为base64加密后的数据
	 * @param data	需要解密的数据
	 * @param key	解密数据使用的key，3des秘钥长度为24字节，当秘钥不够时，右补0到24位
	 * @param charset	字符集类型，默认UTF-8
	 * @return
	 * @throws Exception
	 * @version 1.0,2017年8月18日 下午2:27:27,Liugl,Ins
	 */
	public static String decrypt(String data, String key, String charset) throws Exception {
		if (StringUtils.isBlank(data) || StringUtils.isBlank(key))
			throw new Exception("数据解密参数为空");
		if (key.length() < 24)
			key = StringUtils.rightPad(key, 24, '0');
		if (StringUtils.isBlank(charset))
			charset = "UTF-8";
		//base64解密
		byte[] temp = Base64.decodeToBytes(data);
		//解密
		byte[] d = decrypt(temp, key.getBytes(charset));
		//得到16进制数据
		String r = ByteUtils.byteArray2HexString(d);
		//转换16进制数据为字符串
		r = ByteUtils.toStringHex(r, charset);
		return r;
	}

	/**
	 * PCK方式解密
	 * @param data	需要解密的数据
	 * @param key	解密数据使用的key
	 * @return
	 * @throws Exception
	 * @version 1.0,2017年8月18日 上午10:43:21,Liugl,Ins
	 */
	public static byte[] decryptPck(byte[] data, byte[] key) throws Exception {
		if (key.length < 24) {
			key = convertKey(key);
		}
		Key k = toKey(key);
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		//IvParameterSpec ips = new IvParameterSpec(data);
		//Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);//DESede/ECB/PKCS5Padding
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	/**
	 * 将16字节的密钥转换成24字节
	 * @param srcKey	需要转换的数据
	 * @return
	 * @version 1.0,2017年8月18日 上午10:43:01,Liugl,Ins
	 */
	public static byte[] convertKey(byte[] srcKey) {
		byte[] destKey = null;
		byte[] keyFirst = new byte[8];
		ByteBuffer buffer = ByteBuffer.wrap(srcKey);
		buffer.get(keyFirst);
		buffer.clear();
		buffer = ByteBuffer.allocate(48);  //原为:24,因为memberId解密时容量不够,扩充到48
		buffer.put(srcKey);
		buffer.put(keyFirst);
		buffer.flip();
		destKey = buffer.array();
		buffer.clear();
		return destKey;
	}
	
	public static void main(String args[]) throws Exception {
		String k = "214NGBszPB6zgMiASxb1";
		if (k.length() < 24)
			k = StringUtils.rightPad(k, 24, '0');
		System.out.println("key: " + k);
		byte[] key = k.getBytes();
		byte[] data = "1234aa".getBytes();

		byte[] result = DESede.encrypt(data, key);
		String base = Base64.encode(result);
		System.out.println("3des加密后：" + base);
		
		byte[] temp = Base64.decodeToBytes(base);//base64解密
		byte[] jiemi = DESede.decrypt(temp, key);
		System.out.println("3des解密后：" + ByteUtils.toStringHex(ByteUtils.byteArray2HexString(jiemi), "utf-8"));
		
		String jiemi1 = DESede.decrypt(base, k, "UTF-8");
		System.out.println("3des解密后：" + jiemi1);

	}
}
