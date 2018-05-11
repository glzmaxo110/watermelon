package com.zc.travel.common.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配工具类。包括：手机号，邮箱，IP地址
 * 
 * @author tianjing
 * @date 2015年7月1日 下午3:17:00
 * @version 2.0
 */
public class RegexUtils
{
	/**
	 * 手机号正则匹配
	 * 
	 * @param mobileNumber 手机号 兼容港澳手机校验
	 * @return true:手机号格式正确
	 */
	public static final boolean mobileRegex(String mobileNumber) {
		// 非空判断
		if (null == mobileNumber)
			return false;
		// 判断手机号位数
		if (11 != mobileNumber.length())
			return false;
		Pattern p = null;
		// 匹配手机号是否已“1”开头,如果不是,则是港澳手机
		if (!mobileNumber.startsWith("1")){
			String areaCode=mobileNumber.substring(0,3);
			switch (areaCode) {
				//香港手机
				case "852":
					p = Pattern.compile("^([6|9|5])\\d{7}$");
					mobileNumber=mobileNumber.substring(3);
					break;
				//澳门手机
				case "853":
					p = Pattern.compile("^([6])\\d{7}$");
					mobileNumber=mobileNumber.substring(3);
					break;
				default:
					return false;
			}
		}else {
			p = Pattern.compile("^[1]\\d{10}$");
		}
		Matcher m = p.matcher(mobileNumber);
		return m.matches();
	}

