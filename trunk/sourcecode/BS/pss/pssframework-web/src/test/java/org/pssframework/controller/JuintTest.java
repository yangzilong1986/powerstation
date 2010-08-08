package org.pssframework.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pssframework.model.system.OrgInfo;
import org.pssframework.model.system.RoleInfo;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import pep.bp.realinterface.mto.MTO_376;
import pep.bp.realinterface.mto.MessageTranObject;

/**
 * Created by IntelliJ IDEA.
 * User: Baocj
 * Date: 2010-5-16
 * Time: 22:31:44
 * To change this template use File | Settings | File Templates.
 */
public class JuintTest {

	private String jsonString;

	static {
		 ConvertUtils.register(new SqlDateConverter(), java.util.Date.class);
		// ConvertUtils.register(new UtilDateConverter(), java.util.Date.class);   
	}

	@Before
	public void one() {
		jsonString = "{\"collectObjects\":[{\"logicalAddr\":\"96123456\",\"equipProtocol\":\"GW_376\",\"channelType\":\"1\",\"pwAlgorith\":\"0\",\"pwContent\":\"8888\",\"mpExpressMode\":\"3\",\"mpSn\":[\"0\"],\"commandItems\":[{\"identifier\":\"100C0025\"}]}]}";
		//jsonString = "{\"mtoType\":\"GW_376\",\"collectObjects\":[{\"logicalAddr\":\"96123456\",\"equipProtocol\":\"100\",   \"channelType\":\"1\",\"pwAlgorith\":\"0\",\"pwContent\":\"8888\",\"mpExpressMode\":\"3\",\"mpSn\":[\"0\"],\"commandItems\":[{\"identifier\":\"100C0025\"}]}]}";
	}

	@After
	public void two() {

	}

	public void fuck() {
		char[] init = new char[]{'0', '0', '0', '0', '0', '0', '0', '0'};
		System.out.println(String.valueOf(init));

	}

	@Test
	public void testString() {
		

		List<RoleInfo> list = new ArrayList<RoleInfo>();
		List<Long> listIds = new ArrayList<Long>();
		listIds.add(1L);
		listIds.add(2L);
		listIds.add(3L);
		listIds.add(4L);

		List<RoleInfo> roleInfos = new ArrayList<RoleInfo>();
		RoleInfo roleInfo = new RoleInfo(1L);
		roleInfos.add(roleInfo);
		roleInfo = new RoleInfo(2L);
		roleInfos.add(roleInfo);
		roleInfo = new RoleInfo(4L);
		roleInfos.add(roleInfo);

		for (RoleInfo role : roleInfos) {

			for (Long roleId : listIds) {

				if (role.getRoleId() == roleId) {
					break;
				} else {
					list.add(new RoleInfo(roleId));
				}

			}
		}

		System.out.println(list);

	}

	@Test
	public void testDouble() {

		Integer a = 12;
		String e = Integer.toHexString(a);

		String.format("c0", e);
		System.out.println(e);

		String[] s = new String[5];
		s[3] = "1";
		assertEquals(s[3], "1");

		List list = new ArrayList();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(1);
		Object[] o = list.toArray();
		assertEquals(o[4], 1);

		double c = 0.22d;
		double d = 0.23d;
		DecimalFormat df = new DecimalFormat("########.##");
		System.out.print(c * d);

		System.out.print(df.format(c * d));


	}

	public void testBeanCopy() {

		OrgInfo orgInfo1 = new OrgInfo();
		orgInfo1.setOrgId(1L);
		OrgInfo orgInfo2 = new OrgInfo();
		orgInfo2.setOrgName("asdasd");
		BeanUtilsBean2 beanUtilsBean =  new BeanUtilsBean2();
	org.springframework.beans.BeanUtils.copyProperties(orgInfo2,orgInfo1);
		//BeanUtils.copyProperties(orgInfo2, orgInfo1);
		assertEquals(orgInfo2.getOrgId(), new Long(1L));
		assertEquals(orgInfo2.getOrgName(),"asdasd");
	}

