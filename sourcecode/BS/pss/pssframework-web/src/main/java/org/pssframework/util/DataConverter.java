package org.pssframework.util;

import java.util.Iterator;

/**
 * 数据转换器
 * @author Zhangyu
 * @version 1.0
 * Create Date : 20081227
 * --------------------
 * Modify Date : 20090527
 * Modify Author : Zhangyu
 * Modify Content : 修改类名为DataConverter（原为DataConver）
 * --------------------
 * Modify Date : 20090527
 * Modify Author : Zhangyu
 * Modify Content : 增加方法，如下
 *     public static Short[] primArray2ObjArray(short[] primArray)
 *     public static short[] objArray2PrimArray(Short[] objArray)
 *     public static Integer[] primArray2ObjArray(int[] primArray)
 *     public static int[] objArray2PrimArray(Integer[] objArray)
 *     public static Long[] primArray2ObjArray(long[] primArray)
 *     public static long[] objArray2PrimArray(Long[] objArray)
 *     public static Float[] primArray2ObjArray(float[] primArray)
 *     public static float[] objArray2PrimArray(Float[] objArray)
 *     public static Double[] primArray2ObjArray(double[] primArray)
 *     public static double[] objArray2PrimArray(Double[] objArray)
 *     public static Character[] primArray2ObjArray(char[] primArray)
 *     public static char[] objArray2PrimArray(Character[] objArray)
 *     public static Boolean[] primArray2ObjArray(boolean[] primArray)
 *     public static boolean[] objArray2PrimArray(Boolean[] objArray)
 * --------------------
 */
