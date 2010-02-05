package com.hisun.framework.parser;

import com.hisun.framework.HiDefaultServer;
import com.hisun.framework.HiFrameworkBuilder;
import org.apache.commons.digester.AbstractObjectCreationFactory;
import org.xml.sax.Attributes;

public class HiServerFactory extends AbstractObjectCreationFactory {
    public Object createObject(Attributes attributes) throws Exception {
        HiDefaultServer server = new HiDefaultServer();

        server.setName(attributes.getValue("name"));
        server.setType(attributes.getValue("type"));
        String trace = attributes.getValue("trace");
        if (trace != null) {
            server.setTrace(trace);
        }

        server.startBuild();

        return HiFrameworkBuilder.getObjectDecorator().decorate(server, "addDeclare");
    }
}