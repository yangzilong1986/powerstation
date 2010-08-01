package org.pssframework.util;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * 字符串辅助类，处理常用的字符串操作
 * 
 */

public class StringUtil {
	public static String filterNull(String arg) {
		if (null != arg && arg.length() > 0)
			return arg;
		else
			return "";
	}

	/**
	 * 将字符串转化为整数
	 * @param str String
	 * @return int
	 */
	public static int parseInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * 处理字符串：如果为null,则返回空串
	 * @param sIn String
	 * @return String
	 */
	public static String nvl(String sIn) {
		return (sIn == null) ? "" : sIn;
	}

	/**
	 * 如果字符串为null或空串，转换为缺省的字符串
	 * @param sIn String
	 * @param sDefault String 缺省的字符串
	 * @return String
	 */
	public static String nvl(String sIn, String sDefault) {
		return (sIn == null) ? sDefault : sIn;
	}

	/**
	 * 将字符串首字母大写
	 * @param src String
	 * @return String
	 */
	public static String capital(String src) {
		String result = "";
		if (src == null || src.length() == 0)
			return "";
		else {
			try {
				result = src.substring(0, 1).toUpperCase() + src.substring(1);
			} catch (Exception e) {
				result = "";
			}
		}
		return result;
	}

	public static String formatDate(String date) {
		//1.判断是否合法
		if (null != date && date.length() != 8)
			return "";
		//2.转换
		StringBuffer d = new StringBuffer();
		d.append(date.substring(0, 4));
		d.append("-");
		d.append(date.substring(4, 6));
		d.append("-");
		d.append(date.substring(6, 8));
		return d.toString();
	}

	/**
	 * 缺省的字符串分割符
	 */
	public static String DEFAULT_DELIM = "$*";

	/**
	 * 私有构造方法，防止类的实例化，因为工具类不需要实例化。
	 */
	private StringUtil() {
	}

	/**
	 * 判断是否为空
	 * 
	 * @param array 字符串
	 * @return boolean
	 */
	public static boolean isNull(String s) {
		if (null != s && s.length() > 0)
			return true;
		else
			return false;
	}

	/**
	 * 将字符串数组使用缺省的分隔符合并成一个字符串。
	 * 
	 * @param array 字符串数组
	 * @return 合并后的字符串
	 */
	public static String join(String[] array) {
		return join(array, DEFAULT_DELIM);
	}

	/**
	 * 将字符串数组使用指定的分隔符合并成一个字符串。
	 * 
	 * @param array 字符串数组
	 * @param delim 分隔符，为null的时候使用缺省分割符（逗号）
	 * @return 合并后的字符串
	 */
	public static String join(String[] array, String delim) {
		int length = array.length - 1;
		if (delim == null) {
			delim = DEFAULT_DELIM;
		}
		StringBuffer result = new StringBuffer(length * 8);
		for (int i = 0; i < length; i++) {
			result.append(array[i]);
			result.append(delim);
		}
		result.append(array[length]);
		return result.toString();
	}

	/**
	 * 将字符串使用缺省分割符（逗号）划分的单词数组。
	 * 
	 * @param source 需要进行划分的原字符串
	 * @return 划分以后的数组，如果source为null的时候返回以source为唯一元素的数组。
	 */
	public static String[] split(String source) {
		return split(source, DEFAULT_DELIM);
	}

