package com.zc.travel.common.utils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 类描述：生成随机数
 * @author: huangbin
 * @date： 日期：2013-12-31 时间：下午4:55:45
 * @version 1.0
 */
public class UUIDUitl {
	
	public static final String allCharStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+";
	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String numberChar = "0123456789";
	public static final String specialChar = "!@#$%^&*()_+";

	/**
	 * 返回一个定长的随机字符串，由数字组成
	 * @param length	随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateInteger(int length) {

        return IntStream.range(0, length).mapToObj(i -> String.valueOf(numberChar
                .charAt(ThreadLocalRandom.current().nextInt(numberChar.length())))).collect(Collectors.joining());
	}

	/**
	 * 返回一个定长的随机字符串，由大小写字母、数字组成
	 * @param length	随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateString(int length) {

        return  IntStream.range(0, length).mapToObj(i -> String.valueOf(allChar.charAt(ThreadLocalRandom.current()
                .nextInt(allChar.length())))).collect(Collectors.joining());
	}

	/**
	 * 返回一个定长的随机字符串，由大小写字母、数字、特殊字符组成
	 * @param length	随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateAllString(int length) {
       return IntStream.range(0, length).mapToObj(i -> String.valueOf(allCharStr
               .charAt(ThreadLocalRandom.current().nextInt(allCharStr.length())))).collect(Collectors.joining());
	}

	/**
	 * 返回一个定长的随机纯字母字符串，由大小写字母组成
	 * @param length	随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateMixString(int length) {
        return  IntStream.range(0, length).mapToObj(i -> String.valueOf(allChar
                .charAt(ThreadLocalRandom.current().nextInt(letterChar.length())))).collect(Collectors.joining());
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串
	 * @param length	随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机纯大写字母字符串
	 * @param length	随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	/**
	 * 生成一个定长的纯0字符串
	 * @param length	字符串长度
	 * @return 纯0字符串
	 */
	public static String generateZeroString(int length) {
        return IntStream.range(0, length).mapToObj(i -> "0").collect(Collectors.joining());
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * @param num		数字
	 * @param fixdlenth	字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(long num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 根据数字生成一个定长的字符串，长度不够前面补0
	 * @param num		数字
	 * @param fixdlenth	字符串长度
	 * @return 定长的字符串
	 */
	public static String toFixdLengthString(int num, int fixdlenth) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (fixdlenth - strNum.length() >= 0) {
			sb.append(generateZeroString(fixdlenth - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth + "的字符串发生异常！");
		}
		sb.append(strNum);
		return sb.toString();
	}

	/**
	 * 八位数字+字母+特殊字符随机密码生成
	 * @return
	 */
	public static String generatePwdStr() {
		StringBuffer sb = new StringBuffer();
        IntStream.range(0, 3).forEach(i -> {
            sb.append(allChar.charAt(ThreadLocalRandom.current().nextInt(letterChar.length())));
            sb.append(numberChar.charAt(ThreadLocalRandom.current().nextInt(numberChar.length())));
        });
        IntStream.range(0, 2).map(i -> specialChar.charAt(ThreadLocalRandom.current()
                .nextInt(specialChar.length()))).forEach(sb::append);
		return sb.toString();
	}

}
