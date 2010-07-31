package org.pssframework.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.bp.realinterface.mto.CollectObject;
import pep.bp.realinterface.mto.CommandItem;
import pep.bp.realinterface.mto.MTOType;
import pep.bp.realinterface.mto.MTO_376;
import pep.bp.realinterface.mto.MessageTranObject;

/**
 * 
 * @author Zhangyu
 * 
 */
public class ConverterUtils {
    private static Logger logger = LoggerFactory.getLogger(ConverterUtils.class);

    /**
     * jsonString to List<MessageTranObject>
     * 
     * @param jsonString
     * @return
     * @throws Exception
     */
    public static List<MessageTranObject> jsonString2MTOList(String jsonString) throws Exception {
        List<MessageTranObject> mtoList = new ArrayList<MessageTranObject>();
        return mtoList;
    }

    /**
     * 
     * @param mtoType
     * @param jsonString
     * @return
     * @throws Exception
     */
    public static MessageTranObject jsonString2MessageTranObject(String mtoType, String jsonString) throws Exception {
        MessageTranObject mto = null;
        if("100".equals(mtoType)) {
            mto = new MTO_376();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        if(mto != null && MTOType.GW_376.equals(mto.getType())) {
            try {
                mto = objectMapper.readValue(jsonString, MTO_376.class);
            }
            catch(JsonParseException e) {
                logger.error("JsonParseException", e.fillInStackTrace());
            }
            catch(JsonMappingException e) {
                logger.error("JsonMappingException", e.fillInStackTrace());
            }
            catch(IOException e) {
                logger.error("IOException", e.fillInStackTrace());
            }
        }

        return mto;
    }

    /**
     * jsonString to MessageTranObject
     * 
     * @param jsonString
     * @return
     * @throws Exception
     */
    public static MessageTranObject jsonString2MessageTranObject(String jsonString) throws Exception {
        MessageTranObject mto = null;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jnRoot = mapper.readTree(jsonString);
        
        JsonNode jnMTOType = jnRoot.get("mtoType");

        if(jnMTOType == null) {
            logger.error("no mtoType value");
            throw new Exception("no mtoType value");
        }
        if("100".equals(jnMTOType.getTextValue())) {
            mto = new MTO_376();
        }

        if(mto != null && MTOType.GW_376.equals(mto.getType())) {
            MTO_376 mto376 = new MTO_376();
            JsonNode jnCOList = jnRoot.get("coList");
            if(jnCOList == null) {
                logger.error("no mtoType value");
                throw new Exception("no mtoType value");
            }
            logger.debug(jnCOList.toString());

            Iterator<JsonNode> itCOList = jnCOList.getElements();
            int i = 0;
            while(itCOList.hasNext()) {
                i++;
                JsonNode jnCollectObject = itCOList.next();
                logger.debug(i + " : " + jnCollectObject.toString());
                CollectObject co = jsonString2CollectObject(jnCollectObject.toString());
                mto376.addCollectObject(co);
            }
            mto = (MessageTranObject) mto376;
        }

        return mto;
    }

    /**
     * jsonString to CollectObject
     * 
     * @param jsonString
     * @return
     * @throws Exception
     */
    public static CollectObject jsonString2CollectObject(String jsonString) throws Exception {
        CollectObject co = new CollectObject();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readTree(jsonString);
        
        JsonNode jnLogicalAddr = jsonNode.get("logicalAddr");
        JsonNode jnEquipProtocol = jsonNode.get("equipProtocol");
        JsonNode jnChannelType = jsonNode.get("channelType");
        JsonNode jnPwAlgorith = jsonNode.get("pwAlgorith");
        JsonNode jnPwContent = jsonNode.get("pwContent");
        JsonNode jnMpExpressMode = jsonNode.get("mpExpressMode");
        JsonNode jnMpSn = jsonNode.get("mpSn");
        JsonNode jnCommandItems = jsonNode.get("commandItems");

        if(jnLogicalAddr == null) {
            logger.error("no logicalAddr value");
            throw new Exception("no logicalAddr value");
        }
        co.setLogicalAddr(jnLogicalAddr.getTextValue());

        if(jnEquipProtocol == null) {
            logger.error("no equipProtocol value");
            throw new Exception("no equipProtocol value");
        }
        co.setEquipProtocol(jnEquipProtocol.getTextValue());

        if(jnChannelType != null) {
            co.setChannelType(jnChannelType.getTextValue());
        }
        else {
            co.setChannelType("1");
        }

        if(jnPwAlgorith != null) {
            co.setPwAlgorith(jnPwAlgorith.getTextValue());
        }

        if(jnPwContent != null) {
            co.setPwContent(jnPwContent.getTextValue());
        }

        if(jnMpExpressMode != null) {
            String s = jnMpExpressMode.getTextValue();
            int i = Integer.parseInt(s);
            co.setMpExpressMode(i);
        }

        if(jnMpSn != null) {
            co.setMpSn(stringArray2intArray(jnMpSn.getTextValue().split(",")));
        }

        if(jnCommandItems == null) {
            logger.error("no commandItems value");
            throw new Exception("no commandItems value");
        }
        Iterator<JsonNode> itCommandItems = jnCommandItems.getElements();
        //List<CommandItem> cis = new ArrayList<CommandItem>();
        while(itCommandItems.hasNext()) {
            CommandItem ci = new CommandItem();
            JsonNode iNode = itCommandItems.next();
            JsonNode jnIdentifier = iNode.get("identifier");
            JsonNode jnDatacellParam = iNode.get("datacellParam");

            if(jnIdentifier == null) {
                logger.error("one element of commandItems's identifier is null");
            }
            else {
                ci.setIdentifier(jnIdentifier.getTextValue());
            }

            if(jnDatacellParam != null) {
                Iterator<JsonNode> itDatacellParams = jnDatacellParam.getElements();
                Map<String, String> datacellParam = new HashMap<String, String>();
                while(itDatacellParams.hasNext()) {
                    JsonNode jNode = itDatacellParams.next();
                    JsonNode jnDataItemCode = jNode.get("dataItemCode");
                    JsonNode jnDataItemValue = jNode.get("dataItemValue");
                    if(jnDataItemCode != null && jnDataItemValue != null) {
                        datacellParam.put(jnDataItemCode.getTextValue(), jnDataItemValue.getTextValue());
                    }
                }
                ci.setDatacellParam(datacellParam);
            }

            co.AddCommandItem(ci);
        }

        return co;
    }

    /**
     * stringArray to intArray
     * 
     * @param stringArray
     * @return
     * @throws Exception
     */
    private static int[] stringArray2intArray(String[] stringArray) throws Exception {
        if(stringArray == null) {
            logger.error("stringArray is null");
            throw new Exception("stringArray is null");
        }

        if(stringArray.length == 0) {
            logger.error("stringArray's size is 0");
            throw new Exception("stringArray's size is 0");
        }

        int[] ia = new int[stringArray.length];
        for(int i = 0; i < ia.length; i++) {
            ia[i] = Integer.parseInt(stringArray[i]);
        }

        return ia;
    }
}