	/**
	 * 此方法将给出的字符串source使用delim划分为单词数组。 注意：分隔字符串中每一个 <b>(ANY) </b>的字符都作为独立的分割符。 <br>
	 * 举个例子： <br>
	 * "mofit.com.cn"用"com"分割后的结果是三个字符串"fit."、"."和"n"，而不是"mofit."和".cn"。
	 * 
	 * @param source 需要进行划分的原字符串
	 * @param delim 单词的分隔字符串
	 * @return 划分以后的数组，如果source为null的时候返回以source为唯一元素的数组， 如果delim为null则使用逗号作为分隔字符串。
	 */
	public static String[] split(String source, String delim) {
		String[] wordLists;
		if (source == null) {
			wordLists = new String[1];
			wordLists[0] = source;
			return wordLists;
		}
		if (delim == null) {
			delim = DEFAULT_DELIM;
		}
		StringTokenizer st = new StringTokenizer(source, delim);

		int total = st.countTokens();
		wordLists = new String[total];
		for (int i = 0; i < total; i++) {
			wordLists[i] = st.nextToken();
		}
		return wordLists;
	}

	/**
	 * 字符串数组中是否包含指定的字符串。 注意：准确的说应该是匹配，而不是包含。 <br>
	 * 举个例子：字符串数组"mofit.com.cn","neusoft.com"里 <b>不包含 </b>"com"， <br>
	 * 但是 <b>包含 </b>"mofti.com.cn"。
	 * 
	 * @param strings 字符串数组
	 * @param string 字符串
	 * @param caseSensitive 是否大小写敏感
	 * @return 包含时返回true，否则返回false
	 */
	public static boolean contains(String[] strings, String string, boolean caseSensitive) {
		for (int i = 0; i < strings.length; i++) {
			if (caseSensitive == true) {
				if (strings[i].equals(string))
					return true;
			} else {
				if (strings[i].equalsIgnoreCase(string))
					return true;
			}
		}
		return false;
	}

	/**
	 * 字符串数组中是否包含指定的字符串。大小写敏感。 <br>
	 * 注意：准确的说应该是匹配，而不是包含。 <br>
	 * 举个例子：字符串数组"mofit.com.cn","neusoft.com"里 <b>不包含 </b>"com"， <br>
	 * 但是 <b>包含 </b>"mofti.com.cn"。
	 * 
	 * @param strings 字符串数组
	 * @param string 字符串
	 * @return 包含时返回true，否则返回false
	 */
	public static boolean contains(String[] strings, String string) {
		return contains(strings, string, true);
	}

	/**
	 * 去除左边多余的空格。
	 * 
	 * @param value 待去左边空格的字符串
	 * @return 去掉左边空格后的字符串
	 */
	public static String trimLeft(String value) {
		String result = value;
		if (result == null)
			return result;
		char ch[] = result.toCharArray();
		int index = -1;
		for (int i = 0; i < ch.length; i++) {
			if (Character.isWhitespace(ch[i])) {
				index = i;
			} else {
				break;
			}
		}
		if (index != -1) {
			result = result.substring(index + 1);
		}
		return result;
	}

	/**
	 * 去除右边多余的空格。
	 * 
	 * @param value 待去右边空格的字符串
	 * @return 去掉右边空格后的字符串
	 */
	public static String trimRight(String value) {
		String result = value;
		if (result == null)
			return result;
		char ch[] = result.toCharArray();
		int endIndex = -1;
		for (int i = ch.length - 1; i > -1; i--) {
			if (Character.isWhitespace(ch[i])) {
				endIndex = i;
			} else {
				break;
			}
		}
		if (endIndex != -1) {
			result = result.substring(0, endIndex);
		}
		return result;
	}

