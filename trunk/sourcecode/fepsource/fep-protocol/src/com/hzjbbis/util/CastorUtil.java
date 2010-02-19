package com.hzjbbis.util;

import com.hzjbbis.exception.CastorException;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class CastorUtil {
    public static Object unmarshal(String mappingResource, String dataResource) {
        if (!(mappingResource.startsWith("/"))) {
            mappingResource = "/" + mappingResource;
        }
        if (!(dataResource.startsWith("/"))) {
            dataResource = "/" + dataResource;
        }
        try {
            Mapping mapping = new Mapping();
            InputSource in = new InputSource(CastorUtil.class.getResourceAsStream(mappingResource));
            try {
                mapping.loadMapping(in);
            } finally {
                in.getByteStream().close();
            }

            Unmarshaller unmarshaller = new Unmarshaller(mapping);
            Reader reader = new BufferedReader(new InputStreamReader(CastorUtil.class.getResourceAsStream(dataResource)));
            try {
                Object localObject2 = unmarshaller.unmarshal(reader);

                return localObject2;
            } finally {
                reader.close();
            }
        } catch (Exception ex) {
            String msg = "Error to unmarshal from xml [mappingResource: " + mappingResource + ", dataResource: " + dataResource + "]";

            throw new CastorException(msg, ex);
        }
    }
}