public class DataConverter {
	private static final char[] HEX_ARR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	private static final String[] BINHEX_ARR = {"0000", "0001", "0010", "0011", 
		"0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
	
	/**
	 * short[] -> Short[]
	 * @param primArray
	 * @return
	 */
	public static Short[] primArray2ObjArray(short[] primArray) {
	    if(primArray == null) {
	        return null;
	    }
	    Short[] objArray = new Short[primArray.length];
	    for(int i = 0; i < primArray.length; i++) {
	        objArray[i] = new Short(primArray[i]);
	    }
	    return objArray;
	}
	
	/**
	 * Short[] -> short[]
	 * @param objArray
	 * @return
	 */
	public static short[] objArray2PrimArray(Short[] objArray) {
	    if(objArray == null) {
	        return null;
	    }
	    short[] primArray = new short[objArray.length];
	    for(int i = 0; i < objArray.length; i++) {
	        primArray[i] = objArray[i].shortValue();
	    }
	    return primArray;
	}
	
	/**
	 * int[] -> Integer[]
	 * @param primArray
	 * @return
	 */
	public static Integer[] primArray2ObjArray(int[] primArray) {
	    if(primArray == null) {
	        return null;
	    }
	    Integer[] objArray = new Integer[primArray.length];
        for(int i = 0; i < primArray.length; i++) {
            objArray[i] = new Integer(primArray[i]);
        }
        return objArray;
	}

	/**
	 * Integer[] -> int[]
	 * @param objArray
	 * @return
	 */
	public static int[] objArray2PrimArray(Integer[] objArray) {
        if(objArray == null) {
            return null;
        }
        int[] primArray = new int[objArray.length];
        for(int i = 0; i < objArray.length; i++) {
            primArray[i] = objArray[i].intValue();
        }
        return primArray;
    }
	
	/**
	 * long[] -> Long[]
	 * @param primArray
	 * @return
	 */
	public static Long[] primArray2ObjArray(long[] primArray) {
        if(primArray == null) {
            return null;
        }
        Long[] objArray = new Long[primArray.length];
        for(int i = 0; i < primArray.length; i++) {
            objArray[i] = new Long(primArray[i]);
        }
        return objArray;
    }
	
	/**
	 * Long[] -> long[]
	 * @param objArray
	 * @return
	 */
	public static long[] objArray2PrimArray(Long[] objArray) {
        if(objArray == null) {
            return null;
        }
        long[] primArray = new long[objArray.length];
        for(int i = 0; i < objArray.length; i++) {
            primArray[i] = objArray[i].longValue();
        }
        return primArray;
    }
	
	/**
	 * float[] -> Float[]
	 * @param primArray
	 * @return
	 */
	public static Float[] primArray2ObjArray(float[] primArray) {
        if(primArray == null) {
            return null;
        }
        Float[] objArray = new Float[primArray.length];
        for(int i = 0; i < primArray.length; i++) {
            objArray[i] = new Float(primArray[i]);
        }
        return objArray;
    }
	
	/**
	 * Float[] -> float[]
	 * @param objArray
	 * @return
	 */
	public static float[] objArray2PrimArray(Float[] objArray) {
        if(objArray == null) {
            return null;
        }
        float[] primArray = new float[objArray.length];
        for(int i = 0; i < objArray.length; i++) {
            primArray[i] = objArray[i].floatValue();
        }
        return primArray;
    }
	
	/**
	 * double[] -> Double[]
	 * @param primArray
	 * @return
	 */
	public static Double[] primArray2ObjArray(double[] primArray) {
        if(primArray == null) {
            return null;
        }
        Double[] objArray = new Double[primArray.length];
        for(int i = 0; i < primArray.length; i++) {
            objArray[i] = new Double(primArray[i]);
        }
        return objArray;
    }
	
	/**
	 * Double[] -> double[]
	 * @param objArray
	 * @return
	 */
	public static double[] objArray2PrimArray(Double[] objArray) {
        if(objArray == null) {
            return null;
        }
        double[] primArray = new double[objArray.length];
        for(int i = 0; i < objArray.length; i++) {
            primArray[i] = objArray[i].doubleValue();
        }
        return primArray;
    }
	
	/**
	 * char[] -> Character[]
	 * @param primArray
	 * @return
	 */
	public static Character[] primArray2ObjArray(char[] primArray) {
        if(primArray == null) {
            return null;
        }
        Character[] objArray = new Character[primArray.length];
        for(int i = 0; i < primArray.length; i++) {
            objArray[i] = new Character(primArray[i]);
        }
        return objArray;
    }
	
	/**
	 * Character[] -> char[]
	 * @param objArray
	 * @return
	 */
	public static char[] objArray2PrimArray(Character[] objArray) {
        if(objArray == null) {
            return null;
        }
        char[] primArray = new char[objArray.length];
        for(int i = 0; i < objArray.length; i++) {
            primArray[i] = objArray[i].charValue();
        }
        return primArray;
    }
	
	/**
	 * boolean[] -> Boolean[]
	 * @param primArray
	 * @return
	 */
	public static Boolean[] primArray2ObjArray(boolean[] primArray) {
        if(primArray == null) {
            return null;
        }
        Boolean[] objArray = new Boolean[primArray.length];
        for(int i = 0; i < primArray.length; i++) {
            objArray[i] = new Boolean(primArray[i]);
        }
        return objArray;
    }
	
	/**
	 * Boolean[] -> boolean[]
	 * @param objArray
	 * @return
	 */
	public static boolean[] objArray2PrimArray(Boolean[] objArray) {
        if(objArray == null) {
            return null;
        }
        boolean[] primArray = new boolean[objArray.length];
        for(int i = 0; i < objArray.length; i++) {
            primArray[i] = objArray[i].booleanValue();
        }
        return primArray;
    }
	
	/**
	 * java.sql.Timestamp -> java.util.Date
	 * @param timestamp
	 * @return
	 */
	public static java.util.Date sqlTimestamp2UtilDate(java.sql.Timestamp timestamp) {
	    return new java.util.Date(timestamp.getTime());
	}
	
	/**
	 * java.sql.Date -> java.util.Date
	 * @param date
	 * @return
	 */
	public static java.util.Date sqlDate2UtilDate(java.sql.Date date) {
	    return new java.util.Date(date.getTime());
	}
	
	/**
	 * java.math.BigDecimal -> java.lang.Integer
	 * @param bigDecimal
	 * @return
	 */
	public static Integer mathBigDecimal2Integer(java.math.BigDecimal bigDecimal) {
	    return new Integer(bigDecimal.intValue());
	}
	
	/**
	 * java.math.BigDecimal -> java.lang.Long
	 * @param bigDecimal
	 * @return
	 */
	public static Long mathBigDecimal2Long(java.math.BigDecimal bigDecimal) {
	    return new Long(bigDecimal.longValue());
	}
	
	/**
	 * java.math.BigDecimal -> java.lang.Float
	 * @param bigDecimal
	 * @return
	 */
	public static Float mathBigDecimal2Float(java.math.BigDecimal bigDecimal) {
	    return new Float(bigDecimal.floatValue());
	}
	
	/**
	 * java.math.BigDecimal -> java.lang.Double
	 * @param bigDecimal
	 * @return
	 */
	public static Double mathBigDecimal2Double(java.math.BigDecimal bigDecimal) {
	    return new Double(bigDecimal.doubleValue());
	}
	
	/**
	 * java.util.Map -> net.sf.json.JSONObject
	 * @param map
	 * @return
	 */
	public static net.sf.json.JSONObject map2JSONObject(java.util.Map map) {
	    Iterator iterator = map.keySet().iterator();
	    Object key = null;
	    Object value = null;
	    net.sf.json.JSONObject json = new net.sf.json.JSONObject();
	    while(iterator.hasNext()) {
	        key = iterator.next();
	        value = map.get(key);
	        json.put(key, value);
	    }
	    return json;
	}

    /**
     * java.util.Map -> net.sf.json.JSONObject
     * @param map
     * @return
     */
    public static net.sf.json.JSONObject map2json(java.util.Map map) {
        Iterator iterator = map.keySet().iterator();
        Object key = null;
        Object value = null;
        net.sf.json.JSONObject json = new net.sf.json.JSONObject();
        net.sf.json.JSONArray jsonArray = new net.sf.json.JSONArray();
        while(iterator.hasNext()) {
            key = iterator.next();
            value = map.get(key);
            net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
            jsonObject.put("key", key);
            jsonObject.put("value", value);
            jsonArray.add(jsonObject);
        }
        json.put("data", jsonArray);
        return json;
    }
	
    public static byte[] hexs2bytes(String hexs) {
        int hexs_len = hexs.length();
        if(hexs_len % 2 != 0) {
            hexs_len = hexs_len + 1;
            hexs = "0" + hexs;
	    }
	    char[] chars = hexs.toCharArray();
	    byte[] bytes = new byte[hexs_len / 2];
	    int j = 0;
	    
	    for(int i = 0; i < hexs_len; i = i + 2) {
	        bytes[j] = (byte) ((Character.digit(chars[i], 16) << 4) + Character.digit(chars[i + 1], 16));
	        j++;
	    }

	    return bytes;
    }
    
    public static String hexs2Ascii(String hexs) {
        if(hexs == null) {
            return null;
        }
        byte[] bytearray = hexs.getBytes();
        StringBuffer sbAscii = new StringBuffer();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytearray.length; i++) {
            sb = new StringBuffer();
            sb.append(bytearray[i]);
            sbAscii.append(DataConverter.int2Hex(Integer.parseInt(sb.toString()), 1));
        }
        return sbAscii.toString();
    }
	