	/**
	 * 得到字符串的字节长度。汉字占两个字节，字母占一个字节
	 * 
	 * @param source 字符串
	 * @return 字符串的字节长度
	 */
	public static int getLength(String source) {
		int len = 0;
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			int highByte = c >>> 8;
			len += highByte == 0 ? 1 : 2;
		}
		return len;
	}

	/**
	 * 使用给定的字串替换源字符串中指定的字串。
	 * 
	 * @param mainString 源字符串
	 * @param oldString 被替换的字串
	 * @param newString 替换字串
	 * @return 替换后的字符串
	 */
	public final static String replace(String mainString, String oldString, String newString) {
		if (mainString == null)
			return null;
		int i = mainString.lastIndexOf(oldString);
		if (i < 0)
			return mainString;
		StringBuffer mainSb = new StringBuffer(mainString);
		while (i >= 0) {
			mainSb.replace(i, i + oldString.length(), newString);
			i = mainString.lastIndexOf(oldString, i - 1);
		}
		return mainSb.toString();
	}

	/**
	 * 将给定的字符串转换为中文GBK编码的字符串。
	 * 
	 * @param str 输入字符串
	 * @return 经GBK编码后的字符串，如果有异常，则返回原编码字符串
	 */
	public final static String toChinese(final String str) {
		if (null == str || "".equals(str))
			return str;
		String retVal = str;
		try {
			retVal = new String(str.getBytes("ISO8859_1"), "GBK");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 将给定的中文GBK编码转换为UNICODE编码的字符串。
	 * 
	 * @param str 输入字符串
	 * @return 经GBK编码后的字符串，如果有异常，则返回原编码字符串
	 */
	public final static String toUNICODE(final String str) {
		if (null == str || "".equals(str))
			return str;
		String retVal = str;
		try {
			retVal = new String(str.getBytes("ZHS16GBK"), "GBK");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return retVal;
	}

	/**
	 * 用于字符串显示。将html敏感的尖括号、引号、连接号等用转义符替代。 <br>
	 * 建议用法：在接收到客户端传来的字符串时，不进行转换，直接存入数据库； <br>
	 * 在从数据库中取出，传给客户端做html显示时，才转换。
	 * 
	 * @param input 需要检查的字符串
	 * @return 转化后的字串
	 */
	public final static String convertToHTML(String input) {
		if (null == input || "".equals(input))
			return input;

		StringBuffer buf = new StringBuffer();
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else if (ch == '&') {
				buf.append("&amp;");
			} else if (ch == '"') {
				buf.append("&quot;");
			} else if (ch == '\n') {
				buf.append("<BR/>");
			} else {
				buf.append(ch);
			}
		}
		return buf.toString();
	}

	/**
	 * 定义字符串加密时需要用到的标记字符串
	 */
	private static String ENCRYPT_IN = "YN8K1JOZVURB3MDETS5GPL27AXW`IHQ94C6F0~qwert!@yuiop#$asdfghj%kl^&*zxc vbn(m)_+|{}:\"<>?-=\\[];,./'";

	/**
	 * 定义字符串加密时需要用到的转义字符串
	 */
	private static String ENCRYPT_OUT = "qazwsxcderfvbgtyhnmjuiklop~!@#$%^&*()_+|{ }:\"<>?-=\\[];,./'ABCDE`FGHIJKLMNOPQRSTUVWXYZ0123456789";

	/**
	 * 对给定字符串进行加密操作
	 * 
	 * @param inPass 待加密的字符串
	 * @return 加密后的字符串
	 */
	public static String encrypt(String inPass) {
		String stringIn = ENCRYPT_IN;
		String stringOut = ENCRYPT_OUT;
		int time1 = Calendar.getInstance().get(Calendar.MINUTE);
		int time2 = Calendar.getInstance().get(Calendar.SECOND);
		int offset = (time1 + time2) % 95;
		String outPass = stringIn.substring(offset, offset + 1);
		stringIn = stringIn + stringIn;
		stringIn = stringIn.substring(offset, offset + 95);
		String temp = "";
		for (int i = 0; i <= inPass.length() - 1; i++) {
			temp = temp + stringOut.charAt(stringIn.indexOf(inPass.charAt(i)));

		}
		outPass = outPass + temp;
		return outPass;
	}

	/**
	 * 对给定字符串进行解密操作
	 * 
	 * @param outPass 待解密的字符串
	 * @return 解密还原后的字符串
	 */
	public static String decrypt(String outPass) {
		String stringIn = ENCRYPT_IN;
		String stringOut = ENCRYPT_OUT;
		int offset = stringIn.indexOf(outPass.charAt(0));
		stringIn = stringIn + stringIn;
		stringIn = stringIn.substring(offset, offset + 95);
		outPass = outPass.substring(1);
		String inPass = "";
		for (int i = 0; i <= outPass.length() - 1; i++) {
			inPass = inPass + stringIn.charAt(stringOut.indexOf(outPass.charAt(i)));

		}
		return inPass;
	}

	//指定的字符串累加
	public static String strAdd(String chr, int len) {
		if (len > 0) {
			StringBuffer ret = new StringBuffer(len);
			for (int i = 0; i < len; i++) {
				ret.append(chr);
			}
			return (ret.toString());
		} else
			return "";
	}

	//给字符串补足到指定的长度，从左边补足chr指定的字符
	public static String lPad(String source, String chr, int len) {
		int lenleft = len - source.length();
		if (lenleft < 0) {
			lenleft = 0;
		}
		return (strAdd(chr, lenleft) + source);
	}

	//给字符串补足到指定的长度，从右边补足chr指定的字符
	public static String rPad(String source, String chr, int len) {
		int lenleft = len - source.length();
		if (lenleft < 0) {
			lenleft = 0;
		}
		return (source + strAdd(chr, lenleft));
	}

	public static void main(String[] args) {

		String[] test = { "北京 mofit. com. cn", " neusoft.com ", "<out\"OfTax>" };
		String all = join(test, "||");

		System.out.println("字符串[" + test[0] + "],[" + test[1] + "],[" + test[2] + "]合并结果：" + all);
		System.out.println("字符串[" + test[0] + "],[" + test[1] + "],[" + test[2] + "]是否包含［<out\"oftax>］(区分大小写)："
				+ contains(test, "<out\"oftax>"));
		System.out.println("字符串[" + test[0] + "],[" + test[1] + "],[" + test[2] + "]是否包含［<out\"oftax>］(不区分大小写)："
				+ contains(test, "<out\"oftax>", false));
		System.out.println("字符串[" + all + "]转换为html：" + convertToHTML(all));

		String en = encrypt(test[2]);

		System.out.println("字符串[" + test[2] + "]加密后为：" + en);
		System.out.println("字符串[" + en + "]解密后为：" + decrypt(en));
		System.out.println("字符串[" + test[0] + "]的长度为：" + getLength(test[0]));
		System.out.println("字符串[" + all + "]用[Company]替换[.com]后为：[" + replace(all, ".com", "Company") + "]");

		String[] sp = split(test[0], "com");
		System.out.println("字符串[" + test[0] + "]用[com]分隔为" + sp.length + "个字符串。分别为：[" + sp[0] + "],[" + sp[1] + "],["
				+ sp[2] + "]...");

		System.out.println("字符串[" + test[1] + "]去掉左空格为：[" + trimLeft(test[1]) + "]");
		System.out.println("字符串[" + test[1] + "]去掉右空格为：[" + trimRight(test[1]) + "]");
		System.out.println("字符串[" + test[1] + "]去掉空格为：[" + removeBlank(test[1]) + "]");
	}

	/**
	 * 取小数点后1位
	 * @param f float
	 * @return float
	 */
	public static float roundFloat(float f) {

		float result = (float) ((int) ((f + 0.0005) * 1000) / 10.0);
		return result;
	}

	/**
	 * 取小数点后一位
	 */
	public static String round(float f) {
		String temp = (f + 0.05) + "";
		if (null != temp && temp.length() > 0) {
			temp = temp.substring(0, temp.lastIndexOf(".") + 2);
		}
		return temp;
	}

	/** 
	* 去掉字符串中的空格 
	* @param str 
	* @return String 
	*/
	public static String removeBlank(String str) {
		StringBuilder sb = new StringBuilder();
		char c = ' ';
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch != c) {
				sb.append(ch);
			}
		}
		return sb.toString();
	}
}