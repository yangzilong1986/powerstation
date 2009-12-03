package com.hisun.message;

import java.io.Serializable;
import java.util.List;

public abstract interface HiETF extends Serializable
{
  public static final String ROOT_PRE = "ROOT.";

  public abstract boolean isNullNode();

  public abstract HiETF addNode(String paramString);

  public abstract HiETF addArrayNode(String paramString, int paramInt);

  public abstract HiETF addNode(String paramString1, String paramString2);

  public abstract HiETF addNodeBase(String paramString1, String paramString2);

  public abstract HiETF addNodeBase(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract HiETF appendNode(String paramString);

  public abstract HiETF appendNode(String paramString1, String paramString2);

  public abstract HiETF appendNode(HiETF paramHiETF);

  public abstract HiETF getChildNode(HiETF paramHiETF, String paramString);

  public abstract HiETF getChildNode(String paramString);

  public abstract HiETF getArrayChildNode(String paramString, int paramInt);

  public abstract HiETF getChildNodeBase(String paramString);

  public abstract HiETF getChildNodeBase(String paramString1, String paramString2, String paramString3);

  public abstract List getChildNodes(String paramString);

  public abstract List getChildNodesBase(String paramString);

  public abstract List getChildNodesBase(String paramString1, String paramString2, String paramString3);

  public abstract List getGrandChildNodesBase(String paramString1, String paramString2, String paramString3);

  public abstract List getChildNodes();

  public abstract List getChildFuzzyEnd(String paramString);

  public abstract List getChildFuzzyEndBase(String paramString);

  public abstract List getGrandChildFuzzyEnd(String paramString);

  public abstract List getGrandChildFuzzyEndBase(String paramString);

  public abstract HiETF getGrandChildNode(String paramString);

  public abstract void setGrandChildNode(String paramString1, String paramString2);

  public abstract HiETF getGrandChildNodeBase(String paramString);

  public abstract void setGrandChildNodeBase(String paramString1, String paramString2);

  public abstract HiETF getGrandChildNodeBase(String paramString1, String paramString2, String paramString3);

  public abstract String getGrandChildValue(String paramString);

  public abstract String getGrandChildValueBase(String paramString);

  public abstract HiETF getFirstChild();

  public abstract HiETF getNext();

  public abstract String getName();

  public abstract void setName(String paramString);

  public abstract String getValue();

  public abstract void setValue(String paramString);

  public abstract String getAttrValue(String paramString);

  public abstract void setAttrValue(String paramString1, String paramString2);

  public abstract String getAttrValue(String paramString1, String paramString2, String paramString3);

  public abstract void setAttrValue(String paramString1, String paramString2, String paramString3, String paramString4);

  public abstract String getChildValue(String paramString);

  public abstract String getChildValueBase(String paramString);

  public abstract void setChildValue(String paramString1, String paramString2);

  public abstract void setChildValueBase(String paramString1, String paramString2);

  public abstract HiETF getRootNode();

  public abstract HiETF getParent();

  public abstract void removeNode();

  public abstract void removeChildNode(String paramString);

  public abstract void removeChildNode(HiETF paramHiETF);

  public abstract void removeChildNode(int paramInt);

  public abstract void removeChildNodes();

  public abstract void removeGrandChild(String paramString);

  public abstract HiETF cloneNode();

  public abstract HiETF cloneRootNode();

  public abstract void copyTo(HiETF paramHiETF);

  public abstract boolean combineAll(HiETF paramHiETF);

  public abstract boolean combine(HiETF paramHiETF, boolean paramBoolean);

  public abstract String getXmlString();

  public abstract boolean isEndNode();

  public abstract boolean isExist(String paramString);
}