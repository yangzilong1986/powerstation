/*      */ package com.hisun.message;
/*      */ 
/*      */ import com.hisun.exception.HiException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.StringTokenizer;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.lang.math.NumberUtils;
/*      */ import org.dom4j.DocumentException;
/*      */ import org.dom4j.DocumentHelper;
/*      */ import org.dom4j.Element;
/*      */ import org.dom4j.Node;
/*      */ import org.dom4j.QName;
/*      */ import org.dom4j.io.DOMWriter;
/*      */ 
/*      */ public class HiXmlETF
/*      */   implements HiETF
/*      */ {
/*      */   private static final long serialVersionUID = 3975875382709996694L;
/*   45 */   private Element node = null;
/*      */ 
/*      */   public HiXmlETF()
/*      */   {
/*   53 */     this.node = DocumentHelper.createDocument().addElement("ROOT");
/*      */   }
/*      */ 
/*      */   public HiXmlETF(String name, String value)
/*      */   {
/*   66 */     createNode(name, value);
/*      */   }
/*      */ 
/*      */   public HiXmlETF(String text)
/*      */     throws HiException
/*      */   {
/*      */     try
/*      */     {
/*   81 */       this.node = DocumentHelper.parseText(text).getRootElement();
/*      */     }
/*      */     catch (DocumentException e)
/*      */     {
/*   85 */       throw new HiException("215012", text, e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public HiXmlETF(Element node)
/*      */   {
/*   92 */     this.node = node;
/*      */   }
/*      */ 
/*      */   private Element getNodeCopy()
/*      */   {
/*   97 */     return this.node.createCopy();
/*      */   }
/*      */ 
/*      */   public Element getNode()
/*      */   {
/*  102 */     return this.node;
/*      */   }
/*      */ 
/*      */   private void createNode(String name, String value)
/*      */   {
/*  107 */     this.node = DocumentHelper.createDocument().addElement(name.toUpperCase());
/*      */ 
/*  109 */     if (!(StringUtils.isNotEmpty(value))) {
/*      */       return;
/*      */     }
/*  112 */     setText(this.node, value);
/*      */   }
/*      */ 
/*      */   private List createEtfList(List nodeList)
/*      */   {
/*  118 */     List etfList = new ArrayList();
/*  119 */     for (Iterator iter = nodeList.iterator(); iter.hasNext(); )
/*      */     {
/*  121 */       etfList.add(new HiXmlETF((Element)iter.next()));
/*      */     }
/*  123 */     return etfList;
/*      */   }
/*      */ 
/*      */   public boolean isNullNode()
/*      */   {
/*  133 */     return (this.node == null);
/*      */   }
/*      */ 
/*      */   public HiETF addNode(String name)
/*      */   {
/*  146 */     return addNodeBase(name.toUpperCase(), "");
/*      */   }
/*      */ 
/*      */   public HiETF addNode(String name, String value)
/*      */   {
/*  160 */     return addNodeBase(name.toUpperCase(), value);
/*      */   }
/*      */ 
/*      */   public HiETF addNodeBase(String name, String value)
/*      */   {
/*  175 */     if ((StringUtils.isEmpty(name)) || (this.node == null))
/*      */     {
/*  177 */       return null;
/*      */     }
/*      */ 
/*  180 */     Element curNode = this.node;
/*  181 */     Element posNode = null;
/*      */ 
/*  184 */     int pos = StringUtils.indexOf(name, ".");
/*      */ 
/*  186 */     while (pos >= 0)
/*      */     {
/*  188 */       String item = StringUtils.substring(name, 0, pos);
/*      */ 
/*  190 */       if (item.length() > 0)
/*      */       {
/*  192 */         posNode = curNode.element(item);
/*      */ 
/*  194 */         if (posNode == null)
/*      */         {
/*  196 */           curNode = curNode.addElement(item);
/*      */         }
/*      */         else
/*      */         {
/*  200 */           curNode = posNode;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  207 */       name = StringUtils.substring(name, pos + 1);
/*  208 */       pos = StringUtils.indexOf(name, ".");
/*      */     }
/*      */ 
/*  211 */     if (name.length() > 0)
/*      */     {
/*  213 */       curNode = curNode.addElement(name);
/*  214 */       if (StringUtils.isNotEmpty(value))
/*      */       {
/*  217 */         setText(curNode, value);
/*      */       }
/*  219 */       return new HiXmlETF(curNode);
/*      */     }
/*      */ 
/*  222 */     return null;
/*      */   }
/*      */ 
/*      */   public HiETF addNodeBase(String name, String value, String prefix, String uri)
/*      */   {
/*  240 */     if (StringUtils.isBlank(prefix))
/*      */     {
/*  242 */       return addNodeBase(name, value);
/*      */     }
/*      */ 
/*  245 */     if ((StringUtils.isEmpty(name)) || (this.node == null))
/*      */     {
/*  247 */       return null;
/*      */     }
/*      */ 
/*  250 */     if (name.endsWith("."))
/*      */     {
/*  252 */       return null;
/*      */     }
/*      */ 
/*  255 */     Element curNode = this.node;
/*  256 */     Element posNode = null;
/*      */ 
/*  259 */     int pos = StringUtils.indexOf(name, ".");
/*      */ 
/*  261 */     while (pos >= 0)
/*      */     {
/*  263 */       String item = StringUtils.substring(name, 0, pos);
/*      */ 
/*  265 */       if (item.length() > 0)
/*      */       {
/*  267 */         posNode = curNode.element(QName.get(item, prefix, uri));
/*      */ 
/*  269 */         if (posNode == null)
/*      */         {
/*  271 */           curNode = curNode.addElement(QName.get(item, prefix, uri));
/*      */         }
/*      */         else
/*      */         {
/*  275 */           curNode = posNode;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  282 */       name = StringUtils.substring(name, pos + 1);
/*  283 */       pos = StringUtils.indexOf(name, ".");
/*      */     }
/*      */ 
/*  286 */     if (name.length() > 0)
/*      */     {
/*  288 */       curNode = curNode.addElement(QName.get(name, prefix, uri));
/*  289 */       if (StringUtils.isNotEmpty(value))
/*      */       {
/*  292 */         setText(curNode, value);
/*      */       }
/*  294 */       return new HiXmlETF(curNode);
/*      */     }
/*      */ 
/*  297 */     return null;
/*      */   }
/*      */ 
/*      */   public HiETF appendNode(String name)
/*      */   {
/*  310 */     return appendNode(name, "");
/*      */   }
/*      */ 
/*      */   public HiETF appendNode(String name, String value)
/*      */   {
/*  324 */     if (StringUtils.isEmpty(name))
/*      */     {
/*  326 */       return null;
/*      */     }
/*      */ 
/*  329 */     if (this.node == null)
/*      */     {
/*  331 */       return null;
/*      */     }
/*      */ 
/*  334 */     name = name.toUpperCase();
/*      */ 
/*  336 */     Element curNode = null;
/*  337 */     curNode = this.node.addElement(name);
/*      */ 
/*  339 */     if (StringUtils.isNotEmpty(value))
/*      */     {
/*  342 */       setText(curNode, value);
/*      */     }
/*  344 */     return new HiXmlETF(curNode);
/*      */   }
/*      */ 
/*      */   public HiETF appendNode(HiETF child)
/*      */   {
/*  356 */     if ((isNullNode()) || (child == null) || (child.isNullNode()))
/*      */     {
/*  358 */       return null;
/*      */     }
/*      */ 
/*  361 */     Element childNode = ((HiXmlETF)child).getNodeCopy();
/*  362 */     this.node.add(childNode);
/*      */ 
/*  364 */     return new HiXmlETF(childNode);
/*      */   }
/*      */ 
/*      */   public HiETF getChildNode(HiETF refChild, String child)
/*      */   {
/*  376 */     child = child.toUpperCase();
/*      */ 
/*  379 */     if (this.node == null)
/*      */     {
/*  381 */       return null;
/*      */     }
/*  383 */     if (refChild == null)
/*      */     {
/*  385 */       return getChildNode(child);
/*      */     }
/*      */ 
/*  388 */     int pos = this.node.indexOf(((HiXmlETF)refChild).getNode());
/*  389 */     if (pos < 0)
/*      */     {
/*  391 */       return null;
/*      */     }
/*      */ 
/*  396 */     int nodeCount = this.node.nodeCount();
/*      */ 
/*  398 */     for (int i = pos + 1; i < nodeCount; ++i)
/*      */     {
/*  400 */       Node nodeItem = this.node.node(i);
/*  401 */       if (!(nodeItem instanceof Element))
/*      */         continue;
/*  403 */       Element childNode = (Element)nodeItem;
/*  404 */       if (childNode.getName().equals(child))
/*      */       {
/*  406 */         return new HiXmlETF(childNode);
/*      */       }
/*      */     }
/*      */ 
/*  410 */     return null;
/*      */   }
/*      */ 
/*      */   public HiETF getChildNode(String child)
/*      */   {
/*  422 */     return getChildNodeBase(child.toUpperCase());
/*      */   }
/*      */ 
/*      */   public HiETF getChildNodeBase(String child)
/*      */   {
/*  434 */     if (this.node == null)
/*      */     {
/*  436 */       return null;
/*      */     }
/*      */ 
/*  439 */     Element childNode = this.node.element(child);
/*  440 */     if (childNode == null)
/*      */     {
/*  442 */       return null;
/*      */     }
/*  444 */     return new HiXmlETF(childNode);
/*      */   }
/*      */ 
/*      */   public HiETF getChildNodeBase(String child, String prefix, String uri)
/*      */   {
/*  459 */     if (this.node == null)
/*      */     {
/*  461 */       return null;
/*      */     }
/*      */ 
/*  464 */     Element childNode = this.node.element(QName.get(child, prefix, uri));
/*  465 */     if (childNode == null)
/*      */     {
/*  467 */       return null;
/*      */     }
/*  469 */     return new HiXmlETF(childNode);
/*      */   }
/*      */ 
/*      */   public List getChildNodes(String child)
/*      */   {
/*  481 */     return getChildNodesBase(child.toUpperCase());
/*      */   }
/*      */ 
/*      */   public List getChildNodesBase(String child)
/*      */   {
/*  492 */     if (this.node == null)
/*      */     {
/*  494 */       return null;
/*      */     }
/*      */ 
/*  497 */     return createEtfList(this.node.elements(child));
/*      */   }
/*      */ 
/*      */   public List getChildNodesBase(String child, String prefix, String uri)
/*      */   {
/*  512 */     if (StringUtils.isBlank(prefix))
/*      */     {
/*  514 */       return getChildNodesBase(child);
/*      */     }
/*      */ 
/*  517 */     if (this.node == null)
/*      */     {
/*  519 */       return null;
/*      */     }
/*      */ 
/*  522 */     return createEtfList(this.node.elements(QName.get(child, prefix, uri)));
/*      */   }
/*      */ 
/*      */   public List getGrandChildNodesBase(String grandChild)
/*      */   {
/*  534 */     grandChild = StringUtils.removeStart(grandChild, ".");
/*      */ 
/*  537 */     if (!(StringUtils.contains(grandChild, '.')))
/*      */     {
/*  539 */       return getChildNodesBase(grandChild);
/*      */     }
/*      */ 
/*  542 */     if ((this.node == null) || (StringUtils.isEmpty(grandChild)))
/*      */     {
/*  544 */       return null;
/*      */     }
/*      */ 
/*  547 */     HiETF curParent = getGrandChildNodeBase(StringUtils.substringBeforeLast(grandChild, "."));
/*      */ 
/*  550 */     if (curParent == null)
/*      */     {
/*  552 */       return null;
/*      */     }
/*      */ 
/*  555 */     String curChild = StringUtils.substringAfterLast(grandChild, ".");
/*      */ 
/*  557 */     return curParent.getChildNodesBase(curChild);
/*      */   }
/*      */ 
/*      */   public List getGrandChildNodesBase(String grandChild, String prefix, String uri)
/*      */   {
/*  573 */     grandChild = StringUtils.removeStart(grandChild, ".");
/*      */ 
/*  576 */     if (!(StringUtils.contains(grandChild, '.')))
/*      */     {
/*  578 */       return getChildNodesBase(grandChild, prefix, uri);
/*      */     }
/*      */ 
/*  581 */     if ((this.node == null) || (StringUtils.isEmpty(grandChild)))
/*      */     {
/*  583 */       return null;
/*      */     }
/*      */ 
/*  587 */     HiETF curParent = getGrandChildNodeBase(StringUtils.substringBeforeLast(grandChild, "."));
/*      */ 
/*  590 */     if (curParent == null)
/*      */     {
/*  592 */       return null;
/*      */     }
/*      */ 
/*  595 */     String curChild = StringUtils.substringAfterLast(grandChild, ".");
/*      */ 
/*  597 */     return curParent.getChildNodesBase(curChild, prefix, uri);
/*      */   }
/*      */ 
/*      */   public List getChildNodes()
/*      */   {
/*  607 */     if (this.node == null)
/*      */     {
/*  609 */       return null;
/*      */     }
/*      */ 
/*  612 */     return createEtfList(this.node.elements());
/*      */   }
/*      */ 
/*      */   public List getChildFuzzyEnd(String fuzzyChild)
/*      */   {
/*  624 */     if ((this.node == null) || (StringUtils.isEmpty(fuzzyChild)))
/*      */     {
/*  626 */       return null;
/*      */     }
/*      */ 
/*  629 */     return getChildFuzzyEndBase(fuzzyChild.toUpperCase());
/*      */   }
/*      */ 
/*      */   public List getChildFuzzyEndBase(String fuzzyChild)
/*      */   {
/*  641 */     if ((this.node == null) || (StringUtils.isEmpty(fuzzyChild)))
/*      */     {
/*  643 */       return null;
/*      */     }
/*      */ 
/*  646 */     List childResult = new ArrayList();
/*      */ 
/*  648 */     for (Iterator iter = this.node.elements().iterator(); iter.hasNext(); )
/*      */     {
/*  650 */       Element childNode = (Element)iter.next();
/*  651 */       if (!(childNode.getName().startsWith(fuzzyChild)))
/*      */         continue;
/*  653 */       childResult.add(childNode);
/*      */     }
/*      */ 
/*  657 */     return createEtfList(childResult);
/*      */   }
/*      */ 
/*      */   public List getGrandChildFuzzyEndBase(String fuzzyGrandChild)
/*      */   {
/*  671 */     if ((this.node == null) || (StringUtils.isEmpty(fuzzyGrandChild)))
/*      */     {
/*  673 */       return null;
/*      */     }
/*      */ 
/*  676 */     fuzzyGrandChild = StringUtils.removeStart(fuzzyGrandChild, ".");
/*      */ 
/*  679 */     if (!(StringUtils.contains(fuzzyGrandChild, '.')))
/*      */     {
/*  681 */       return getChildFuzzyEnd(fuzzyGrandChild);
/*      */     }
/*      */ 
/*  684 */     HiETF curParent = getGrandChildNode(StringUtils.substringBeforeLast(fuzzyGrandChild, "."));
/*      */ 
/*  687 */     if (curParent == null)
/*      */     {
/*  689 */       return null;
/*      */     }
/*      */ 
/*  692 */     String curChild = StringUtils.substringAfterLast(fuzzyGrandChild, ".");
/*      */ 
/*  694 */     return curParent.getChildFuzzyEnd(curChild);
/*      */   }
/*      */ 
/*      */   public List getGrandChildFuzzyEnd(String fuzzyGrandChild)
/*      */   {
/*  708 */     if (StringUtils.isEmpty(fuzzyGrandChild))
/*      */     {
/*  710 */       return null;
/*      */     }
/*      */ 
/*  713 */     return getGrandChildFuzzyEndBase(fuzzyGrandChild.toUpperCase());
/*      */   }
/*      */ 
/*      */   public HiETF getGrandChildByCur(String grandChild)
/*      */   {
/*  726 */     if ((this.node == null) || (StringUtils.isEmpty(grandChild)))
/*      */     {
/*  728 */       return null;
/*      */     }
/*      */ 
/*  731 */     if (!(StringUtils.contains(grandChild, '.')))
/*      */     {
/*  733 */       return getChildNode(grandChild);
/*      */     }
/*      */ 
/*  737 */     if (grandChild.endsWith("."))
/*      */     {
/*  739 */       return null;
/*      */     }
/*      */ 
/*  742 */     grandChild = grandChild.toUpperCase();
/*      */ 
/*  744 */     Element curNode = this.node;
/*  745 */     Element posNode = null;
/*      */ 
/*  748 */     int pos = StringUtils.indexOf(grandChild, ".");
/*      */ 
/*  750 */     while (pos >= 0)
/*      */     {
/*  752 */       String item = StringUtils.substring(grandChild, 0, pos);
/*      */ 
/*  754 */       if (item.length() > 0)
/*      */       {
/*  756 */         posNode = curNode.element(item);
/*      */ 
/*  758 */         if (posNode == null)
/*      */         {
/*  760 */           return null;
/*      */         }
/*      */ 
/*  764 */         curNode = posNode;
/*      */       }
/*      */ 
/*  767 */       grandChild = StringUtils.substring(grandChild, pos + 1);
/*  768 */       pos = StringUtils.indexOf(grandChild, ".");
/*      */     }
/*      */ 
/*  771 */     if (grandChild.length() > 0)
/*      */     {
/*  773 */       curNode = curNode.element(grandChild);
/*      */ 
/*  775 */       if (curNode == null)
/*      */       {
/*  777 */         return null;
/*      */       }
/*  779 */       return new HiXmlETF(curNode);
/*      */     }
/*      */ 
/*  782 */     return null;
/*      */   }
/*      */ 
/*      */   public void setGrandChildByCur(String grandChild, String value)
/*      */   {
/*  799 */     if ((this.node == null) || (StringUtils.isEmpty(grandChild)))
/*      */     {
/*  801 */       return;
/*      */     }
/*      */ 
/*  804 */     if (!(StringUtils.contains(grandChild, '.')))
/*      */     {
/*  806 */       setChildValue(grandChild, value);
/*  807 */       return;
/*      */     }
/*      */ 
/*  811 */     if (grandChild.endsWith("."))
/*      */     {
/*  813 */       return;
/*      */     }
/*      */ 
/*  816 */     grandChild = grandChild.toUpperCase();
/*      */ 
/*  818 */     Element curNode = this.node;
/*  819 */     Element posNode = null;
/*  820 */     String item = "";
/*      */ 
/*  822 */     StringTokenizer tokenizer = new StringTokenizer(grandChild, ".");
/*  823 */     while (tokenizer.hasMoreElements())
/*      */     {
/*  825 */       item = tokenizer.nextToken();
/*      */ 
/*  827 */       if (item.length() <= 0)
/*      */         continue;
/*  829 */       posNode = curNode.element(item);
/*      */ 
/*  831 */       if (posNode == null)
/*      */       {
/*  833 */         curNode = curNode.addElement(item);
/*      */       }
/*      */ 
/*  837 */       curNode = posNode;
/*      */     }
/*      */ 
/*  841 */     if (value == null) {
/*      */       return;
/*      */     }
/*  844 */     setText(curNode, value);
/*      */   }
/*      */ 
/*      */   public HiETF getGrandChildNode(String grandChild)
/*      */   {
/*  860 */     return getGrandChildNodeBase(grandChild.toUpperCase());
/*      */   }
/*      */ 
/*      */   public void setGrandChildNode(String grandChild, String value)
/*      */   {
/*  878 */     setGrandChildNodeBase(grandChild.toUpperCase(), value);
/*      */   }
/*      */ 
/*      */   public HiETF getGrandChildNodeBase(String grandChild)
/*      */   {
/*  894 */     if ((this.node == null) || (StringUtils.isEmpty(grandChild)))
/*      */     {
/*  896 */       return null;
/*      */     }
/*      */ 
/*  900 */     if (!(StringUtils.contains(grandChild, '.')))
/*      */     {
/*  902 */       return getChildNodeBase(grandChild);
/*      */     }
/*      */ 
/*  906 */     if (grandChild.endsWith("."))
/*      */     {
/*  908 */       return null;
/*      */     }
/*      */ 
/*  911 */     Element curNode = this.node;
/*  912 */     Element posNode = null;
/*  913 */     String item = "";
/*      */ 
/*  915 */     if (grandChild.startsWith("ROOT."))
/*      */     {
/*  917 */       curNode = this.node.getDocument().getRootElement();
/*  918 */       grandChild = StringUtils.substringAfter(grandChild, ".");
/*      */     }
/*      */ 
/*  921 */     StringTokenizer tokenizer = new StringTokenizer(grandChild, ".");
/*  922 */     while (tokenizer.hasMoreElements())
/*      */     {
/*  924 */       item = tokenizer.nextToken();
/*      */ 
/*  926 */       if (item.length() <= 0)
/*      */         continue;
/*  928 */       int idx = NumberUtils.toInt(item);
/*  929 */       if (idx > 0) {
/*  930 */         List list = curNode.elements();
/*  931 */         if (idx > list.size())
/*  932 */           posNode = null;
/*      */         else
/*  934 */           posNode = (Element)list.get(idx - 1);
/*      */       }
/*      */       else {
/*  937 */         posNode = curNode.element(item);
/*      */       }
/*      */ 
/*  940 */       if (posNode == null)
/*      */       {
/*  942 */         return null;
/*      */       }
/*      */ 
/*  946 */       curNode = posNode;
/*      */     }
/*      */ 
/*  951 */     return new HiXmlETF(curNode);
/*      */   }
/*      */ 
/*      */   public void setGrandChildNodeBase(String grandChild, String value)
/*      */   {
/*  968 */     if ((this.node == null) || (StringUtils.isEmpty(grandChild)))
/*      */     {
/*  970 */       return;
/*      */     }
/*      */ 
/*  974 */     if (!(StringUtils.contains(grandChild, '.')))
/*      */     {
/*  976 */       setChildValueBase(grandChild, value);
/*  977 */       return;
/*      */     }
/*      */ 
/*  981 */     if (grandChild.endsWith("."))
/*      */     {
/*  983 */       return;
/*      */     }
/*      */ 
/*  986 */     Element curNode = this.node;
/*  987 */     Element posNode = null;
/*  988 */     String item = "";
/*      */ 
/*  990 */     if (grandChild.startsWith("ROOT."))
/*      */     {
/*  992 */       curNode = this.node.getDocument().getRootElement();
/*  993 */       grandChild = StringUtils.substringAfter(grandChild, ".");
/*      */     }
/*      */ 
/*  996 */     StringTokenizer tokenizer = new StringTokenizer(grandChild, ".");
/*  997 */     while (tokenizer.hasMoreElements())
/*      */     {
/*  999 */       item = tokenizer.nextToken();
/*      */ 
/* 1001 */       if (item.length() <= 0) {
/*      */         continue;
/*      */       }
/* 1004 */       posNode = curNode.element(item);
/*      */ 
/* 1006 */       if (posNode == null)
/*      */       {
/* 1008 */         curNode = curNode.addElement(item);
/*      */       }
/*      */ 
/* 1012 */       curNode = posNode;
/*      */     }
/*      */ 
/* 1017 */     if (value == null) {
/*      */       return;
/*      */     }
/* 1020 */     setText(curNode, value);
/*      */   }
/*      */ 
/*      */   public HiETF getGrandChildNodeBase(String grandChild, String prefix, String uri)
/*      */   {
/* 1039 */     if (StringUtils.isBlank(prefix))
/*      */     {
/* 1041 */       return getGrandChildNodeBase(grandChild);
/*      */     }
/*      */ 
/* 1044 */     if ((this.node == null) || (StringUtils.isEmpty(grandChild)))
/*      */     {
/* 1046 */       return null;
/*      */     }
/*      */ 
/* 1050 */     if (!(StringUtils.contains(grandChild, '.')))
/*      */     {
/* 1052 */       return getChildNodeBase(grandChild, prefix, uri);
/*      */     }
/*      */ 
/* 1056 */     if (grandChild.endsWith("."))
/*      */     {
/* 1058 */       return null;
/*      */     }
/*      */ 
/* 1061 */     Element curNode = this.node;
/* 1062 */     Element posNode = null;
/* 1063 */     String item = "";
/*      */ 
/* 1065 */     if (grandChild.startsWith("ROOT."))
/*      */     {
/* 1067 */       curNode = this.node.getDocument().getRootElement();
/* 1068 */       grandChild = StringUtils.substringAfter(grandChild, ".");
/*      */     }
/*      */ 
/* 1071 */     StringTokenizer tokenizer = new StringTokenizer(grandChild, ".");
/* 1072 */     while (tokenizer.hasMoreElements())
/*      */     {
/* 1074 */       item = tokenizer.nextToken();
/*      */ 
/* 1076 */       if (item.length() <= 0)
/*      */         continue;
/* 1078 */       posNode = curNode.element(QName.get(item, prefix, uri));
/*      */ 
/* 1080 */       if (posNode == null)
/*      */       {
/* 1082 */         return null;
/*      */       }
/*      */ 
/* 1086 */       curNode = posNode;
/*      */     }
/*      */ 
/* 1091 */     return new HiXmlETF(curNode);
/*      */   }
/*      */ 
/*      */   public HiETF getFirstChild()
/*      */   {
/* 1100 */     if (this.node == null)
/*      */     {
/* 1102 */       return null;
/*      */     }
/*      */ 
/* 1105 */     List listChild = this.node.elements();
/* 1106 */     if (listChild.size() == 0)
/*      */     {
/* 1108 */       return null;
/*      */     }
/* 1110 */     return new HiXmlETF((Element)listChild.get(0));
/*      */   }
/*      */ 
/*      */   public HiETF getNext()
/*      */   {
/* 1120 */     if ((this.node == null) || (this.node.isRootElement()))
/*      */     {
/* 1122 */       return null;
/*      */     }
/*      */ 
/* 1125 */     Element parent = this.node.getParent();
/* 1126 */     int pos = parent.indexOf(this.node);
/*      */ 
/* 1128 */     if (parent.nodeCount() <= pos + 1)
/*      */     {
/* 1130 */       return null;
/*      */     }
/*      */ 
/* 1133 */     return new HiXmlETF((Element)parent.node(pos + 1));
/*      */   }
/*      */ 
/*      */   public String getName()
/*      */   {
/* 1143 */     if (this.node == null)
/*      */     {
/* 1145 */       return null;
/*      */     }
/* 1147 */     return this.node.getName();
/*      */   }
/*      */ 
/*      */   public void setName(String name)
/*      */   {
/*      */     try
/*      */     {
/* 1160 */       this.node.setName(name.toUpperCase());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getValue()
/*      */   {
/* 1175 */     if (this.node == null)
/*      */     {
/* 1177 */       return null;
/*      */     }
/* 1179 */     return this.node.getText();
/*      */   }
/*      */ 
/*      */   public void setValue(String value)
/*      */   {
/*      */     try
/*      */     {
/* 1192 */       if (value != null)
/*      */       {
/* 1194 */         setText(this.node, value);
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getAttrValue(String attrName)
/*      */   {
/* 1212 */     if (this.node == null)
/*      */     {
/* 1214 */       return null;
/*      */     }
/* 1216 */     return this.node.attributeValue(attrName);
/*      */   }
/*      */ 
/*      */   public void setAttrValue(String attrName, String attrVal)
/*      */   {
/* 1229 */     if (this.node == null)
/*      */     {
/* 1231 */       return;
/*      */     }
/* 1233 */     this.node.addAttribute(attrName, attrVal);
/*      */   }
/*      */ 
/*      */   public String getAttrValue(String attrName, String prefix, String uri)
/*      */   {
/* 1248 */     if (this.node == null)
/*      */     {
/* 1250 */       return null;
/*      */     }
/* 1252 */     if ((StringUtils.isBlank(prefix)) || (StringUtils.isBlank(uri)))
/*      */     {
/* 1254 */       return this.node.attributeValue(attrName);
/*      */     }
/* 1256 */     return this.node.attributeValue(QName.get(attrName, prefix, uri));
/*      */   }
/*      */ 
/*      */   public void setAttrValue(String attrName, String attrVal, String prefix, String uri)
/*      */   {
/* 1270 */     if (this.node == null)
/*      */     {
/* 1272 */       return;
/*      */     }
/* 1274 */     if ((StringUtils.isBlank(prefix)) || (StringUtils.isBlank(uri)))
/*      */     {
/* 1276 */       this.node.addAttribute(attrName, attrVal);
/*      */     }
/*      */     else
/*      */     {
/* 1280 */       this.node.addAttribute(QName.get(attrName, prefix, uri), attrVal);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setChildValue(String child, String value)
/*      */   {
/* 1294 */     setChildValueBase(child.toUpperCase(), value);
/*      */   }
/*      */ 
/*      */   public void setChildValueBase(String child, String value)
/*      */   {
/* 1307 */     Element childNode = this.node.element(child);
/* 1308 */     if (childNode == null)
/*      */     {
/* 1310 */       childNode = this.node.addElement(child);
/* 1311 */       if (value == null) {
/*      */         return;
/*      */       }
/* 1314 */       setText(childNode, value);
/*      */     }
/*      */     else
/*      */     {
/* 1319 */       if (value == null) {
/*      */         return;
/*      */       }
/*      */ 
/* 1323 */       setText(childNode, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getChildValue(String child)
/*      */   {
/* 1337 */     return getChildValueBase(child.toUpperCase());
/*      */   }
/*      */ 
/*      */   public String getChildValueBase(String child)
/*      */   {
/* 1349 */     Element childNode = this.node.element(child);
/* 1350 */     if (childNode == null)
/*      */     {
/* 1352 */       return null;
/*      */     }
/* 1354 */     return childNode.getText();
/*      */   }
/*      */ 
/*      */   public String getGrandChildValue(String grandChild)
/*      */   {
/* 1369 */     return getGrandChildValueBase(grandChild.toUpperCase());
/*      */   }
/*      */ 
/*      */   public String getGrandChildValueBase(String grandChild)
/*      */   {
/* 1384 */     if ((this.node == null) || (StringUtils.isEmpty(grandChild)))
/*      */     {
/* 1386 */       return null;
/*      */     }
/*      */ 
/* 1390 */     if (!(StringUtils.contains(grandChild, '.')))
/*      */     {
/* 1392 */       return getChildValueBase(grandChild);
/*      */     }
/*      */ 
/* 1396 */     if (grandChild.endsWith("."))
/*      */     {
/* 1398 */       return null;
/*      */     }
/*      */ 
/* 1401 */     Element curNode = this.node;
/* 1402 */     Element posNode = null;
/* 1403 */     String item = "";
/*      */ 
/* 1405 */     if (grandChild.startsWith("ROOT."))
/*      */     {
/* 1407 */       curNode = this.node.getDocument().getRootElement();
/* 1408 */       grandChild = StringUtils.substringAfter(grandChild, ".");
/*      */     }
/*      */ 
/* 1411 */     StringTokenizer tokenizer = new StringTokenizer(grandChild, ".");
/* 1412 */     while (tokenizer.hasMoreElements())
/*      */     {
/* 1414 */       item = tokenizer.nextToken();
/*      */ 
/* 1416 */       if (item.length() <= 0)
/*      */         continue;
/* 1418 */       int idx = NumberUtils.toInt(item);
/* 1419 */       if (idx > 0) {
/* 1420 */         List list = curNode.elements();
/* 1421 */         if (idx > list.size())
/* 1422 */           posNode = null;
/*      */         else
/* 1424 */           posNode = (Element)list.get(idx - 1);
/*      */       }
/*      */       else {
/* 1427 */         posNode = curNode.element(item);
/*      */       }
/*      */ 
/* 1430 */       if (posNode == null)
/*      */       {
/* 1432 */         return null;
/*      */       }
/*      */ 
/* 1436 */       curNode = posNode;
/*      */     }
/*      */ 
/* 1441 */     return curNode.getText();
/*      */   }
/*      */ 
/*      */   public HiETF getRootNode()
/*      */   {
/* 1451 */     if (this.node == null)
/*      */     {
/* 1453 */       return null;
/*      */     }
/*      */ 
/* 1456 */     return new HiXmlETF(this.node.getDocument().getRootElement());
/*      */   }
/*      */ 
/*      */   public HiETF getParent()
/*      */   {
/* 1466 */     if (this.node == null)
/*      */     {
/* 1468 */       return null;
/*      */     }
/* 1470 */     Element parent = this.node.getParent();
/* 1471 */     if (parent == null)
/*      */     {
/* 1473 */       return null;
/*      */     }
/*      */ 
/* 1477 */     return new HiXmlETF(parent);
/*      */   }
/*      */ 
/*      */   public void removeNode()
/*      */   {
/* 1486 */     if (this.node == null)
/*      */     {
/* 1488 */       return;
/*      */     }
/*      */ 
/* 1491 */     if (this.node.isRootElement())
/*      */     {
/* 1493 */       this.node.clearContent();
/* 1494 */       this.node.getDocument().remove(this.node);
/* 1495 */       this.node = null;
/*      */     }
/*      */     else
/*      */     {
/* 1499 */       this.node.clearContent();
/* 1500 */       this.node.getParent().remove(this.node);
/* 1501 */       this.node = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeChildNode(String child)
/*      */   {
/* 1513 */     if ((this.node == null) || (StringUtils.isEmpty(child)))
/*      */     {
/* 1515 */       return;
/*      */     }
/*      */ 
/* 1518 */     this.node.remove(this.node.element(child.toUpperCase()));
/*      */   }
/*      */ 
/*      */   public void removeChildNode(HiETF child)
/*      */   {
/* 1529 */     if ((this.node == null) || (child == null))
/*      */     {
/* 1531 */       return;
/*      */     }
/* 1533 */     this.node.remove(((HiXmlETF)child).getNode());
/*      */   }
/*      */ 
/*      */   public void removeChildNode(int index)
/*      */   {
/* 1544 */     if ((this.node == null) || (index < 0) || (index + 1 > this.node.elements().size()))
/*      */     {
/* 1547 */       return;
/*      */     }
/* 1549 */     this.node.elements().remove(index);
/*      */   }
/*      */ 
/*      */   public void removeChildNodes()
/*      */   {
/* 1558 */     if (this.node == null)
/*      */     {
/* 1560 */       return;
/*      */     }
/* 1562 */     this.node.elements().clear();
/*      */   }
/*      */ 
/*      */   public void removeGrandChild(String grandChild)
/*      */   {
/* 1573 */     if ((this.node == null) || (StringUtils.isEmpty(grandChild)))
/*      */     {
/* 1575 */       return;
/*      */     }
/*      */ 
/* 1578 */     grandChild = grandChild.toUpperCase();
/*      */ 
/* 1580 */     grandChild = StringUtils.removeStart(grandChild, ".");
/*      */ 
/* 1582 */     if (!(StringUtils.contains(grandChild, '.')))
/*      */     {
/* 1584 */       this.node.remove(this.node.element(grandChild));
/* 1585 */       return;
/*      */     }
/*      */ 
/* 1588 */     HiETF curParent = getGrandChildNode(StringUtils.substringBeforeLast(grandChild, "."));
/*      */ 
/* 1591 */     if (curParent == null)
/*      */     {
/* 1593 */       return;
/*      */     }
/* 1595 */     curParent.removeChildNode(StringUtils.substringAfterLast(grandChild, "."));
/*      */   }
/*      */ 
/*      */   public HiETF cloneRootNode()
/*      */   {
/* 1606 */     if (this.node == null)
/*      */     {
/* 1608 */       return null;
/*      */     }
/* 1610 */     return new HiXmlETF(DocumentHelper.createDocument(getNodeCopy()).getRootElement());
/*      */   }
/*      */ 
/*      */   public HiETF cloneNode()
/*      */   {
/* 1620 */     if (this.node == null)
/*      */     {
/* 1622 */       return null;
/*      */     }
/*      */ 
/* 1625 */     return new HiXmlETF(getNodeCopy());
/*      */   }
/*      */ 
/*      */   public void copyTo(HiETF destEtf)
/*      */   {
/* 1630 */     if (destEtf == null)
/*      */       return;
/* 1632 */     ((HiXmlETF)destEtf).node = this.node;
/*      */   }
/*      */ 
/*      */   public boolean combineAll(HiETF etf)
/*      */   {
/* 1645 */     if ((etf == null) || (etf.isNullNode()))
/*      */     {
/* 1647 */       return false;
/*      */     }
/*      */ 
/* 1650 */     Iterator childIt = ((HiXmlETF)etf).getNode().elementIterator();
/* 1651 */     Element childNode = null;
/*      */ 
/* 1653 */     while (childIt.hasNext())
/*      */     {
/* 1655 */       childNode = (Element)childIt.next();
/* 1656 */       this.node.add(childNode.createCopy());
/*      */     }
/* 1658 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean combine(HiETF etf, boolean isReplace)
/*      */   {
/* 1671 */     return combine(etf, isReplace, false);
/*      */   }
/*      */ 
/*      */   public boolean combine(HiETF etf, boolean isReplace, boolean isAppend)
/*      */   {
/* 1686 */     if ((etf == null) || (etf.isNullNode()))
/*      */     {
/* 1688 */       return false;
/*      */     }
/*      */ 
/* 1691 */     Iterator childIt = ((HiXmlETF)etf).getNode().elementIterator();
/* 1692 */     Element childNode = null; Element selfChild = null;
/*      */ 
/* 1695 */     if ((!(isReplace)) && (isAppend))
/*      */     {
/* 1697 */       while (childIt.hasNext())
/*      */       {
/* 1699 */         childNode = (Element)childIt.next();
/* 1700 */         this.node.add(childNode.createCopy());
/*      */       }
/* 1702 */       return true;
/*      */     }
/*      */ 
/* 1706 */     while (childIt.hasNext())
/*      */     {
/* 1708 */       childNode = (Element)childIt.next();
/*      */ 
/* 1710 */       selfChild = this.node.element(childNode.getName());
/* 1711 */       if (selfChild == null)
/*      */       {
/* 1713 */         this.node.add(childNode.createCopy());
/*      */       }
/*      */ 
/* 1717 */       if (!(isReplace))
/*      */         continue;
/* 1719 */       this.node.remove(selfChild);
/* 1720 */       this.node.add(childNode.createCopy());
/*      */     }
/*      */ 
/* 1725 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean isEndNode()
/*      */   {
/* 1737 */     return (this.node.elements().size() > 0);
/*      */   }
/*      */ 
/*      */   public boolean isExist(String childName)
/*      */   {
/* 1744 */     if (this.node == null)
/*      */       break label27;
/* 1746 */     Element t = (Element)this.node.selectSingleNode(childName);
/*      */ 
/* 1749 */     label27: return (t == null);
/*      */   }
/*      */ 
/*      */   public String getXmlString()
/*      */   {
/* 1761 */     return ((this.node == null) ? null : this.node.asXML());
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1766 */     return ((this.node == null) ? null : this.node.asXML());
/*      */   }
/*      */ 
/*      */   public org.w3c.dom.Document toDOMDocument()
/*      */     throws HiException
/*      */   {
/* 1776 */     if (this.node != null)
/*      */     {
/* 1778 */       DOMWriter domWriter = new DOMWriter();
/*      */       try {
/* 1780 */         return domWriter.write(this.node.getDocument());
/*      */       } catch (DocumentException e) {
/* 1782 */         throw new HiException("215011", "ETF", e);
/*      */       }
/*      */     }
/*      */ 
/* 1786 */     return null;
/*      */   }
/*      */ 
/*      */   private void setText(Element node, String value)
/*      */   {
/* 1792 */     node.setText(value);
/*      */   }
/*      */ 
/*      */   public HiETF addArrayNode(String name, int idx) {
/* 1796 */     return addNode(name + "_" + idx);
/*      */   }
/*      */ 
/*      */   public HiETF getArrayChildNode(String child, int idx) {
/* 1800 */     return getChildNode(child + "_" + idx);
/*      */   }
/*      */ }