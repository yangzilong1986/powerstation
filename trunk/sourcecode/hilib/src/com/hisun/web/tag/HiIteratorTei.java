package com.hisun.web.tag;


import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

public class HiIteratorTei extends TagExtraInfo {
    public VariableInfo[] getVariableInfo(TagData data) {
        VariableInfo[] result;

        VariableInfo[] variables = new VariableInfo[2];


        int counter = 0;


        String id = data.getAttributeString("id");


        String type = "com.hisun.message.HiETF";


        if (id != null) {

            if (type == null) {

                type = "java.lang.Object";
            }


            variables[(counter++)] = new VariableInfo(data.getAttributeString("id"), type, true, 0);
        }


        String indexId = data.getAttributeString("indexId");


        if (indexId != null) {

            variables[(counter++)] = new VariableInfo(indexId, "java.lang.Integer", true, 0);
        }


        if (counter > 0) {

            result = new VariableInfo[counter];

            System.arraycopy(variables, 0, result, 0, counter);
        } else {

            result = new VariableInfo[0];
        }


        return result;
    }
}