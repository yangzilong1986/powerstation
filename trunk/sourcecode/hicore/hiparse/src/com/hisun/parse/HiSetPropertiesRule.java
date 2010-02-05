package com.hisun.parse;

import com.hisun.message.HiContext;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

import java.util.HashMap;

public class HiSetPropertiesRule extends Rule {
    private String[] attributeNames;
    private String[] propertyNames;
    private boolean ignoreMissingProperty;

    /**
     * @deprecated
     */
    public HiSetPropertiesRule(Digester digester) {
    }

    public HiSetPropertiesRule() {
        this.ignoreMissingProperty = true;
    }

    public HiSetPropertiesRule(String attributeName, String propertyName) {
        this.ignoreMissingProperty = true;

        this.attributeNames = new String[1];
        this.attributeNames[0] = attributeName;
        this.propertyNames = new String[1];
        this.propertyNames[0] = propertyName;
    }

    public HiSetPropertiesRule(String[] attributeNames, String[] propertyNames) {
        this.ignoreMissingProperty = true;

        this.attributeNames = new String[attributeNames.length];
        int i = 0;
        for (int size = attributeNames.length; i < size; ++i) {
            this.attributeNames[i] = attributeNames[i];
        }

        this.propertyNames = new String[propertyNames.length];
        i = 0;
        for (size = propertyNames.length; i < size; ++i)
            this.propertyNames[i] = propertyNames[i];
    }

    public void begin(Attributes attributes) throws Exception {
        HashMap values = new HashMap();

        int attNamesLength = 0;
        if (this.attributeNames != null) {
            attNamesLength = this.attributeNames.length;
        }
        int propNamesLength = 0;
        if (this.propertyNames != null) {
            propNamesLength = this.propertyNames.length;
        }

        for (int i = 0; i < attributes.getLength(); ++i) {
            String name = attributes.getLocalName(i);
            if ("".equals(name)) {
                name = attributes.getQName(i);
            }
            String value = attributes.getValue(i);

            for (int n = 0; n < attNamesLength; ++n) {
                if (name.equals(this.attributeNames[n])) {
                    if (n < propNamesLength) {
                        name = this.propertyNames[n];
                        break;
                    }

                    name = null;

                    break;
                }
            }

            if (this.digester.getLogger().isDebugEnabled()) {
                this.digester.getLogger().debug("[SetPropertiesRule]{" + this.digester.getMatch() + "} Setting property '" + name + "' to '" + value + "'");
            }

            if ((!(this.ignoreMissingProperty)) && (name != null)) {
                Object top = this.digester.peek();
                boolean test = PropertyUtils.isWriteable(top, name);
                if (!(test)) {
                    throw new NoSuchMethodException("Property " + name + " can't be set");
                }
            }
            if (name != null) {
                values.put(name, getRealValue(value));
            }

        }

        Object top = this.digester.peek();
        if (this.digester.getLogger().isDebugEnabled()) {
            if (top != null) {
                this.digester.getLogger().debug("[SetPropertiesRule]{" + this.digester.getMatch() + "} Set " + top.getClass().getName() + " properties");
            } else {
                this.digester.getLogger().debug("[SetPropertiesRule]{" + this.digester.getMatch() + "} Set NULL properties");
            }
        }

        BeanUtils.populate(top, values);
    }

    public void addAlias(String attributeName, String propertyName) {
        if (this.attributeNames == null) {
            this.attributeNames = new String[1];
            this.attributeNames[0] = attributeName;
            this.propertyNames = new String[1];
            this.propertyNames[0] = propertyName;
        } else {
            int length = this.attributeNames.length;
            String[] tempAttributes = new String[length + 1];
            for (int i = 0; i < length; ++i) {
                tempAttributes[i] = this.attributeNames[i];
            }
            tempAttributes[length] = attributeName;

            String[] tempProperties = new String[length + 1];
            for (int i = 0; (i < length) && (i < this.propertyNames.length); ++i) {
                tempProperties[i] = this.propertyNames[i];
            }
            tempProperties[length] = propertyName;

            this.propertyNames = tempProperties;
            this.attributeNames = tempAttributes;
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("SetPropertiesRule[");
        sb.append("]");
        return sb.toString();
    }

    public boolean isIgnoreMissingProperty() {
        return this.ignoreMissingProperty;
    }

    public void setIgnoreMissingProperty(boolean ignoreMissingProperty) {
        this.ignoreMissingProperty = ignoreMissingProperty;
    }

    private String getRealValue(String value) {
        if ((value != null) && (value.indexOf(95) == 0)) {
            return getParamValue(value);
        }
        return value;
    }

    private String getParamValue(String value) {
        HiContext ctx = HiContext.getCurrentContext();
        if (ctx != null) {
            String newValue = ctx.getStrProp("@PARA", value);
            if (newValue != null) return newValue;
        }
        return value;
    }
}