	/**
	 * 
	 * @param b
	 * @return
	 */
	public static char number2Hex(byte b) {
        char hex = '?';
        if(b >= 0 && b <= 15) {
        	hex = HEX_ARR[b];
        }
        return hex;
    }
	
	/**
	 * 
	 * @param hex
	 * @return
	 */
	public static int hex2Int(char hex) {
		return Integer.parseInt(String.valueOf(hex), 16);
	}
	
	/**
	 * 
	 * @param hexs
	 * @return
	 */
	public static long hex2Int(String hexs) {
	    if(hexs == null || hexs.length() == 0) {
	        return 0;
	    }
	    int i_digit = 0;
	    long result = 0;
	    for(int i = hexs.length(); i > 0; i--, i_digit++) {
	        result += hex2Int(hexs.charAt(i - 1)) * Math.pow(16, i_digit);
	    }
	    return result;
	}
	
	/**
	 * 
	 * @param num
	 * @param len
	 * @return
	 */
	public static String int2Bin(int num, int len) {
	    return zerofillTops(Integer.toBinaryString(num), len);
	}
	
	/**
	 * 数字转换为Hex字符串
	 * @param num : 待转换的数字
	 * @return
	 */
	public static String int2Hex(long num) {
        String hexs = "";
        for(int i = 0; i < 8; i++) {
        	hexs = number2Hex((byte)(num & 0x0f)) + hexs;
            num >>>= 4;
        }
        
        int length = hexs.length() - 1;
        for(int i = 0; i < length; i++) {
        	if(hexs.charAt(0) == '0') {
        		hexs = hexs.substring(1);
        	}
        	else {
        		break;
        	}
        }
        
        return hexs;
    }
	
	/**
	 * 数字转换为Hex字符串
	 * @param num : 待转换的数字
	 * @param cb  : 转换后的字节数 <= 8
	 * @return
	 */
	public static String int2Hex(long num, int cb) {
		String hexs = "";
		if(num >= (long) Math.pow(2, cb * 8)) {
			for(int i = 0; i < cb; i++) {
				hexs += "FF" + hexs;
			}
		}
		else {
			hexs = int2Hex(num);
			hexs = zerofillTops(hexs, cb * 2);
		}
		return hexs;
	}
	
	/**
	 * 
	 * @param num
	 * @param lable
	 * @return
	 */
	public static String int2Hex(long num, String lable) {
		String hexs = "";
		if(num >= (long) Math.pow(2, lable.length() * 4)) {
			for(int i = 0; i < lable.length(); i++) {
				hexs += "F" + hexs;
			}
		}
		else {
			hexs = int2Hex(num);
			hexs = zerofillTops(hexs, lable.length());
		}
		return hexs;
	}
	