	public void jackSon() throws JsonGenerationException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();

		MessageTranObject mto_376 = mapper.readValue(jsonString, MTO_376.class);
		System.out.println(mto_376);

		MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
		MockHttpServletResponse httpServletResponse = new MockHttpServletResponse();
		Map<String, String> mapIn1 = new TreeMap<String, String>();
		mapIn1.put("a", "a1");
		mapIn1.put("b", "b2");
		mapIn1.put("c", "c3");
		mapIn1.put("d", "d4");

		Map<String, String> mapIn2 = new TreeMap<String, String>();
		mapIn2.put("e", "e1");
		mapIn2.put("f", "f2");
		mapIn2.put("g", "g3");
		mapIn2.put("h", "h4");

		Map<String, String> mapIn3 = new TreeMap<String, String>();
		mapIn2.put("i", "i1");
		mapIn2.put("j", "j2");
		mapIn2.put("k", "k3");
		mapIn2.put("l", "l4");

		Map<String, String> mapIn4 = new TreeMap<String, String>();
		mapIn4.put("m", "m1");
		mapIn4.put("n", "n2");
		mapIn4.put("o", "o3");
		mapIn4.put("p", "p4");

		Map<String, Map<String, String>> testMap = new LinkedHashMap<String, Map<String, String>>();
		testMap.put("zdljdz1#cldbh1#common1#dataitem1", mapIn1);
		testMap.put("zdljdz2#cldbh2#common1#dataitem2", mapIn2);
		testMap.put("zdljdz3#cldbh3#common1#dataitem3", mapIn3);
		testMap.put("zdljdz4#cldbh4#common1#dataitem4", mapIn4);

