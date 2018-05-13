package com.xx.watermelon.common.utils.number;

import com.xx.watermelon.common.utils.string.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;


public abstract class AmountUtil {

	private AmountUtil() {
	}

	/**
	 * 加法运算
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 减法运算
	 * @param v1 被减数
	 * @param v2 减数
	 * @return
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	
	/**
	 * 减法运算
	 * @param v1 被减数
	 * @param v2 减数
	 * @return
	 */
	public static BigDecimal sub(BigDecimal v1, BigDecimal v2, int scale, RoundingMode roundingMode) {
		return v1.subtract(v2).setScale(scale, roundingMode);
	}

	/**
	 * 乘法运算
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 除法运算，当发生除不尽的情况时，精确到小数点以后2位，以后的数字四舍五入
	 * @param v1 被除数
	 * @param v2 除数
	 * @return
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, 2);
	}

	/**
	 * 除法运算，当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 精确到小数点以后几位
	 * @return
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 四舍五入
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 判断 a 与 b 是否相等
	 * @param a
	 * @param b
	 * @return a==b 返回true, a!=b 返回false
	 */
	public static boolean equal(double a, double b) {
		BigDecimal v1 = BigDecimal.valueOf(a);
		BigDecimal v2 = BigDecimal.valueOf(b);
		if (v1.compareTo(v2) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断 a 是否大于等于 b
	 * @param a
	 * @param b
	 * @return a&gt;=b 返回true, a&lt;b 返回false
	 */
	public static boolean greaterThanOrEqualTo(double a, double b) {
		BigDecimal v1 = BigDecimal.valueOf(a);
		BigDecimal v2 = BigDecimal.valueOf(b);
		if (v1.compareTo(v2) >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断 a 是否大于 b
	 * @param a
	 * @param b
	 * @return a&gt;b 返回true, a&lt;=b 返回 false
	 */
	public static boolean bigger(double a, double b) {
		BigDecimal v1 = BigDecimal.valueOf(a);
		BigDecimal v2 = BigDecimal.valueOf(b);
		if (v1.compareTo(v2) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 判断 a 是否小于 b
	 * @param a
	 * @param b
	 * @return a&lt;b 返回true, a&gt;=b 返回 false
	 */
	public static boolean lessThan(double a, double b) {
		BigDecimal v1 = BigDecimal.valueOf(a);
		BigDecimal v2 = BigDecimal.valueOf(b);
		if (v1.compareTo(v2) == -1) {
			return true;
		}
		return false;
	}

	/**
	 * 四舍五入保留小数点后两位
	 * @param num
	 * @return
	 */
	public static double roundDown(double num) {
		return Double.valueOf(String.format("%.2f", num));
		// return new BigDecimal(num).setScale(2,
		// BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 金额格式化小数位（四舍五入）
	 * @param amount 金额
	 * @param scale 保留小数位数
	 * @return
	 */
	public static String formatNumber(Double amount, int scale) {
		if (null == amount)
			amount = 0.00;
		BigDecimal b = new BigDecimal(amount).setScale(scale, BigDecimal.ROUND_HALF_UP);
		return b.toString();
	}
	public static String formatNumber(String amount, int scale) {
		if (StringUtils.isBlank(amount))
			amount = "0.00";
		BigDecimal b = new BigDecimal(amount).setScale(scale, BigDecimal.ROUND_HALF_UP);
		return b.toString();
	}
	public static double formatNumberDouble(Double amount, int scale) {
		if (null == amount)
			amount = 0.00;
		BigDecimal b = new BigDecimal(amount).setScale(scale, BigDecimal.ROUND_HALF_UP);
		return b.doubleValue();
	}

	public static void main(String[] args) {
		Double a = 101.005D;
		Double b = 0.0D;
		Double s = AmountUtil.sub(a, b);
		System.out.println(AmountUtil.roundDown(s));
		System.out.println(AmountUtil.div(101.1D, 1D, 2));
	}
}
