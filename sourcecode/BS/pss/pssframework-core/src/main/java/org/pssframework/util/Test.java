package org.pssframework.util;

/**
 * 
 */


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Baocj
 *
 */
public class Test {
	private static HanyuPinyinOutputFormat format;

	public static void setFormat(HanyuPinyinOutputFormat hanyuPinyinOutputFormat) {
		format = hanyuPinyinOutputFormat;
	}

	public Test() {

	}

	
	public Test(HanyuPinyinOutputFormat hanyuPinyinOutputFormat) {
		format = hanyuPinyinOutputFormat;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

		PropertyPlaceholderConfigurer prop = (PropertyPlaceholderConfigurer) ctx.getBean("propertyConfigurer");
		System.out.println(getPingYinFullUpcase("单田芳"));
	}

	public static String getPingYinFullUpcase(String src) {
		char[] t1;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = format;
		t3.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuffer t4 = new StringBuffer("");
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				//判断是否为汉字字符函数 [\\u4E00-\\u9FA5]+
				if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4.append(t2[0]);
				} else
					t4.append(String.valueOf(t1[i]));
			}
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t4.toString();
	}

	public static String getPingYinUpcase(String src) {
		char[] t1;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = format;
		t3.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuffer t4 = new StringBuffer("");
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				//判断是否为汉字字符函数 [\\u4E00-\\u9FA5]+
				if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4.append(t2[0].charAt(0));
				} else
					t4.append(String.valueOf(t1[i]));
			}
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t4.toString();
	}
}
