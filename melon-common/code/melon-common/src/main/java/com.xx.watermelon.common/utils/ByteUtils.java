package com.zc.travel.common.utils;

/**
 * @description 字节转换工具类
 * @remark
 */
public class ByteUtils {

	/**
	 * 十六进制 转换 byte[]
	 * @param hexStr
	 * @return
	 */
	public static byte[] hexString2ByteArray(String hexStr){
		if (hexStr == null)
			return null;
		if (hexStr.length() % 2 != 0) {
			return null;
		}
		byte[] data = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			char hc = hexStr.charAt(2 * i);
			char lc = hexStr.charAt(2 * i + 1);
			byte hb = hexChar2Byte(hc);
			byte lb = hexChar2Byte(lc);
			if (hb < 0 || lb < 0) {
				return null;
			}
			int n = hb << 4;
			data[i] = (byte) (n + lb);
		}
		return data;
	}

	/**
	 * char 转换为 byte
	 * @param c
	 * @return
	 */
	public static byte hexChar2Byte(char c){
		if (c >= '0' && c <= '9')
			return (byte) (c - '0');
		if (c >= 'a' && c <= 'f')
			return (byte) (c - 'a' + 10);
		if (c >= 'A' && c <= 'F')
			return (byte) (c - 'A' + 10);
		return -1;
	}

	/**
	 * byte[] 转 16进制字符串
	 * @param arr
	 * @return
	 */
	public static String byteArray2HexString(byte[] arr){
		StringBuilder sbd = new StringBuilder();
		for (byte b : arr) {
			String tmp = Integer.toHexString(0xFF & b);
			if (tmp.length() < 2)
				tmp = "0" + tmp;
			sbd.append(tmp);
		}
		return sbd.toString();
	}
	
	/**
	 * 16进制数据转换为字符串
	 * @param s			需要转换的16进制字符串
	 * @param charset	编码格式
	 * @return
	 * @version 1.0,2017年8月18日 上午11:54:48,Liugl,Ins
	 */
	public static String toStringHex(String s, String charset) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, charset);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

}