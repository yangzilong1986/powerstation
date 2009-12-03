/*     */ package com.hisun.message;
/*     */ 
/*     */ import com.hisun.common.HiIgnoreHashMap;
/*     */ import com.hisun.exception.HiSysException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import net.sf.json.JSONArray;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Attribute;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.io.DOMReader;
/*     */ import org.w3c.dom.Text;
/*     */ 
/*     */ public class HiETFUtils
/*     */ {
/*     */   static final String PRE_ATTR = "ATTR_";
/*     */ 
/*     */   public static HiETF convertToXmlETF(org.w3c.dom.Document domDoc)
/*     */   {
/*  57 */     DOMReader reader = new DOMReader();
/*  58 */     org.dom4j.Document doc = reader.read(domDoc);
/*     */ 
/*  60 */     return convertToXmlETF(doc);
/*     */   }
/*     */ 
/*     */   public static HiETF convertToXmlETF(org.dom4j.Document doc)
/*     */   {
/*  76 */     if (doc != null)
/*     */     {
/*  78 */       org.dom4j.Element root = doc.getRootElement();
/*  79 */       org.dom4j.Element etfRoot = DocumentHelper.createDocument().addElement(root.getName());
/*     */ 
/*  81 */       xmlToETF(root, etfRoot);
/*     */ 
/*  83 */       return new HiXmlETF(etfRoot);
/*     */     }
/*     */ 
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */   public static org.w3c.dom.Document convertToDOM(HiETF etf)
/*     */     throws HiSysException
/*     */   {
/* 102 */     HiXmlETF xmlEtf = (HiXmlETF)etf;
/* 103 */     if (xmlEtf == null)
/*     */     {
/* 105 */       return null;
/*     */     }
/* 107 */     org.dom4j.Element etfRoot = xmlEtf.getNode();
/*     */ 
/* 109 */     DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
/* 110 */     DocumentBuilder build = null;
/*     */     try {
/* 112 */       build = fac.newDocumentBuilder();
/*     */     } catch (ParserConfigurationException e) {
/* 114 */       throw new HiSysException("215013", "ETF", e);
/*     */     }
/* 116 */     org.w3c.dom.Document domDoc = build.newDocument();
/* 117 */     org.w3c.dom.Element root = domDoc.createElement(etfRoot.getName());
/* 118 */     domDoc.appendChild(root);
/*     */ 
/* 120 */     etfToXml(etfRoot, root);
/*     */ 
/* 122 */     return domDoc;
/*     */   }
/*     */ 
/*     */   public static org.dom4j.Document convertToDocument(HiETF etf)
/*     */   {
/* 138 */     HiXmlETF xmlEtf = (HiXmlETF)etf;
/* 139 */     if (xmlEtf == null)
/*     */     {
/* 141 */       return null;
/*     */     }
/* 143 */     org.dom4j.Element etfRoot = xmlEtf.getNode();
/* 144 */     org.dom4j.Element root = DocumentHelper.createDocument().addElement(etfRoot.getName());
/*     */ 
/* 146 */     etfToXml(etfRoot, root);
/*     */ 
/* 148 */     return root.getDocument();
/*     */   }
/*     */ 
/*     */   private static void etfToXml(org.dom4j.Element etfRoot, org.dom4j.Element root)
/*     */   {
/* 153 */     Iterator it = etfRoot.elementIterator();
/* 154 */     org.dom4j.Element child = null; org.dom4j.Element etfChild = null;
/*     */ 
/* 157 */     while (it.hasNext())
/*     */     {
/* 159 */       etfChild = (org.dom4j.Element)it.next();
/* 160 */       String childName = etfChild.getName();
/* 161 */       if (childName.startsWith("ATTR_"))
/*     */       {
/* 163 */         root.addAttribute(StringUtils.substringAfter(childName, "ATTR_"), etfChild.getText());
/*     */       }
/*     */ 
/* 168 */       child = root.addElement(childName);
/* 169 */       child.setText(etfChild.getText());
/*     */ 
/* 171 */       etfToXml(etfChild, child);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void etfToXml(org.dom4j.Element etfRoot, org.w3c.dom.Element root)
/*     */   {
/* 177 */     Iterator it = etfRoot.elementIterator();
/* 178 */     org.dom4j.Element etfChild = null;
/*     */ 
/* 180 */     org.w3c.dom.Document domDoc = root.getOwnerDocument();
/*     */ 
/* 184 */     while (it.hasNext())
/*     */     {
/* 186 */       etfChild = (org.dom4j.Element)it.next();
/* 187 */       String childName = etfChild.getName();
/* 188 */       if (childName.startsWith("ATTR_"))
/*     */       {
/* 190 */         root.setAttribute(StringUtils.substringAfter(childName, "ATTR_"), etfChild.getText());
/*     */       }
/*     */ 
/* 195 */       org.w3c.dom.Element child = domDoc.createElement(childName);
/* 196 */       Text text = domDoc.createTextNode(etfChild.getText());
/* 197 */       child.appendChild(text);
/* 198 */       root.appendChild(child);
/*     */ 
/* 200 */       etfToXml(etfChild, child);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void xmlToETF(org.dom4j.Element root, org.dom4j.Element etfRoot)
/*     */   {
/* 206 */     Iterator it = root.elementIterator();
/* 207 */     org.dom4j.Element child = null; org.dom4j.Element etfChild = null;
/*     */ 
/* 209 */     Iterator attrIt = null;
/* 210 */     Attribute attr = null;
/* 211 */     while (it.hasNext())
/*     */     {
/* 213 */       child = (org.dom4j.Element)it.next();
/* 214 */       etfChild = etfRoot.addElement(child.getName());
/*     */ 
/* 216 */       attrIt = child.attributeIterator();
/* 217 */       while (attrIt.hasNext())
/*     */       {
/* 219 */         attr = (Attribute)attrIt.next();
/* 220 */         org.dom4j.Element attrChild = etfChild.addElement("ATTR_" + attr.getName());
/* 221 */         attrChild.setText(attr.getValue());
/*     */       }
/*     */ 
/* 225 */       etfChild.setText(child.getText());
/*     */ 
/* 227 */       xmlToETF(child, etfChild);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static HiETF form2etf(HashMap map) throws Exception {
/* 232 */     return form2etf(map, false);
/*     */   }
/*     */ 
/*     */   public static HiETF form2etf(HashMap map, boolean ignoreCase) throws Exception {
/* 236 */     Iterator iter = map.keySet().iterator();
/* 237 */     int idx1 = 0;
/* 238 */     HiETF etf = HiETFFactory.createETF();
/* 239 */     while (iter.hasNext()) {
/* 240 */       String name = (String)iter.next();
/* 241 */       String[] values = (String[])(String[])map.get(name);
/*     */ 
/* 243 */       String groupName = "GROUP";
/* 244 */       String varName = name;
/* 245 */       idx1 = name.indexOf(46);
/*     */ 
/* 247 */       if (values.length == 1) {
/* 248 */         if (StringUtils.isBlank(values[0]))
/*     */           continue;
/* 250 */         if (idx1 == -1) {
/* 251 */           setValue(etf, name, values[0], ignoreCase);
/*     */         }
/* 253 */         groupName = name.substring(0, idx1);
/* 254 */         varName = name.substring(idx1 + 1);
/* 255 */         HiETF group = null;
/* 256 */         if (!(ignoreCase))
/* 257 */           group = etf.addNodeBase(groupName + "_1", "");
/*     */         else {
/* 259 */           group = etf.addNode(groupName + "_1");
/*     */         }
/*     */ 
/* 262 */         setValue(group, varName, values[0], ignoreCase);
/*     */ 
/* 264 */         if (!(ignoreCase))
/* 265 */           etf.setChildValueBase(groupName + "_NUM", "1");
/*     */         else {
/* 267 */           etf.setChildValue(groupName + "_NUM", "1");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 273 */       if (idx1 == -1)
/*     */       {
/* 275 */         setValue(etf, name, values[0], ignoreCase);
/*     */       }
/*     */ 
/* 279 */       if (idx1 != -1) {
/* 280 */         groupName = name.substring(0, idx1);
/* 281 */         varName = name.substring(idx1 + 1);
/*     */       }
/* 283 */       int count = 0;
/* 284 */       for (int i = 0; i < values.length; ++i) {
/* 285 */         if (StringUtils.isBlank(values[i]))
/*     */           continue;
/* 287 */         String tmp = groupName + "_" + (count + 1);
/* 288 */         HiETF group = null;
/* 289 */         if (!(ignoreCase))
/* 290 */           group = etf.getChildNodeBase(tmp);
/*     */         else {
/* 292 */           group = etf.getChildNode(tmp);
/*     */         }
/* 294 */         if (group == null) {
/* 295 */           if (!(ignoreCase))
/* 296 */             group = etf.addNodeBase(tmp, "");
/*     */           else {
/* 298 */             group = etf.addNode(tmp);
/*     */           }
/*     */         }
/* 301 */         setValue(group, varName, values[i], ignoreCase);
/* 302 */         ++count;
/*     */       }
/*     */ 
/* 305 */       if (count != 0) {
/* 306 */         if (!(ignoreCase))
/* 307 */           etf.setChildValueBase(groupName + "_NUM", String.valueOf(count));
/*     */         else {
/* 309 */           etf.setChildValue(groupName + "_NUM", String.valueOf(count));
/*     */         }
/*     */       }
/*     */     }
/* 313 */     return etf;
/*     */   }
/*     */ 
/*     */   private static void setValue(HiETF node, String name, String value, boolean ignoreCase) throws Exception
/*     */   {
/* 318 */     if (StringUtils.isNotBlank(value))
/* 319 */       if (!(ignoreCase))
/* 320 */         node.setChildValueBase(name, value);
/*     */       else
/* 322 */         node.setChildValue(name, value);
/*     */   }
/*     */ 
/*     */   public static String etf2form(HiETF etf)
/*     */   {
/* 328 */     StringBuffer buf = new StringBuffer();
/* 329 */     etf2form(etf.getChildNodes(), "", buf);
/* 330 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public static void etf2form(List list, String prefix, StringBuffer buf) {
/* 334 */     for (int i = 0; i < list.size(); ++i) {
/* 335 */       HiETF node = (HiETF)list.get(i);
/* 336 */       List list1 = node.getChildNodes();
/* 337 */       if (list1.size() != 0) {
/* 338 */         etf2form(list1, node.getName(), buf);
/*     */       } else {
/* 340 */         String value = node.getValue();
/* 341 */         if (buf.length() > 0) {
/* 342 */           buf.append("&");
/*     */         }
/* 344 */         if ("null".equals(value)) {
/* 345 */           value = "";
/*     */         }
/* 347 */         if (prefix == "")
/* 348 */           buf.append(node.getName() + "=" + value);
/*     */         else
/* 350 */           buf.append(prefix + "." + node.getName() + "=" + value);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static HiIgnoreHashMap etf2HashMapList(HiETF etf)
/*     */   {
/* 358 */     HiIgnoreHashMap map = new HiIgnoreHashMap();
/* 359 */     toHashMapList(etf.getChildNodes(), map, map);
/* 360 */     return map;
/*     */   }
/*     */ 
/*     */   public static void toHashMapList(List list, HiIgnoreHashMap map, HiIgnoreHashMap parentMap) {
/* 364 */     for (int i = 0; i < list.size(); ++i) {
/* 365 */       HiETF node = (HiETF)list.get(i);
/* 366 */       List list1 = node.getChildNodes();
/* 367 */       if (list1.size() != 0)
/*     */       {
/*     */         ArrayList tmpList;
/* 368 */         parentMap = map;
/* 369 */         String tmp = node.getName();
/* 370 */         int idx = tmp.lastIndexOf(95);
/* 371 */         if (idx == -1) {
/* 372 */           idx = tmp.length();
/*     */         }
/* 374 */         tmp = tmp.substring(0, idx);
/*     */ 
/* 376 */         if ((tmpList = (ArrayList)parentMap.get(tmp)) == null) {
/* 377 */           tmpList = new ArrayList();
/* 378 */           parentMap.put(tmp, tmpList);
/*     */         }
/* 380 */         HiIgnoreHashMap tmpMap = new HiIgnoreHashMap();
/* 381 */         tmpList.add(tmpMap);
/* 382 */         toHashMapList(list1, tmpMap, parentMap);
/*     */       } else {
/* 384 */         String value = node.getValue();
/* 385 */         if ("null".equals(value))
/* 386 */           map.put(node.getName(), "");
/*     */         else
/* 388 */           map.put(node.getName(), value);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static JSONObject toJSON(HiETF etf)
/*     */   {
/* 395 */     return JSONObject.fromObject(etf2HashMapList(etf));
/*     */   }
/*     */ 
/*     */   public static HiETF fromJSONStr(HiETF root, String JSONStr)
/*     */   {
/* 400 */     return fromJSON(root, JSONObject.fromObject(JSONStr));
/*     */   }
/*     */ 
/*     */   public static HiETF fromJSONStr(String jsonStr) {
/* 404 */     return fromJSON(JSONObject.fromObject(jsonStr));
/*     */   }
/*     */ 
/*     */   public static HiETF fromJSON(JSONObject object) {
/* 408 */     HiETF root = HiETFFactory.createETF();
/* 409 */     Iterator iter = object.keys();
/* 410 */     while (iter.hasNext()) {
/* 411 */       String key = (String)iter.next();
/* 412 */       Object o = object.get(key);
/* 413 */       if (o instanceof JSONObject) {
/* 414 */         fromJSON(root, object.getJSONObject(key));
/* 415 */       } else if (o instanceof JSONArray) {
/* 416 */         JSONArray array = (JSONArray)o;
/* 417 */         for (int i = 0; i < array.size(); ++i) {
/* 418 */           HiETF grpNod = root.addNode(key + (i + 1));
/* 419 */           fromJSON(grpNod, array.getJSONObject(i));
/*     */         }
/*     */       } else {
/* 422 */         root.setChildValue(key, o.toString());
/*     */       }
/*     */     }
/* 425 */     return root;
/*     */   }
/*     */ 
/*     */   public static HiETF fromJSON(HiETF root, JSONObject object) {
/* 429 */     Iterator iter = object.keys();
/* 430 */     while (iter.hasNext()) {
/* 431 */       String key = (String)iter.next();
/* 432 */       Object o = object.get(key);
/* 433 */       if (o instanceof JSONObject) {
/* 434 */         HiETF grpNod = root.addNode(key);
/* 435 */         fromJSON(root, object);
/*     */       } else {
/* 437 */         root.setChildValue(key, o.toString());
/*     */       }
/*     */     }
/* 440 */     return null;
/*     */   }
/*     */ }