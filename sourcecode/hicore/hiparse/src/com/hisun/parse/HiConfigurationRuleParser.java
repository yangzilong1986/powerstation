package com.hisun.parse;

import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.NodeCreateRule;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.xmlrules.DigesterRuleParser;
import org.xml.sax.Attributes;

import javax.xml.parsers.ParserConfigurationException;

public class HiConfigurationRuleParser extends DigesterRuleParser {
    final String ruleClassName;

    public HiConfigurationRuleParser() {
        this.ruleClassName = Rule.class.getName();
    }

    public void addRuleInstances(Digester digester) {
        super.addRuleInstances(digester);

        digester.addFactoryCreate("*/set-hisun-rule", new ConfigCreateRuleFactory());

        digester.addRule("*/set-hisun-rule", new PatternRule("pattern"));
        digester.addSetNext("*/set-hisun-rule", "add", this.ruleClassName);

        digester.addFactoryCreate("*/hset-property-rule", new HiSetPropertyRuleFactory());
        digester.addRule("*/hset-property-rule", new PatternRule("pattern"));
        digester.addSetNext("*/hset-property-rule", "add", this.ruleClassName);
    }

    private class PatternRule extends Rule {
        private String mAttrName;
        private String mPattern = null;

        public PatternRule(String paramString) {
            this.mAttrName = paramString;
        }

        public void begin(String namespace, String name, Attributes aAttrs) {
            this.mPattern = aAttrs.getValue(this.mAttrName);
            if (this.mPattern == null) return;
            HiConfigurationRuleParser.this.patternStack.push(this.mPattern);
        }

        public void end(String namespace, String name) {
            if (this.mPattern == null) return;
            HiConfigurationRuleParser.this.patternStack.pop();
        }
    }

    protected class HiNodeCreateRuleFactory extends AbstractObjectCreationFactory {
        public Object createObject(Attributes attributes) throws ParserConfigurationException {
            return new NodeCreateRule();
        }
    }

    protected class HiSetPropertiesRuleFactory extends AbstractObjectCreationFactory {
        public Object createObject(Attributes attributes) {
            return new HiSetPropertiesRule();
        }
    }

    protected class HiSetPropertyRuleFactory extends AbstractObjectCreationFactory {
        public Object createObject(Attributes attributes) throws Exception {
            String name = attributes.getValue("name");
            String value = attributes.getValue("value");
            return new HiSetPropertyRule(name, value);
        }
    }

    protected class ConfigCreateRuleFactory extends AbstractObjectCreationFactory {
        public Object createObject(Attributes attributes) {
            String methodName = attributes.getValue("methodname");
            String paramType = attributes.getValue("paramtype");
            return new HiRule(methodName, paramType);
        }
    }
}