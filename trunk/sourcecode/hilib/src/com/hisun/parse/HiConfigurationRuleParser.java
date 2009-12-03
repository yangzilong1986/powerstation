/*     */ package com.hisun.parse;
/*     */ 
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.apache.commons.digester.AbstractObjectCreationFactory;
/*     */ import org.apache.commons.digester.Digester;
/*     */ import org.apache.commons.digester.NodeCreateRule;
/*     */ import org.apache.commons.digester.Rule;
/*     */ import org.apache.commons.digester.xmlrules.DigesterRuleParser;
/*     */ import org.apache.commons.digester.xmlrules.DigesterRuleParser.PatternStack;
/*     */ import org.xml.sax.Attributes;
/*     */ 
/*     */ public class HiConfigurationRuleParser extends DigesterRuleParser
/*     */ {
/*     */   final String ruleClassName;
/*     */ 
/*     */   public HiConfigurationRuleParser()
/*     */   {
/*  15 */     this.ruleClassName = Rule.class.getName();
/*     */   }
/*     */ 
/*     */   public void addRuleInstances(Digester digester)
/*     */   {
/*  20 */     super.addRuleInstances(digester);
/*     */ 
/*  23 */     digester.addFactoryCreate("*/set-hisun-rule", new ConfigCreateRuleFactory());
/*     */ 
/*  25 */     digester.addRule("*/set-hisun-rule", new PatternRule("pattern"));
/*  26 */     digester.addSetNext("*/set-hisun-rule", "add", this.ruleClassName);
/*     */ 
/*  28 */     digester.addFactoryCreate("*/hset-property-rule", new HiSetPropertyRuleFactory());
/*  29 */     digester.addRule("*/hset-property-rule", new PatternRule("pattern"));
/*  30 */     digester.addSetNext("*/hset-property-rule", "add", this.ruleClassName);
/*     */   }
/*     */ 
/*     */   private class PatternRule extends Rule
/*     */   {
/*     */     private String mAttrName;
/*  83 */     private String mPattern = null;
/*     */ 
/*     */     public PatternRule(String paramString)
/*     */     {
/*  93 */       this.mAttrName = paramString;
/*     */     }
/*     */ 
/*     */     public void begin(String namespace, String name, Attributes aAttrs)
/*     */     {
/* 106 */       this.mPattern = aAttrs.getValue(this.mAttrName);
/* 107 */       if (this.mPattern == null)
/*     */         return;
/* 109 */       HiConfigurationRuleParser.this.patternStack.push(this.mPattern);
/*     */     }
/*     */ 
/*     */     public void end(String namespace, String name)
/*     */     {
/* 119 */       if (this.mPattern == null)
/*     */         return;
/* 121 */       HiConfigurationRuleParser.this.patternStack.pop();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected class HiNodeCreateRuleFactory extends AbstractObjectCreationFactory
/*     */   {
/*     */     public Object createObject(Attributes attributes)
/*     */       throws ParserConfigurationException
/*     */     {
/*  65 */       return new NodeCreateRule();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected class HiSetPropertiesRuleFactory extends AbstractObjectCreationFactory
/*     */   {
/*     */     public Object createObject(Attributes attributes)
/*     */     {
/*  59 */       return new HiSetPropertiesRule();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected class HiSetPropertyRuleFactory extends AbstractObjectCreationFactory
/*     */   {
/*     */     public Object createObject(Attributes attributes)
/*     */       throws Exception
/*     */     {
/*  51 */       String name = attributes.getValue("name");
/*  52 */       String value = attributes.getValue("value");
/*  53 */       return new HiSetPropertyRule(name, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected class ConfigCreateRuleFactory extends AbstractObjectCreationFactory
/*     */   {
/*     */     public Object createObject(Attributes attributes)
/*     */     {
/*  41 */       String methodName = attributes.getValue("methodname");
/*  42 */       String paramType = attributes.getValue("paramtype");
/*  43 */       return new HiRule(methodName, paramType);
/*     */     }
/*     */   }
/*     */ }