	/**
	 * 手机号正则匹配区分港澳
	 * @param mobileNumber
	 * @param areaCode
	 * @return
	 */
	public static final boolean mobileRegex(String mobileNumber, String areaCode) {
		if (StringUtils.isBlank(mobileNumber) || StringUtils.isBlank(areaCode))
			return false;
		Pattern p = null;
		switch (areaCode) {
			//大陆手机
			case "86":
				p = Pattern.compile("^1\\d{10}$");
				break;
			//香港手机
			case "852":
				p = Pattern.compile("^([6|9|5])\\d{7}$");
				break;
			//澳门手机
			case "853":
				p = Pattern.compile("^([6])\\d{7}$");
				break;
			default:
				return false;
		}
		Matcher m = p.matcher(mobileNumber);
		return m.matches();
	}
	/**
	 * 邮箱地址正则匹配
	 * 
	 * @param email 邮箱地址
	 * @return true:邮箱地址格式正确
	 */
	public static final boolean emailRegex(String email) {
		// 非空判断
		if (null == email)
			return false;
		// 判断邮箱地址是否有“@”符号、是否有“.”符号
		if (-1 == email.indexOf("@") || -1 == email.indexOf("."))
			return false;
		//Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		Pattern p = Pattern.compile("^[\\.a-zA-Z0-9_=-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
		Matcher m = p.matcher(email);
		return m.matches();
	}
	/**
	 * 店铺联系人信息正则
	 *
	 * @param mobile 联系信息
	 * @return true:联系信息格式正确
	 */
	public static final boolean shopContactRegex(String mobile) {
		// 非空判断
		if (null == mobile)
			return false;
		Pattern p = Pattern.compile("^(?=.*[\\d])[\\+\\-0-9]{7,15}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
	/**
	 * 子账户会员名正则匹配,只能数字和字母 必须有字母
	 *
	 * @param memberName 联系信息
	 * @return true:联系信息格式正确
	 */
	public static final boolean childAccountMemberName(String memberName) {
		// 非空判断
		if (null == memberName)
			return false;
		Pattern p = Pattern.compile("^(?=.*[A-Za-z])[A-Za-z0-9]{6,12}$");
		Matcher m = p.matcher(memberName);
		return m.matches();
	}
	
	/**
	 * 密码复杂度正则
	 * 
	 * @param pwd
	 * @return true:符合要求
	 */
	public static final boolean pwdComplexRegex(String pwd) {
		if (StringUtils.isBlank(pwd))
			return false;
		// 密码长度校验
		int pwdLen = pwd.length();
		if (pwdLen < 6 || pwdLen > 20)
			return false;
		// 6-20位数字，字母或符号
		Pattern p = Pattern.compile("^.{6,20}$");
		Matcher m = p.matcher(pwd);
		return m.matches();
	}
	/**
	 * 身份证格式匹配 香港
	 *	HK: ^[A-Z]{1}[1-9]{6}(\(|\（)(\d|A)(\)|\）)$,
	 * @param identityCardNo
	 * @return true:居民身份证格式正确
	 */
	public static final boolean identityCardNoForHKRegex(String identityCardNo) {
		if (StringUtils.isBlank(identityCardNo))
			return false;
		Pattern p = Pattern.compile("^[A-Z]{1}[0-9]{6}(\\()(\\d|A)(\\))$");
		Matcher m = p.matcher(identityCardNo);
		return m.matches();
	}
	/**
	 * 身份证格式匹配 澳门
	 *	MC: ^(1|5|7)([1-9]{6})(\（|\()(\d)(\）|\))$,
	 * @param identityCardNo
	 * @return true:居民身份证格式正确
	 */
	public static final boolean identityCardNoForMCRegex(String identityCardNo) {
		if (StringUtils.isBlank(identityCardNo))
			return false;
		Pattern p = Pattern.compile("^(1|5|7)([0-9]{6})(\\()(\\d)(\\))$");
		Matcher m = p.matcher(identityCardNo);
		return m.matches();
	}
	
	/**
	 * 身份证格式匹配 大陆
	 * 
	 * @param identityCardNo
	 * @return true:居民身份证格式正确
	 */
	public static final boolean identityCardNoRegex(String identityCardNo) {
		if (StringUtils.isBlank(identityCardNo))
			return false;
		Pattern p = Pattern.compile("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)");
		Matcher m = p.matcher(identityCardNo);
		return m.matches();
//		if (len == 18) {
//			/**
//			 * 18位身份证号码各位的含义: <br>
//			 * 1-2位省、自治区、直辖市代码； <br>
//			 * 3-4位地级市、盟、自治州代码； <br>
//			 * 5-6位县、县级市、区代码； <br>
//			 * 7-14位出生年月日，比如19670401代表1967年4月1日； <br>
//			 * 15-17位为顺序号，其中17位（倒数第二位）男为单数，女为双数； <br>
//			 * 18位为校验码，0-9和X。作为尾号的校验码，是由把前十七位数字带入统一的公式计算出来的，计算的结果是0-10，<br>
//			 * 如果某人的尾号是0－9，都不会出现X，但如果尾号是10，那么就得用X来代替，因为如果用10做尾号，那么此人的身份证就变成了19位。X是罗马数字的10，用X来代替10。<br>
//			 */
////			Pattern p = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");
//			
//		}
//		if (len == 15) {
//			/**
//			 * 15位身份证号码各位的含义: <br>
//			 * 1-2位省、自治区、直辖市代码； <br>
//			 * 3-4位地级市、盟、自治州代码； <br>
//			 * 5-6位县、县级市、区代码； <br>
//			 * 7-12位出生年月日,比如670401代表1967年4月1日,与18位的第一个区别； <br>
//			 * 13-15位为顺序号，其中15位男为单数，女为双数；<br>
//			 */
//			Pattern p = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
//			Matcher m = p.matcher(identityCardNo);
//			return m.matches();
//		}
//		return false;
	}
	
	/**
	 * 实名姓名正则
	 * 
	 * @param realName
	 * @return true:真实姓名吻合
	 */
	public static final boolean realNameRegex(String realName) {
		if (StringUtils.isBlank(realName))
			return false;
		Pattern p = Pattern.compile("^(([\u4E00-\u9FA5]{2,4})|([a-zA-Z]{3,10}))$");
		Matcher m = p.matcher(realName);
		return m.matches();
	}

	/**
	 * 日期yyyy-MM-dd日期类格式校验
	 * @param dateStr
	 * @return
     */
	public static final  boolean dateFormatRegex(String dateStr){
		if (StringUtils.isBlank(dateStr))
			return false;
		Pattern p = Pattern.compile("^(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)$");
		Matcher m = p.matcher(dateStr);
		return m.matches();
	}
	/**
	 * 日期yyyy-MM-dd HH:mm:ss日期类格式校验
	 * @param dateStr
	 * @return
     */
	public static final  boolean dateTimeFormatRegex(String dateStr){
		if (StringUtils.isBlank(dateStr))
			return false;
		Pattern p = Pattern.compile("^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$");
		Matcher m = p.matcher(dateStr);
		return m.matches();
	}
	/**
	 * 是否为数字格式
	 * @param dateStr
	 * @return
	 */
	public static final  boolean isNumberRegex(String dateStr){
		if (StringUtils.isBlank(dateStr))
			return false;
		Pattern p = Pattern.compile("^([0-9]{1,})$");
		Matcher m = p.matcher(dateStr);
		return m.matches();
	}
	
}