		mapper = new ObjectMapper();
		httpServletResponse.setCharacterEncoding("utf-8");
		httpServletResponse.setContentType("application/json");
		mapper.writeValue(httpServletResponse.getOutputStream(), testMap);
		System.out.println(mapper.writeValueAsString(testMap));
	}

	private static byte[] shortToByteArray(short s) {
		byte[] shortBuf = new byte[2];
		for (int i = 0; i < 2; i++) {
			int offset = (shortBuf.length - 1 - i) * 8;
			shortBuf[i] = (byte) ((s >>> offset) & 0xff);
		}
		return shortBuf;
	}

	public static final int byteArrayToShort(byte[] b) {
		return (b[0] << 8) + (b[1] & 0xFF);
	}

	public static byte[] intToByteArray(int value) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			int offset = (b.length - 1 - i) * 8;
			b[i] = (byte) ((value >>> offset) & 0xFF);
		}
		return b;
	}

	public static final int byteArrayToInt(byte[] b) {
		return (b[0] << 24) + ((b[1] & 0xFF) << 16) + ((b[2] & 0xFF) << 8) + (b[3] & 0xFF);
	}

	/*
				* byte to int
				*
				* @param b 待转换的字节数组
				*
				* @param offset 偏移量，字节数组中开始转换的位置
				*
				* @return
				*/

	public static int byte2int(byte b[], int offset) {
		return b[offset + 3] & 0xff | (b[offset + 2] & 0xff) << 8 | (b[offset + 1] & 0xff) << 16
				| (b[offset] & 0xff) << 24;
	}

	/**
	 * int to byte
	 *
	 * @param n待转换的整形变量
	 * @param buf       转换后生成的字节数组
	 * @param offset    偏移量，字节数组中开始存放的位置
	 */
	public static void int2byte(int n, byte buf[], int offset) {
		buf[offset] = (byte) (n >> 24);
		buf[offset + 1] = (byte) (n >> 16);
		buf[offset + 2] = (byte) (n >> 8);
		buf[offset + 3] = (byte) n;
	}

	/**
	 * @param n                      待转换的short变量
	 * @param buf                    转换后存放的byte数组
	 * @param offset偏移量，字节数组中开始存放的位置
	 * @returntype void
	 */
	public static void short2byte(int n, byte buf[], int offset) {
		buf[offset] = (byte) (n >> 8);
		buf[offset + 1] = (byte) n;
	}

	/**
	 * @param buf
	 * @return
	 */
	public static String byte2Hex(byte[] buf) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (byte b : buf) {
			if (b == 0) {
				sb.append("00");
			} else if (b == -1) {
				sb.append("FF");
			} else {
				String str = Integer.toHexString(b).toUpperCase();
				// sb.append(a);
				if (str.length() == 8) {
					str = str.substring(6, 8);
				} else if (str.length() < 2) {
					str = "0" + str;
				}
				sb.append(str);

			}
			sb.append(" ");
		}
		sb.append("}");
		return sb.toString();
	}

	public static int unsignedByteToInt(byte b) {
		return (int) b & 0xFF;
	}

	/**
	 * convert signed one byte into a hexadecimal digit
	 *
	 * @param b byte
	 * @return convert result
	 */
	public static String byteToHex(byte b) {
		int i = b & 0xFF;
		return Integer.toHexString(i);
	}

	/**
	 * convert signed 4 bytes into a 32-bit integer
	 *
	 * @param buf bytes buffer
	 * @param pos beginning <code>byte</code>> for converting
	 * @return convert result
	 */
	public static long unsigned4BytesToInt(byte[] buf, int pos) {
		int firstByte = 0;
		int secondByte = 0;
		int thirdByte = 0;
		int fourthByte = 0;
		int index = pos;
		firstByte = (0x000000FF & ((int) buf[index]));
		secondByte = (0x000000FF & ((int) buf[index + 1]));
		thirdByte = (0x000000FF & ((int) buf[index + 2]));
		fourthByte = (0x000000FF & ((int) buf[index + 3]));
		index = index + 4;
		return ((long) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;
	}

	public static long bytes2long(byte[] b) {

		int mask = 0xff;
		int temp = 0;
		int res = 0;
		for (int i = 0; i < 8; i++) {
			res <<= 8;
			temp = b[i] & mask;
			res |= temp;
		}
		return res;
	}

	public static byte[] long2bytes(long num) {
		byte[] b = new byte[8];
		for (int i = 0; i < 8; i++) {
			b[i] = (byte) (num >>> (56 - i * 8));
		}
		return b;
	}

	public static long getLong(byte[] bb, int index) {
		return ((((long) bb[index + 0] & 0xff) << 56) | (((long) bb[index + 1] & 0xff) << 48)
				| (((long) bb[index + 2] & 0xff) << 40) | (((long) bb[index + 3] & 0xff) << 32)
				| (((long) bb[index + 4] & 0xff) << 24) | (((long) bb[index + 5] & 0xff) << 16)
				| (((long) bb[index + 6] & 0xff) << 8) | (((long) bb[index + 7] & 0xff) << 0));
	}

	public static void putLong(byte[] bb, long x, int index) {
		bb[index + 0] = (byte) (x >> 56);
		bb[index + 1] = (byte) (x >> 48);
		bb[index + 2] = (byte) (x >> 40);
		bb[index + 3] = (byte) (x >> 32);
		bb[index + 4] = (byte) (x >> 24);
		bb[index + 5] = (byte) (x >> 16);
		bb[index + 6] = (byte) (x >> 8);
		bb[index + 7] = (byte) (x >> 0);
	}

	public static void putShort(byte b[], short s, int index) {
		b[index] = (byte) (s >> 8);
		b[index + 1] = (byte) (s >> 0);
	}

	public static short getShort(byte[] b, int index) {
		return (short) (((b[index] << 8) | b[index + 1] & 0xff));
	}

	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;
		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}
		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}
		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;
		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}
			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}
			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}
}