	/**
	 * 十六进制字符串转换为二进制字符串
	 * @param hexs
	 * @return
	 */
	public static String hex2Bin(String hexs) {
		String bins = "";
		hexs = (hexs != null ? hexs.trim() : "");
		for(int i = 0; i < hexs.length(); i++) {
			bins += BINHEX_ARR[hex2Int(hexs.charAt(i))];
		}
		return bins;
	}
	
	/**
	 * 二进制字符串转换为十六进制字符串
	 * @param bins
	 * @return
	 */
	public static String bin2Hex(String bins) {
	    String hexs = "";
	    bins = (bins != null ? bins.trim() : "");
	    int len = bins.length();
	    bins = DataConverter.zerofillTops(bins, len + ((len % 4) == 0 ? 0 : (4 - (len % 4))));
	    int bcount = (bins.length() / 4);
	    for(int i = 0; i < bcount; i++) {
            hexs += String.valueOf(HEX_ARR[Integer.parseInt(bins.substring(0, 4), 2)]);
            bins = bins.substring(4);
        }
	    return hexs;
	}
	
	/**
	 * ͷ头部以字符c补齐
	 * @param s
	 * @param length
	 * @param c
	 * @return
	 */
	public static String fillTops(String s, int length, char c) {
		String s_result = s;
		if(s_result == null) {
			s_result = "";
		}
		else {
			s_result = s_result.trim();
		}
		
		int lens = s_result.length();
		if(lens < length) {
			for(int i = 0; i < (length - lens); i++) {
				s_result = c + s_result;
			}
		}
		else {
		    s_result = s_result.substring(0, length);
		}
		
		return s_result;
	}
	
	/**
	 * 头部以字符0补齐
	 * @param s
	 * @param length
	 * @return
	 */
	public static String zerofillTops(String s, int length) {
		return fillTops(s, length, '0');
	}
	
	/**
	 * 尾部以字符c补齐
	 * @param s
	 * @param length
	 * @param c
	 * @return
	 */
	public static String fillTails(String s, int length, char c) {
		String s_result = s;
		if(s_result == null) {
			s_result = "";
		}
		else {
			s_result = s_result.trim();
		}
		
		int lens = s_result.length();
		if(lens < length) {
			for(int i = 0; i < (length - lens); i++) {
				s_result = s_result + c;
			}
		}
        else {
            s_result = s_result.substring(0, length);
        }
		
		return s_result;
	}
	
	/**
	 * 尾部以字符0补齐
	 * @param s
	 * @param length
	 * @return
	 */
	public static String zerofillTails(String s, int length) {
		return fillTails(s, length, '0');
	}

	/**
	 * 
	 * @param bytes
	 * @param length
	 * @return
	 */
	public static String bytes2str(byte[] bytes, int length) {
	    byte[] tmp = new byte[4];
	    String str = "";
	    for(int i = 0; i < length; i++) {
	        tmp[0] = bytes[i];
	        if((i % 10 == 0) && (i > 0)) {
	            str = str + bytestostr(tmp).substring(4, 6);
	        }
	        else {
	            str = str + bytestostr(tmp).substring(4, 6);
	        }
	    }
	    return str;
	}
	
	
	public static String bytestostr(byte[] bytes) {
	    int i = bytestoint(bytes, 0);
	    String str = inttostr(i, 16);
	    return str;
	}
	
	public static int bytestoint(byte[] bytes, int offset) {
	    int st = 0;
	    int tmp = 0;
	    int mod = 0xff;
	    for(int i = offset; i < offset + 4; i++) {
	        tmp = bytes[i];
	        st = st + ((tmp & mod) << (8 * (i - offset)));
	    }
	    return st;
	}
	
	public static String inttostr(int i, int system) {
	    String rstr = "";
	    int rint = i;
	    if(system == 10) {   //十进制
	        rstr = String.valueOf(i);
	    }
	    if(system == 16) {
	        for(int j = 0; j < 4; j++) {
	            if(rint == 0) {
	                rstr = "0" + rstr;
	            }
	            else if(rint < 16) {
	                rstr = tentosixteen(rint) + rstr;
	                rint = 0;
	            }
	            else {
	                rstr = tentosixteen(rint % 16) + rstr;
	                rint = (rint - (rint % 16)) / 16;
	            }
	        }
	        rstr = "0x" + rstr;
	    }
	    return rstr;
	}
	
	public static String tentosixteen(int i) {
	    String rs = "";
	    switch(i) {
	    case 10:
	        rs = "A";
	        break;
	    case 11:
	        rs = "B";
	        break;
	    case 12:
	        rs = "C";
	        break;
	    case 13:
	        rs = "D";
	        break;
	    case 14:
	        rs = "E";
	        break;
	    case 15:
	        rs = "F";
	        break;
	    default:
	        rs = String.valueOf(i);
	    }
	    return rs;
	}
}
