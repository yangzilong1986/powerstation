package com.hisun.framework.parser;

import com.hisun.framework.HiFrameworkBuilder;
import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.xml.sax.Attributes;

public class HiBeanFactory extends AbstractObjectCreationFactory {
    public Object createObject(Attributes attributes) throws Exception {
        Object instance;
        String className = attributes.getValue("class");
        if (className != null) {
            Class clazz = this.digester.getClassLoader().loadClass(className);
            instance = clazz.newInstance();
            return instance;
        }

        String alias = attributes.getValue("alias");
        if (alias != null) {
            instance = HiFrameworkBuilder.getCommFactory().get(alias);
            if (instance != null) return instance;
        }
        throw new Exception("create object failed!");
    }
}